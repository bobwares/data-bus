package com.bobwares.databus.common.dao;

import com.bobwares.databus.ParamBuilder;
import com.bobwares.databus.common.model.ResourceConfiguration;
import com.bobwares.databus.common.model.PagingObject;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.util.MultiValueMap;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class RoutingDataBusDaoTest {

    @Mock
    private DataSource dataSource;

    @Mock
    SimpleDataBusDao simpleDataBusDao;

    private  RoutingDataBusDao routingDataBusDao;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        routingDataBusDao = new RoutingDataBusDao();
        routingDataBusDao.setDataSource(dataSource);
    }

    @Ignore
    @Test
    public void testGetRecords() throws Exception {

        Configuration configuration = new Configuration();
        configuration.setDatabase("EP");
        configuration.setStoredProcedure("MOCK_SP");
        MultiValueMap<String, String> params = ParamBuilder.build();
        Map<String, String> filters = new HashMap<>();
        filters.put("key","test");
        when(simpleDataBusDao.getRecords(configuration, filters, 1, 1, "", false)).thenReturn(null);

        PagingObject pagingObject = routingDataBusDao.getRecords(configuration, filters, 1, -1, "", false);
        assertTrue(pagingObject == null);
    }
}