package com.ekeyfund.oss.modular.code.service;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.mapper.SqlRunner;
import com.ekeyfund.oss.core.constant.DatasourceEnum;
import com.ekeyfund.oss.core.mutidatasource.DynamicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 获取数据库所有的表
 *
 * @author fengshuonan
 * @date 2017-12-04-下午1:37
 */
@Service
public class TableService {

    @Value("${guns.defaultDataSourceName}")
    private DatasourceEnum defaultDataSourceName;

    @Autowired
    private DynamicDataSource mutiDataSource;
//    /**
//     * 获取所有数据库名，目前限制两个配置
//     * @return
//     */
//    public List<String> getDataSourceNames() {
//        List result = new ArrayList();
//        for(DatasourceEnum datasourceEnum:DatasourceEnum.values()){
//            result.add(datasourceEnum.name());
//        }
//        return result;
//    }

    /**
     * 获取当前数据库所有的表信息
     */
    public List<Map<String, Object>> getAllTables() {
        return getAllTables(defaultDataSourceName);
    }

    /**
     * 获取当前数据库所有的表信息
     */
    public List<Map<String, Object>> getAllTables(DatasourceEnum dataSourceName) {
        DruidDataSource dataSource = (DruidDataSource)mutiDataSource.getDataSource(dataSourceName);
        String dbName = new JdbcUrlSplitter(dataSource.getUrl()).database;
        String sql = "select TABLE_NAME as tableName,TABLE_COMMENT as tableComment from information_schema.`TABLES` where TABLE_SCHEMA = '" + dbName + "'";
        return SqlRunner.db().selectList(sql);
    }

    public DatasourceEnum getDefaultDataSourceName() {
        return defaultDataSourceName;
    }

    public void setDefaultDataSourceName(DatasourceEnum defaultDataSourceName) {
        this.defaultDataSourceName = defaultDataSourceName;
    }

    public DynamicDataSource getMutiDataSource() {
        return mutiDataSource;
    }

    public void setMutiDataSource(DynamicDataSource mutiDataSource) {
        this.mutiDataSource = mutiDataSource;
    }

    static class JdbcUrlSplitter
    {
        public String driverName, host, port, database, params;

        public JdbcUrlSplitter(String jdbcUrl)
        {
            int pos, pos1, pos2;
            String connUri;

            if(jdbcUrl == null || !jdbcUrl.startsWith("jdbc:")
                    || (pos1 = jdbcUrl.indexOf(':', 5)) == -1)
                throw new IllegalArgumentException("Invalid JDBC url.");

            driverName = jdbcUrl.substring(5, pos1);
            if((pos2 = jdbcUrl.indexOf('?', pos1)) == -1)
            {
                connUri = jdbcUrl.substring(pos1 + 1);
            }
            else
            {
                connUri = jdbcUrl.substring(pos1 + 1, pos2);
                params = jdbcUrl.substring(pos2 + 1);
            }

            if(connUri.startsWith("//"))
            {
                if((pos = connUri.indexOf('/', 2)) != -1)
                {
                    host = connUri.substring(2, pos);
                    database = connUri.substring(pos + 1);

                    if((pos = host.indexOf(':')) != -1)
                    {
                        port = host.substring(pos + 1);
                        host = host.substring(0, pos);
                    }
                }
            }
            else
            {
                database = connUri;
            }
        }
    }

    public static void main(String [] argcv){
        String url = "jdbc:mysql://192.168.1.210:3306/yq_oss?autoReconnect=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false";
        new JdbcUrlSplitter(url);
    }
}
