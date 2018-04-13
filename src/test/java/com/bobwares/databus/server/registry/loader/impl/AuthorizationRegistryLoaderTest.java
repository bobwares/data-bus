package com.bobwares.databus.server.registry.loader.impl;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationContext;

import com.bobwares.core.bean.Registry;
import com.bobwares.core.util.MapBuilder;
import com.bobwares.databus.common.util.ConfigurationLoader;
import com.bobwares.databus.server.authorization.AuthorizationProvider;
import com.bobwares.databus.server.authorization.SecurityPolicyAuthorizationProvider;
import com.bobwares.databus.server.config.DataBusConfig;
import com.bobwares.databus.server.registry.model.AuthorizationDefinition;

public class AuthorizationRegistryLoaderTest {

    String validPath = "databus/config/authorization";

    @Mock ConfigurationLoader configurationLoader;
    @Mock ApplicationContext applicationContext;
    @Mock DataBusConfig dataBusConfig;

    AuthorizationRegistryLoader registryLoader;
    Registry<AuthorizationDefinition> authorizationDefinitionRegistry;
    AuthorizationDefinition authorizationDefinition;

    @Before
    public void setup() {
        authorizationDefinitionRegistry = new Registry<>();
        registryLoader = new AuthorizationRegistryLoader();
        MockitoAnnotations.initMocks(this);

        registryLoader.setConfigurationLoader(configurationLoader);
        registryLoader.setApplicationContext(applicationContext);
        registryLoader.setAuthorizationDefinitionRegistry(authorizationDefinitionRegistry);
        registryLoader.setDataBusConfig(dataBusConfig);

        authorizationDefinition = new AuthorizationDefinition();
        authorizationDefinition.setAuthorizationProviderKey("beanName");
        when(dataBusConfig.getPathToAuthorizationDefinitions()).thenReturn(validPath);
    }

    @Test
     public void load() {

    	when(configurationLoader.loadConfigurations(validPath, AuthorizationDefinition.class)).thenReturn(MapBuilder.<String,AuthorizationDefinition>newHashMap()
    		.add("file1.json", authorizationDefinition)
    		.build()
        );

        when(applicationContext.getBean("beanName", AuthorizationProvider.class)).thenReturn(new SecurityPolicyAuthorizationProvider());

        registryLoader.load();

        assertThat(authorizationDefinitionRegistry, notNullValue());
        assertThat(authorizationDefinitionRegistry.getCollection().size(), is(1));
    }

}
