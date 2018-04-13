package com.bobwares.databus.server.config;

import com.bobwares.core.bean.Registry;
import com.bobwares.databus.server.registry.model.AuthorizationDefinition;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class AuthorizationConfigurationTest  {

    @Test
    public void testAuthorizationDefinitionRegistry() throws Exception {
        AuthorizationConfiguration authorizationConfiguration = new AuthorizationConfiguration();
        Registry<AuthorizationDefinition> authorizationDefinitionRegistry = authorizationConfiguration.authorizationDefinitionRegistry();
        assertNotNull(authorizationDefinitionRegistry);
    }
}