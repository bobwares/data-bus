package com.bobwares.databus.server.authorization;

import com.bobwares.databus.server.registry.model.AuthorizationDefinition;

public interface AuthorizationProvider {

    boolean isAuthorized(AuthorizationDefinition authorizationDefintion);

}
