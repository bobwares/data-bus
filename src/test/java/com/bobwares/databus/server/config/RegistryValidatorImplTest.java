package com.bobwares.databus.server.config;

import com.bobwares.databus.server.validation.RegistryValidator;
import com.bobwares.test.AbstractRestTest;
import org.junit.Ignore;
import org.junit.Test;

import javax.inject.Inject;

public class RegistryValidatorImplTest extends AbstractRestTest {

    @Inject
    RegistryValidator registryValidator;

    @Ignore
    @Test
    public void testValidate() throws Exception {
        registryValidator.validate();
    }
}