package com.bobwares.databus.common.util;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import javax.inject.Inject;

import org.junit.Test;

import com.bobwares.databus.common.model.Configuration;
import com.bobwares.databus.server.authorization.SecurityPolicyAuthorizationProvider;
import com.bobwares.databus.server.registry.model.AuthorizationDefinition;
import com.bobwares.test.AbstractDatabaseTest;

public class ConfigurationLoaderTest extends AbstractDatabaseTest {

	@Inject SecurityPolicyAuthorizationProvider securityPolicyAuthorizationProvider;

    @Test
    public void testLoadResourceConfiguration() {
        JsonConfigurationLoader jsonConfigurationLoader = new JsonConfigurationLoader();
        Configuration configuration = jsonConfigurationLoader.loadConfiguration("databus/configuration-loader-test.json", Configuration.class);

        assertNotNull(configuration);
    }

    @Test
    public void testAuthorizationConfiguration() {
        JsonConfigurationLoader jsonConfigurationLoader = new JsonConfigurationLoader();
        AuthorizationDefinition authorizationDefinition = jsonConfigurationLoader.loadConfiguration("databus/config/authorization/test-authorization.json", AuthorizationDefinition.class);

        assertThat(authorizationDefinition, notNullValue());
        assertThat(authorizationDefinition.getAuthKey(), is("test-authorization"));
        assertThat(authorizationDefinition.getAuthorizationProviderKey(), is("securityPolicyAuthorizationProvider"));
        assertThat(authorizationDefinition.getAuthenticated(), is(true));
        assertThat(authorizationDefinition.getRoles(), hasItems("role1", "role2"));
        assertThat(authorizationDefinition.getPermission(), is("permission"));
        assertThat(authorizationDefinition.getTargetId(), is(1L));
        assertThat(authorizationDefinition.getTargetTable(), is("TABLE"));
        assertThat(authorizationDefinition.getEncryptedParameters(), is(true));
        assertThat(authorizationDefinition.getImpersonated(), is(false));
        assertThat(authorizationDefinition.getIpRestriction(), is("bobwares"));
        assertThat(authorizationDefinition.getProfiles(), hasItems("profile1", "profile2"));
    }

}
