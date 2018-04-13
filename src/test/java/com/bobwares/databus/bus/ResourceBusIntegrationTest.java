package com.bobwares.databus.bus;

import com.bobwares.databus.ParamBuilder;
import com.bobwares.databus.bus.service.ResourceBusService;
import com.bobwares.databus.common.model.Configuration;
import com.bobwares.databus.common.model.ResponseObject;
import com.bobwares.databus.server.registry.model.ResourceDefinition;
import com.bobwares.databus.server.service.request.RequestObject;
import com.bobwares.databus.server.service.request.RequestSource;
import com.bobwares.test.AbstractDatabaseTest;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.util.MultiValueMap;

import javax.inject.Inject;

import static com.bobwares.databus.LoadConfiguration.loadConfiguration;
import static org.junit.Assert.assertNotNull;

public class ResourceBusIntegrationTest extends AbstractDatabaseTest {

    private static final String PATH_DATABUS = "databus";
    private static final String BUS_TESTS_PAGE_TEST = "bus-tests/page-test";
    private RequestObject requestObject;
    private ResourceDefinition resourceDefinition;

    @Inject
    ResourceBusService resourceBusService;

    @Before
    public void setup() {
        Configuration configuration = loadConfiguration(BUS_TESTS_PAGE_TEST, PATH_DATABUS);
        assertNotNull(configuration);

        resourceDefinition = new ResourceDefinition(BUS_TESTS_PAGE_TEST, configuration);
        MultiValueMap<String, String> parameters = ParamBuilder.build(null, null, null, null);

        requestObject = new RequestObject(parameters, BUS_TESTS_PAGE_TEST, RequestSource.EXTERNAL);
    }

    @Ignore
    @Test
    public void test() {

        ResponseObject page = resourceBusService.getRecords(resourceDefinition, requestObject);
        assertNotNull(page);
    }



}
