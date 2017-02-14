package com.bobwares.databus.server.validation;

import com.bobwares.databus.common.transformer.transformers.ColumnTransformer;
import com.bobwares.databus.server.registry.model.AuthorizationDefinition;
import com.bobwares.databus.server.registry.model.ColumnTransformerDefinition;
import com.bobwares.databus.server.registry.model.ResourceDefinition;
import com.bobwares.databus.server.registry.model.ServiceDefinition;
import com.bobwares.databus.server.registry.service.RegistryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class RegistryValidatorImpl implements RegistryValidator {
	final Logger logger = LoggerFactory.getLogger(getClass());

    private  RegistryService<ColumnTransformerDefinition> columnTransformRegistry;

    @Inject
    public void setColumnTransformRegistry(RegistryService<ColumnTransformerDefinition> columnTransformRegistry) {
        this.columnTransformRegistry = columnTransformRegistry;
    }

    private RegistryService<ResourceDefinition> resourceDefinitionRegistry;

    @Inject
    public void setResourceDefinitionRegistry(RegistryService<ResourceDefinition> resourceDefinitionRegistry) {
        this.resourceDefinitionRegistry = resourceDefinitionRegistry;
    }

    private RegistryService<ServiceDefinition> serviceDefinitionRegistry;

    @Inject
    public void setServiceDefinitionRegistry(RegistryService<ServiceDefinition> serviceDefinitionRegistry) {
        this.serviceDefinitionRegistry = serviceDefinitionRegistry;
    }

    private RegistryService<AuthorizationDefinition> authorizationDefinitionRegistry;

    @Inject
    public void setAuthorizationDefinitionRegistry(RegistryService<AuthorizationDefinition> authorizationDefinitionRegistry) {
        this.authorizationDefinitionRegistry = authorizationDefinitionRegistry;
    }

    @Override
    public void validate() {
        validateAuthKey();
        validateServiceKey();
        validateColumnTransformer();
    }

    private void validateColumnTransformer() {
        for (ResourceDefinition resourceDefinition : resourceDefinitionRegistry.getList()) {
            for (ColumnTransformerDefinition columnTransformerDefinition : columnTransformRegistry.getList()) {
                ColumnTransformer columnTransformer = columnTransformerDefinition.getColumnTransformer();
                if (columnTransformer.needsTransforming(resourceDefinition.getResourceConfiguration())) {
                    resourceDefinition.addColumnTransformerDefinition(columnTransformerDefinition);
                }
            }
        }

    }

    private void validateAuthKey() {
        for (ResourceDefinition resourceDefinition : resourceDefinitionRegistry.getList()) {
            if (resourceDefinition.getAuthKey() != null && !resourceDefinition.getAuthKey().isEmpty()) {
                AuthorizationDefinition authorizationDefinition = authorizationDefinitionRegistry.getEntry(resourceDefinition.getAuthKey());
                if (authorizationDefinition == null) {
                    logger.info(resourceDefinition.getResourceUri() + " has an authKey setting the does not exist. AuthKey - " + resourceDefinition.getAuthKey());

                } else {
                    resourceDefinition.setValid(true);
                    logger.info(resourceDefinition.getResourceUri() + "  AuthKey - " + resourceDefinition.getAuthKey());
                }
            } else {
            	logger.info(resourceDefinition.getResourceUri() + " does not have an authKey setting.");
            }

        }
    }

    private void validateServiceKey() {
        for (ResourceDefinition resourceDefinition : resourceDefinitionRegistry.getList()) {
            if (resourceDefinition.getServiceKey() != null && !resourceDefinition.getServiceKey().isEmpty()) {
                ServiceDefinition serviceDefinition = serviceDefinitionRegistry.getEntry(resourceDefinition.getServiceKey());
                if (serviceDefinition == null) {
                	logger.info("serviceKey - " + resourceDefinition.getServiceKey() + " does not exist for resourceKey - " + resourceDefinition.getResourceUri());
                } else {
                	logger.info("serviceKey - " + resourceDefinition.getServiceKey() + " set for resourceKey - " + resourceDefinition.getResourceUri());
                }
            }
        }
    }
}
