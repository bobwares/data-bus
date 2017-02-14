package com.bobwares.databus.server.registry.service;

import com.bobwares.core.bean.Registry;
import com.bobwares.databus.common.annotation.RegistryServiceComponent;
import com.bobwares.databus.server.registry.model.AuthorizationDefinition;

import javax.inject.Inject;
import java.util.*;

@RegistryServiceComponent
public class AuthorizationRegistry implements RegistryService<AuthorizationDefinition> {

    private Registry<AuthorizationDefinition> authorizationDefinitionRegistry;
    private Map<String, AuthorizationDefinition> authorizationDefinitionMap;

    @Inject
    public void setAuthorizationDefinitionRegistry(Registry<AuthorizationDefinition> authorizationDefinitionRegistry) {
        this.authorizationDefinitionRegistry = authorizationDefinitionRegistry;
    }

    @Override
	public AuthorizationDefinition getEntry(String authKey) {
        return getEntries().get(authKey.toLowerCase());
    }

    @Override
	public Map<String, AuthorizationDefinition> getEntries() {
        if (authorizationDefinitionMap == null) {
        	authorizationDefinitionMap = buildConfigurationMap();
        }
        return authorizationDefinitionMap;
    }

    @Override
    public List<AuthorizationDefinition> getList() {
        return new ArrayList<>(getEntries().values());
    }

    protected Map<String, AuthorizationDefinition> buildConfigurationMap() {
        Map<String, AuthorizationDefinition> resourceMap = new HashMap<>();
        Collection<AuthorizationDefinition> authorizationDefinitions = authorizationDefinitionRegistry.getCollection();
        for (AuthorizationDefinition authorizationDefinition : authorizationDefinitions) {
            resourceMap.put(authorizationDefinition.getAuthKey().toLowerCase(), authorizationDefinition);
        }
        return resourceMap;
    }

}
