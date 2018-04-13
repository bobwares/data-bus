package com.bobwares.databus.server.rest;

import com.bobwares.databus.server.registry.model.AuthorizationDefinition;
import com.bobwares.databus.common.model.DataBusReportObject;
import com.bobwares.databus.server.registry.model.ResourceDefinition;
import com.bobwares.databus.server.registry.model.ServiceDefinition;
import com.bobwares.databus.server.registry.service.RegistryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("databus-report")
public class DataBusReport {
    final Logger logger = LoggerFactory.getLogger(getClass());

    @Inject
    RegistryService<ResourceDefinition> resourceRegistry;

    @Inject
    RegistryService<ServiceDefinition> serviceRegistry;

    @Inject
    RegistryService<AuthorizationDefinition> authorizationRegistry;


    @RequestMapping(method = GET)
    public DataBusReportObject get() {
        DataBusReportObject dataBusReportObject = new DataBusReportObject();
        dataBusReportObject.setResources(resourceRegistry.getEntries());
        dataBusReportObject.setServiceDefinitions(serviceRegistry.getEntries());
        dataBusReportObject.setAuthorizationDefinitions(authorizationRegistry.getEntries());
        return dataBusReportObject;
    }
}
