package com.bobwares.databus.server.registry.model;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.bobwares.databus.server.authorization.AuthorizationProvider;

public class AuthorizationDefinitionTest {

    public static final String AUTH_KEY = "authKey";
    public static final String AUTH_SERVICE_KEY = "authServiceKey";

    @Test
    public void test() throws Exception {
        AuthorizationDefinition authorizationDefinition = new AuthorizationDefinition();

        authorizationDefinition.setAuthKey(AUTH_KEY);
        assertTrue(authorizationDefinition.getAuthKey().equals(AUTH_KEY));

        AuthorizationProvider authorizationProvider = new TestAuthorizationProvider();
        authorizationDefinition.setAuthorizationProvider(authorizationProvider);
        assertTrue(authorizationDefinition.getAuthorizationProvider() == authorizationProvider);

        authorizationDefinition.setAuthorizationProviderKey(AUTH_SERVICE_KEY);
        assertTrue(authorizationDefinition.getAuthorizationProviderKey().equals(AUTH_SERVICE_KEY));

        Map<String, Object> params = new HashMap<>();
        params.put("param1", "param1Value");
        authorizationDefinition.setParams(params);
        assertTrue(authorizationDefinition.getParams().get("param1").equals("param1Value"));

        List<String> roles = Arrays.asList("ADMIN", "VIEW");
        authorizationDefinition.setRoles(roles);
        assertTrue(authorizationDefinition.getRoles().size() == 2);

        assertTrue(authorizationDefinition.getParams() != null);
        assertTrue(authorizationDefinition.getParameter("param1").equals("param1Value"));
    }

    protected class TestAuthorizationProvider implements AuthorizationProvider {
    	@Override
        public boolean isAuthorized(AuthorizationDefinition authorizationDefinition) {
            return true;
        }
    }
}