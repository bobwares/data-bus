package com.bobwares.databus.common.util;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class RoutingDataSourceTest {

    @Test
    public void testDetermineCurrentLookupKey() throws Exception {
        DataBusRoutingDataSource routingDataSource = new DataBusRoutingDataSource();
        Object key = routingDataSource.determineCurrentLookupKey();
        assertTrue(key == null);
    }
}