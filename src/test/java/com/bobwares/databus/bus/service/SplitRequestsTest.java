package com.bobwares.databus.bus.service;

import com.bobwares.databus.bus.model.ResourceMessage;
import com.bobwares.databus.bus.service.endpoint.SplitRequests;
import com.bobwares.databus.common.model.Configuration;
import com.bobwares.databus.server.registry.model.ResourceDefinition;
import com.bobwares.databus.server.service.locator.ResourceLocatorImpl;
import com.bobwares.databus.server.service.request.RequestObject;
import com.bobwares.databus.server.service.request.RequestSource;
import com.bobwares.test.AbstractDatabaseTest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

import static com.bobwares.databus.LoadConfiguration.loadConfiguration;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class SplitRequestsTest extends AbstractDatabaseTest {

    private static final String BUS_TESTS_TEXT_WELCOME_HEADER = "bus-tests/text-welcome-header";
    private static final String BUS_TESTS_TEXT_WELCOME_SUB_HEADER = "bus-tests/text-welcome-sub-header";
    private static final String BUS_TESTS_TEXT_WELCOME_BULLET_1 = "bus-tests/text-welcome-bullet-1";
    private static final String BUS_TESTS_TEXT_WELCOME_BULLET_2 = "bus-tests/text-welcome-bullet-2";
    private static final String BUS_TESTS_PAGE_TEST_JSON = "bus-tests/page-test.json";
    private static final String PATH_DATABUS = "databus";
    private static final String BUS_TESTS_PAGE_TEST = "bus-tests/page-test";

    private Configuration configuration;

    private Configuration textWelcomeHeaderConfiguration;
    private Configuration textWelcomeSubHeaderConfiguration;
    private Configuration textWelcomeBullet1Configuration;
    private Configuration textWelcomeBullet2Configuration;

    @Mock
    private ResourceLocatorImpl resourceLocator;

    SplitRequests splitRequests;

    ResourceMessage pageResourceMessage;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        MultiValueMap<String, String> parameters =  new LinkedMultiValueMap<>();
        RequestObject requestObject = new RequestObject(parameters, BUS_TESTS_PAGE_TEST_JSON, RequestSource.EXTERNAL);

        textWelcomeHeaderConfiguration = loadConfiguration(BUS_TESTS_TEXT_WELCOME_HEADER, PATH_DATABUS);
        assertNotNull(textWelcomeHeaderConfiguration);

        textWelcomeSubHeaderConfiguration = loadConfiguration(BUS_TESTS_TEXT_WELCOME_SUB_HEADER, PATH_DATABUS);
        assertNotNull(textWelcomeSubHeaderConfiguration);

        textWelcomeBullet1Configuration = loadConfiguration(BUS_TESTS_TEXT_WELCOME_BULLET_1, PATH_DATABUS);
        assertNotNull(textWelcomeBullet1Configuration);

        textWelcomeBullet2Configuration = loadConfiguration(BUS_TESTS_TEXT_WELCOME_BULLET_2, PATH_DATABUS);
        assertNotNull(textWelcomeBullet2Configuration);


        configuration = loadConfiguration(BUS_TESTS_PAGE_TEST, PATH_DATABUS);
        assertNotNull(configuration);
        ResourceDefinition resourceDefinition = new ResourceDefinition(BUS_TESTS_PAGE_TEST, configuration);

        pageResourceMessage = new ResourceMessage(requestObject, resourceDefinition);

        splitRequests = new SplitRequests();
        splitRequests.setResourceLocator(resourceLocator);
    }

    @Test
    public void testHandle() throws Exception {
        ResourceDefinition welcomeHeaderResourceDefinition = new ResourceDefinition(BUS_TESTS_TEXT_WELCOME_HEADER, textWelcomeHeaderConfiguration);
        when(resourceLocator.getResource(BUS_TESTS_TEXT_WELCOME_HEADER)).thenReturn(welcomeHeaderResourceDefinition);

        ResourceDefinition welcomeSubHeaderResourceDefinition = new ResourceDefinition(BUS_TESTS_TEXT_WELCOME_SUB_HEADER, textWelcomeSubHeaderConfiguration);
        when(resourceLocator.getResource(BUS_TESTS_TEXT_WELCOME_SUB_HEADER)).thenReturn(welcomeSubHeaderResourceDefinition);

        ResourceDefinition welcomeBullet1ResourceDefinition = new ResourceDefinition(BUS_TESTS_TEXT_WELCOME_BULLET_1, textWelcomeBullet1Configuration);
        when(resourceLocator.getResource(BUS_TESTS_TEXT_WELCOME_BULLET_1)).thenReturn(welcomeBullet1ResourceDefinition);

        ResourceDefinition welcomeBullet2ResourceDefinition = new ResourceDefinition(BUS_TESTS_TEXT_WELCOME_BULLET_2, textWelcomeBullet2Configuration);
        when(resourceLocator.getResource(BUS_TESTS_TEXT_WELCOME_BULLET_2)).thenReturn(welcomeBullet2ResourceDefinition);

        List<ResourceMessage> resourceMessages = splitRequests.handle(pageResourceMessage);

        assertNotNull(resourceMessages);
        assertTrue(resourceMessages.size() == 4);
    }

    @Test(expected = NoSuchMethodException.class)
    public void testHandleError() throws Exception {
        ResourceDefinition welcomeHeaderResourceDefinition = new ResourceDefinition(BUS_TESTS_TEXT_WELCOME_HEADER, textWelcomeHeaderConfiguration);
        when(resourceLocator.getResource(BUS_TESTS_TEXT_WELCOME_HEADER)).thenReturn(welcomeHeaderResourceDefinition);

        ResourceDefinition welcomeSubHeaderResourceDefinition = new ResourceDefinition(BUS_TESTS_TEXT_WELCOME_SUB_HEADER, textWelcomeSubHeaderConfiguration);
        when(resourceLocator.getResource(BUS_TESTS_TEXT_WELCOME_SUB_HEADER)).thenReturn(welcomeSubHeaderResourceDefinition);

        ResourceDefinition welcomeBullet1ResourceDefinition = new ResourceDefinition(BUS_TESTS_TEXT_WELCOME_BULLET_1, textWelcomeBullet1Configuration);
        when(resourceLocator.getResource(BUS_TESTS_TEXT_WELCOME_BULLET_1)).thenReturn(welcomeBullet1ResourceDefinition);

        when(resourceLocator.getResource(BUS_TESTS_TEXT_WELCOME_BULLET_2)).thenThrow(NoSuchMethodException.class);

        List<ResourceMessage> resourceMessages = splitRequests.handle(pageResourceMessage);

        assertNotNull(resourceMessages);
        assertTrue(resourceMessages.size() == 4);

    }

}