package com.bobwares.loader.util;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;


public class MapToJsonImplTest {
    @Test
    public void convObjToONode() throws Exception {
        MapToJson mapToJson = new MapToJsonImpl();
        Map<String,List<Map<String, Object>>> row = buildTestData();
        ObjectNode jsonNodes = mapToJson.convObjToONode(row);
        assertNotNull(jsonNodes);
    }

    private Map<String,List<Map<String, Object>>> buildTestData() {
        Map<String, List<Map<String, Object>>> node = new HashMap<>();
        List<Map<String, Object>> list = new ArrayList<>();

        Map<String,Object> row = new HashMap();
        row.put("locationName","Unit Operations Lab");
        row.put("superLocation","Building Q");
        row.put("geotype","Building");
        List<String> mappingArray = new ArrayList<>();
        mappingArray.add("Q3 CAGE");
        mappingArray.add("Q3 Cage Pallet");
        mappingArray.add("Q3 CAGE/Q108");
        row.put("mapping",mappingArray);

        list.add(row);
        node.put("root",list);
        return node;
    }
}