package com.bobwares.databus.server.authorization;

import com.bobwares.databus.server.registry.model.AuthorizationDefinition;
import com.bobwares.databus.server.registry.service.AuthorizationRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.inject.Inject;

@Service
public class DataBusAuthorizationServiceImpl implements DataBusAuthorizationService {
    final Logger logger = LoggerFactory.getLogger(getClass());

    private AuthorizationRegistry authorizationRegistry;

    @Inject
    public void setAuthorizationRegistry(AuthorizationRegistry authorizationRegistry) {
        this.authorizationRegistry = authorizationRegistry;
    }

    @Override
    public boolean isAuthorized(String authKey) {
        try {
            Assert.notNull(authKey, "authKey is null.");

            AuthorizationDefinition authorizationDefinition = authorizationRegistry.getEntry(authKey);
            Assert.notNull(authorizationDefinition, "Could not retrieve authorizationDefinition from Registry for authKey " + authKey);

            AuthorizationProvider authorizationProvider = authorizationDefinition.getAuthorizationProvider();
            if (authorizationProvider.isAuthorized(authorizationDefinition)) {
            	return true;
            }
        }
        catch (Exception e) {
            logger.error("authorization check failed", e);
        }

        throw new AccessDeniedException("Not Authorized for this resource.");
    }
}
