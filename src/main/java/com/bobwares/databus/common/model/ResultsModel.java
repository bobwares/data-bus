package com.bobwares.databus.common.model;

import java.util.List;
import java.util.Map;

public interface ResultsModel {

	List<Map<String, Object>> getItems();

    void setItems(List<Map<String, Object>> items);
}
