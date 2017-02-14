package com.bobwares.databus.server.service.gateway;

import com.bobwares.databus.common.annotation.DataBusServiceComponent;
import com.bobwares.databus.common.model.ResourceConfiguration;
import com.bobwares.databus.common.model.ResponseObject;
import com.bobwares.databus.common.service.DataBusService;
import com.bobwares.databus.server.registry.model.ResourceDefinition;
import com.bobwares.databus.server.service.request.RequestObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.inject.Named;
import java.util.Map;

@DataBusServiceComponent
@Named("rest-gateway")
public class RestGateway implements DataBusService {

    final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public ResponseObject getRecords(ResourceDefinition resourceDefinition, RequestObject requestObject) {

        ResourceConfiguration resourceConfiguration = resourceDefinition.getResourceConfiguration();
        MultiValueMap<String, Object> parameters = requestObject.getParameters();


        HttpEntity<Map> response = getLocationResource(resourceConfiguration, parameters);

        Map responseBody = response.getBody();

        Integer superLocationId = (Integer) responseBody.get("id");

        parameters.set("superLocation", superLocationId);


        HttpEntity<Map> response2 = putLocationResource(resourceConfiguration, parameters);


        ResponseObject responseObject = new ResponseObject();

        return responseObject;
    }

    private HttpEntity<Map> getLocationResource(ResourceConfiguration resourceConfiguration, MultiValueMap<String,Object> inputMap) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set("accept", MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<?> requestEntity = new HttpEntity(requestHeaders);

        String uri = resourceConfiguration.getPaths().get("GET").getUri();
        String superLocation = (String) inputMap.toSingleValueMap().get("superLocation");

        HttpEntity<Map> response =
                restTemplate.exchange(
                        uri,
                        HttpMethod.GET,
                        requestEntity,
                        Map.class,
                        superLocation);

        logger.info("Get Location Record: " + response.getBody().toString());
        return response;
    }

    private HttpEntity<Map> putLocationResource( ResourceConfiguration resourceConfiguration, MultiValueMap<String,Object> inputMap) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set("accept", MediaType.APPLICATION_JSON_VALUE);
        requestHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<?> requestEntity = new HttpEntity(inputMap.toSingleValueMap(),requestHeaders);


        String uri = resourceConfiguration.getPaths().get("POST").getUri();

        HttpEntity<Map> response =
                restTemplate.exchange(
                        uri,
                        HttpMethod.POST,
                        requestEntity,
                        Map.class);

        logger.info("Create Location Record: " + response.getBody().toString());
        return response;
    }
}
