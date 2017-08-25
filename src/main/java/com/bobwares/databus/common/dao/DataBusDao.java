package com.bobwares.databus.common.dao;


import com.bobwares.databus.common.model.ResourceConfiguration;
import com.bobwares.databus.common.model.PagingObject;

import java.util.Map;

public interface DataBusDao {
    PagingObject getRecords(ResourceConfiguration configuration, Map<String, String> filters, Integer pageNum, Integer pageSize, String sortBy, Boolean reverse);
}
