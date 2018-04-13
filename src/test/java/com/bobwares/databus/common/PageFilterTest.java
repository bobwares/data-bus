package com.bobwares.databus.common;


import com.bobwares.databus.common.util.PageFilter;
import org.junit.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.LinkedList;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PageFilterTest {

    @Test
     public void testGetAllFilters() {
        MultiValueMap<String, String> allFilters = new LinkedMultiValueMap<>();
        LinkedList<String> linkedList = new LinkedList<>();
        linkedList.add("value_1");
        allFilters.put("af-value_1_key",linkedList);

        LinkedList<String> linkedList2 = new LinkedList<>();
        linkedList2.add("value_2");
        allFilters.put("filter-value_2_key",linkedList2);

        Map<String, String> map =  PageFilter.getFilters(allFilters);
        assertNotNull(map);
        String value_1 = map.get("value_1_key");
        assertNotNull(value_1);
        assertEquals(value_1,"value_1");

        String value_2 = map.get("value_2_key");
        assertNotNull(value_2);
        assertEquals(value_2,"value_2");
    }

    @Test
    public void testGetAutoFilters() {
        MultiValueMap<String, String> allFilters = new LinkedMultiValueMap<>();
        LinkedList<String> linkedList = new LinkedList<>();
        linkedList.add("value_1");
        allFilters.put("af-value_1_key",linkedList);

        Map<String, String> map =  PageFilter.getFilters("af-",allFilters);
        assertNotNull(map);
        String value_1 = map.get("value_1_key");
        assertNotNull(value_1);
        assertEquals(value_1,"value_1");
    }

    @Test
     public void testGetFilters() {
        MultiValueMap<String, String> allFilters = new LinkedMultiValueMap<>();
        LinkedList<String> linkedList = new LinkedList<>();
        linkedList.add("value_1");
        allFilters.put("filter-value_1_key",linkedList);

        Map<String, String> map =  PageFilter.getFilters("filter-",allFilters);
        assertNotNull(map);
        String value_1 = map.get("value_1_key");
        assertNotNull(value_1);
        assertEquals(value_1,"value_1");
    }


}
