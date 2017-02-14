package com.bobwares.databus.server.authorization;



import com.bobwares.databus.common.annotation.AuthorizationProviderComponent;
import com.bobwares.databus.server.registry.model.AuthorizationDefinition;

import javax.inject.Inject;
import java.util.List;

@AuthorizationProviderComponent
public class SecurityPolicyAuthorizationProvider implements AuthorizationProvider {


    @Override
    public boolean isAuthorized(AuthorizationDefinition authorizationDefinition) {

//    	if (!checkAuthenticated(authorizationDefinition)) return false;
//    	if (!checkRoles(authorizationDefinition)) return false;
//    	if (!checkPermission(authorizationDefinition)) return false;
//    	if (!checkIpRestriction(authorizationDefinition)) return false;
//    	if (!checkProfiles(authorizationDefinition)) return false;
//    	if (!checkImpersonated(authorizationDefinition)) return false;
//    	if (!checkEncryptedParameters(authorizationDefinition)) return false;

        return true;
    }


}

