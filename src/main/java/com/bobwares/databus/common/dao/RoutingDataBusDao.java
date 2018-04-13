package com.bobwares.databus.common.dao;

import com.bobwares.databus.common.model.ResourceConfiguration;
import com.bobwares.databus.common.model.PagingObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import javax.inject.Named;
import javax.sql.DataSource;
import java.util.Map;

public class RoutingDataBusDao extends SimpleDataBusDao {

    private DataSource dataSource;

    @Autowired(required = false)
    @Named("dataBusDataSource")
    @Override
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public PagingObject getRecords(ResourceConfiguration resourceConfiguration, Map<String, String> filters, Integer pageNum, Integer pageSize, String sortBy, Boolean reverse) {
        resourceConfiguration.setDatasourceContext();
        return super.getRecords(resourceConfiguration, filters, pageNum, pageSize, sortBy, reverse);
    }

	@Override
	protected SimpleJdbcCall getSimpleJdbcCall() {
		return new SimpleJdbcCall(dataSource);
	}

}