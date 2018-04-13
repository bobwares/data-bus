package com.bobwares.databus.common.service;


import com.bobwares.databus.common.annotation.DataBusServiceComponent;
import com.bobwares.databus.common.dao.DataBusDao;
import com.bobwares.databus.common.model.PagingObject;
import com.bobwares.databus.common.model.ResponseObject;
import com.bobwares.databus.common.transformer.service.ColumnTransformerService;
import com.bobwares.databus.server.registry.model.ResourceDefinition;
import com.bobwares.databus.server.service.request.RequestObject;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Map;

@DataBusServiceComponent
@Named("storedProcedureService")
public class StoredProcedureService implements DataBusService {

    @Inject
    @Named("dataBusDao")
    private DataBusDao dataBusDao;

    @Inject
    ColumnTransformerService columnTransformationService;

    @Override
    public ResponseObject getRecords(ResourceDefinition resourceDefinition, RequestObject requestObject) {
        PagingObject pagingObject = dataBusDao.getRecords(resourceDefinition.getResourceConfiguration(), requestObject.getFilters(), requestObject.getPageNum(), requestObject.getPageSize(), requestObject.getSort(), requestObject.isReverse());
        List<Map<String, Object>> transformRows = columnTransformationService.transform(requestObject.getResourceUri(), pagingObject.getItems());
        pagingObject.setItems(transformRows);
        ResponseObject responseObject = new ResponseObject();
        responseObject.setResultsModel(pagingObject);
        return responseObject;
    }
}