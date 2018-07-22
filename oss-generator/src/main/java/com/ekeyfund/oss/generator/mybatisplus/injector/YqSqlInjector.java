package com.ekeyfund.oss.generator.mybatisplus.injector;

import com.baomidou.mybatisplus.entity.TableFieldInfo;
import com.baomidou.mybatisplus.entity.TableInfo;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.enums.SqlMethod;
import com.baomidou.mybatisplus.mapper.LogicSqlInjector;
import com.baomidou.mybatisplus.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.baomidou.mybatisplus.toolkit.TableInfoHelper;
import com.ekeyfund.oss.generator.mybatisplus.injector.id.YqIdGenerator;
import com.ekeyfund.oss.generator.mybatisplus.injector.id.YqSelectKeyGenerator;
import com.ekeyfund.oss.generator.mybatisplus.injector.incrementer.YqKeyGenerator;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.executor.keygen.Jdbc3KeyGenerator;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.SqlSource;

import java.lang.reflect.Field;
import java.util.List;

/**
 * SQL注入器，实现对自增值的注入
 * Created by zhaolin on 3/29/2018.
 */
public class YqSqlInjector extends LogicSqlInjector {
    /**
     * <p>
     * 注入插入 SQL 语句
     * </p>
     *
     * @param selective   是否选择插入
     * @param mapperClass
     * @param modelClass
     * @param table
     */
    @Override
    protected void injectInsertOneSql(boolean selective, Class<?> mapperClass, Class<?> modelClass, TableInfo table) {
        /*
         * INSERT INTO table <trim prefix="(" suffix=")" suffixOverrides=",">
		 * <if test="xx != null">xx,</if> </trim> <trim prefix="values ("
		 * suffix=")" suffixOverrides=","> <if test="xx != null">#{xx},</if>
		 * </trim>
		 */
        KeyGenerator keyGenerator = new NoKeyGenerator();
        StringBuilder fieldBuilder = new StringBuilder();
        StringBuilder placeholderBuilder = new StringBuilder();
        SqlMethod sqlMethod = selective ? SqlMethod.INSERT_ONE : SqlMethod.INSERT_ONE_ALL_COLUMN;

        fieldBuilder.append("\n<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">\n");
        placeholderBuilder.append("\n<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">\n");
        String keyProperty = null;
        String keyColumn = null;

        // 表包含主键处理逻辑,如果不包含主键当普通字段处理
        if (StringUtils.isNotEmpty(table.getKeyProperty())) {
            Field keyField = null;
            YqIdGenerator idGenerator = null;

            List<Field> fieldList = ReflectionKit.getFieldList(modelClass);
            for(Field field:fieldList){
                if(field.getName().equals(table.getKeyProperty())){
                    keyField = field;
                    break;
                }
        }
                idGenerator = keyField.getAnnotation(YqIdGenerator.class);
            if(keyField!=null && idGenerator !=null){
                int length = idGenerator.value();
                //调整为自定义的列
                keyGenerator = genYqKeyGenerator(table, builderAssistant, sqlMethod.getMethod(),new YqKeyGenerator(idGenerator.value(),idGenerator.clazz()));
                keyProperty = table.getKeyProperty();
                keyColumn = table.getKeyColumn();
                fieldBuilder.append(table.getKeyColumn()).append(",");
                placeholderBuilder.append("#{").append(table.getKeyProperty()).append("},");
            } else if (table.getIdType() == IdType.AUTO) {
                /* 自增主键 */
                keyGenerator = new Jdbc3KeyGenerator();
                keyProperty = table.getKeyProperty();
                keyColumn = table.getKeyColumn();
            } else {
                if (null != table.getKeySequence()) {
                    keyGenerator = TableInfoHelper.genKeyGenerator(table, builderAssistant, sqlMethod.getMethod(), languageDriver);
                    keyProperty = table.getKeyProperty();
                    keyColumn = table.getKeyColumn();
                    fieldBuilder.append(table.getKeyColumn()).append(",");
                    placeholderBuilder.append("#{").append(table.getKeyProperty()).append("},");
                } else {
                    /* 用户输入自定义ID */
                    fieldBuilder.append(table.getKeyColumn()).append(",");
                    // 正常自定义主键策略
                    placeholderBuilder.append("#{").append(table.getKeyProperty()).append("},");
                }
            }
        }

        // 是否 IF 标签判断
        boolean ifTag;
        List<TableFieldInfo> fieldList = table.getFieldList();
        for (TableFieldInfo fieldInfo : fieldList) {
            // 在FieldIgnore,INSERT_UPDATE,INSERT 时设置为false
            ifTag = !(FieldFill.INSERT == fieldInfo.getFieldFill()
                    || FieldFill.INSERT_UPDATE == fieldInfo.getFieldFill());
            if (selective && ifTag) {
                fieldBuilder.append(convertIfTagIgnored(fieldInfo, false));
                fieldBuilder.append(fieldInfo.getColumn()).append(",");
                fieldBuilder.append(convertIfTagIgnored(fieldInfo, true));
                placeholderBuilder.append(convertIfTagIgnored(fieldInfo, false));
                placeholderBuilder.append("#{").append(fieldInfo.getEl()).append("},");
                placeholderBuilder.append(convertIfTagIgnored(fieldInfo, true));
            } else {
                fieldBuilder.append(fieldInfo.getColumn()).append(",");
                placeholderBuilder.append("#{").append(fieldInfo.getEl()).append("},");
            }
        }
        fieldBuilder.append("\n</trim>");
        placeholderBuilder.append("\n</trim>");
        String sql = String.format(sqlMethod.getSql(), table.getTableName(), fieldBuilder.toString(),
                placeholderBuilder.toString());
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        this.addInsertMappedStatement(mapperClass, modelClass, sqlMethod.getMethod(), sqlSource, keyGenerator, keyProperty,
                keyColumn);
    }

    /**
     * <p>
     * 自定义 KEY 生成器
     * </p>
     */
    public static KeyGenerator genYqKeyGenerator(TableInfo tableInfo, MapperBuilderAssistant builderAssistant,
                                               String baseStatementId,YqKeyGenerator keyGenerator) {
        YqSelectKeyGenerator selectKeyGenerator = new YqSelectKeyGenerator(tableInfo.getKeyProperty(),keyGenerator,true);
        String id = builderAssistant.applyCurrentNamespace(baseStatementId + SelectKeyGenerator.SELECT_KEY_SUFFIX, false);
        builderAssistant.getConfiguration().addKeyGenerator(id, selectKeyGenerator);
        return selectKeyGenerator;
    }
}
