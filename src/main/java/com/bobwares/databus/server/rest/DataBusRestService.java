package com.bobwares.databus.server.rest;


import com.bobwares.databus.common.model.Field;
import com.bobwares.databus.common.model.ResponseObject;
import com.bobwares.databus.common.service.RequestUriParser;
import com.bobwares.databus.server.renderer.ContentRendererService;
import com.bobwares.databus.server.service.request.DataBusRequestGateway;
import io.swagger.annotations.Api;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerMapping;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("databus")
@Api(tags="databus",description="DataBus services")
public class DataBusRestService {

    private RequestUriParser requestUriParser;

    @Inject
    public void setRequestKeyParser(RequestUriParser requestUriParser) {
        this.requestUriParser = requestUriParser;
    }

    private DataBusRequestGateway dataBusExternalRequestGateway;

    @Inject
    public void setDataBusRequestGateway(DataBusRequestGateway dataBusExternalRequestGateway) {
        this.dataBusExternalRequestGateway = dataBusExternalRequestGateway;
    }

    private ContentRendererService contentRendererService;

    @Inject
    public void setContentRendererService(ContentRendererService contentRendererService) {
        this.contentRendererService = contentRendererService;
    }

    @RequestMapping(value="/**", method=RequestMethod.GET)
    public Object getResource(HttpServletRequest request) throws Exception {
        String resourceKey = getResourceUri(request);
        MultiValueMap<String, Object> parameters = getParameters(request);
        return processRequest(parameters, resourceKey);
    }

    private String getResourceUri(HttpServletRequest request) {
        return requestUriParser.parse((String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE));
    }

    private Object processRequest(MultiValueMap<String, Object> parameters, String resourceKey) throws Exception {
        ResponseObject responseObject = dataBusExternalRequestGateway.processRequest(parameters, resourceKey);
        return contentRendererService.render(resourceKey, responseObject, parameters);
    }

    @RequestMapping(value="/**/field", method=RequestMethod.GET)

    public List<Field> getResourceFields(HttpServletRequest request) {
        String resourceKey = getResourceUri(request);
        return field(resourceKey);
    }

    private List<Field> field(String resourceKey) {
        return dataBusExternalRequestGateway.getResourceFields(resourceKey);
    }

    private MultiValueMap<String, Object> getParameters(HttpServletRequest request) {
    	Map<String, String[]> parameterMap = request.getParameterMap();
    	if (parameterMap == null) return new LinkedMultiValueMap<>();

	    MultiValueMap<String, Object> result = new LinkedMultiValueMap<>(parameterMap.size());
		for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
			for (String value : entry.getValue()) {
				result.add(entry.getKey(), value);
			}
		}
		return result;
    }
}

