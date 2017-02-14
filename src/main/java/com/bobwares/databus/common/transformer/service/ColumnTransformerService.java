package com.bobwares.databus.common.transformer.service;

import java.util.List;
import java.util.Map;

public interface ColumnTransformerService {

    List<Map<String, Object>> transform(String resourceKey, List<Map<String, Object>> rows);

}
