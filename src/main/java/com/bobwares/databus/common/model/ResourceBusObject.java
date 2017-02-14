package com.bobwares.databus.common.model;

import java.util.List;
import java.util.Map;

public class ResourceBusObject implements ResultsModel {

    private List<Map<String, Object>> items;

    @Override
    public List<Map<String, Object>> getItems() {
        return items;
    }

    @Override
    public void setItems(List<Map<String, Object>> items) {
        this.items = items;
    }
}
