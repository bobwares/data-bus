package com.bobwares.databus.server.registry.loader.impl;


import com.bobwares.core.bean.Registry;
import com.bobwares.databus.common.annotation.RegistryLoaderComponent;
import com.bobwares.databus.common.util.ConfigurationLoader;
import com.bobwares.databus.server.authorization.AuthorizationProvider;
import com.bobwares.databus.server.config.DataBusConfig;
import com.bobwares.databus.server.registry.loader.RegistryLoader;
import com.bobwares.databus.server.registry.model.AuthorizationDefinition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;

import javax.inject.Inject;
import java.util.Map;

@RegistryLoaderComponent
public class AuthorizationRegistryLoader implements RegistryLoader<AuthorizationDefinition> {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private DataBusConfig dataBusConfig;

    @Inject
    public void setDataBusConfig(DataBusConfig dataBusConfig) {
        this.dataBusConfig = dataBusConfig;
    }

    private ApplicationContext applicationContext;

    @Inject
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    private ConfigurationLoader configurationLoader;

    @Inject
    public void setConfigurationLoader(ConfigurationLoader configurationLoader) {
        this.configurationLoader = configurationLoader;
    }

    private Registry<AuthorizationDefinition> authorizationDefinitionRegistry;

    @Inject
    public void setAuthorizationDefinitionRegistry(Registry<AuthorizationDefinition> authorizationDefinitionRegistry) {
        this.authorizationDefinitionRegistry = authorizationDefinitionRegistry;
    }


    @Override
    public void load() {
        authorizationDefinitionRegistry.clear();

        Map<String,AuthorizationDefinition> configurationMap = configurationLoader.loadConfigurations(dataBusConfig.getPathToAuthorizationDefinitions(), AuthorizationDefinition.class);
        for (Map.Entry<String, AuthorizationDefinition> entry : configurationMap.entrySet()) {
        	AuthorizationDefinition authorizationDefinition = entry.getValue();

        	AuthorizationProvider authorizationProvider = getAuthorizationProvider(authorizationDefinition);
            if (authorizationProvider != null) {
                authorizationDefinition.setAuthorizationProvider(authorizationProvider);
                authorizationDefinitionRegistry.add(authorizationDefinition);
            }
        }

        for (AuthorizationDefinition authorizationDefinition : authorizationDefinitionRegistry.getCollection()) {
            logger.info("AuthorizationDefinition - " + authorizationDefinition.getAuthKey());
        }
    }

    private AuthorizationProvider getAuthorizationProvider(AuthorizationDefinition loadedAuthorizationDefinition) {
        try {
            AuthorizationProvider bean = applicationContext.getBean(loadedAuthorizationDefinition.getAuthorizationProviderKey(), AuthorizationProvider.class);
            Assert.notNull(bean);
            return bean;
        } catch (Exception e) {
            logger.error("Could not load Authorization Service for authKey {}" + loadedAuthorizationDefinition.getAuthKey(), e);
            return null;
        }
    }

}
