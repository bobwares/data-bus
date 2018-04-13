package com.bobwares.databus.server.config;

import com.bobwares.databus.server.registry.loader.RegistryBootstrap;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;

public class RegistryConfigurationTest {

    private RegistryBootstrap registryConfiguration;

    @Mock
    private ApplicationContext applicationContext;

    @Mock
    ContextRefreshedEvent contextRefreshedEvent;

    @Before
    public void setup() {

        registryConfiguration = new RegistryBootstrap();
        MockitoAnnotations.initMocks(this);
    }

    @Ignore
    @Test
    public void testHandleContextRefresh() throws Exception {
       // registryConfiguration.handleContextRefresh(contextRefreshedEvent);
    }
}