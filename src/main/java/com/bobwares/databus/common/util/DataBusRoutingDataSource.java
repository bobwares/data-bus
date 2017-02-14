package com.bobwares.databus.common.util;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;


public class DataBusRoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
       return DataBusDatabaseContext.getDatabase();
    }

}
