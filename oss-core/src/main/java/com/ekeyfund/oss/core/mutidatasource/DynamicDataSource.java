package com.ekeyfund.oss.core.mutidatasource;

import com.ekeyfund.oss.core.constant.DatasourceEnum;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;

/**
 * 动态数据源
 *
 * @author fengshuonan
 * @date 2017年3月5日 上午9:11:49
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		return DataSourceContextHolder.getDataSourceType();
	}

	/**
	 * 根据名字获取datasource
	 * @param dataSoutce
	 * @return
	 */
	public DataSource getDataSource(DatasourceEnum dataSoutce){
		String dataSourceType = DataSourceContextHolder.getDataSourceType();
		DataSourceContextHolder.setDataSourceType(dataSoutce.name());
		DataSource dataSource = determineTargetDataSource();
		DataSourceContextHolder.setDataSourceType(dataSourceType);
		return dataSource;
	}

}
