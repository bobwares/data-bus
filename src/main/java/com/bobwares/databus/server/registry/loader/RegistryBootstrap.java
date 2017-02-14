package com.bobwares.databus.server.registry.loader;

import com.bobwares.databus.server.validation.RegistryValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collection;

@Service
public class RegistryBootstrap implements SmartInitializingSingleton {
    final Logger logger = LoggerFactory.getLogger(getClass());

    protected Collection<RegistryLoader<?>> registryLoaders;
    protected RegistryValidator registryValidator;

    @Inject
    public RegistryBootstrap setRegistryLoaders(Collection<RegistryLoader<?>> registryLoaders) {
    	this.registryLoaders = registryLoaders;
    	return this;
    }

    @Inject
    public RegistryBootstrap setRegistryValidator(RegistryValidator registryValidator) {
        this.registryValidator = registryValidator;
        return this;
    }

    @Override
    public void afterSingletonsInstantiated() {
        logger.info("DataBus is starting.");
        loadRegistries();
        registryValidator.validate();
        logger.info("DataBus is ready.");
    }

    protected void loadRegistries() {
    	for (RegistryLoader<?> registryLoader : registryLoaders) {
    		registryLoader.load();
    	}
    }


}
