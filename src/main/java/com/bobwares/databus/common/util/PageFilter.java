package com.bobwares.databus.common.util;

import com.google.common.collect.Maps;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Map;

/**
 * Static method for pulling filter data out of Map object.
 **/
public class PageFilter {

    public static String getKey(MultiValueMap<String, String> parameters, String key) {
        List<String> entries = parameters.get(key);
        if (entries != null) {
            return entries.get(0);
        }
        return null;
    }

    public static Map<String, String> getFilters(MultiValueMap<String, Object> allFilters) {
        Map<String, String> targetedFilters = Maps.newHashMap();
        extractFilters(allFilters, targetedFilters, "filter-");
        extractFilters(allFilters, targetedFilters, "af-");
        return targetedFilters;
    }

    public static Map<String, String> getFilters(String prefix, MultiValueMap<String, Object> allFilters) {
        Map<String, String> targetedFilters = Maps.newHashMap();
        extractFilters(allFilters, targetedFilters, prefix);
        return targetedFilters;
    }

    private static void extractFilters(MultiValueMap<String, Object> allFilters, Map<String, String> targetedFilters, String prefix) {
        for (Map.Entry<String,List<Object>> entry : allFilters.entrySet()) {
        	String key = entry.getKey();
        	if (key.startsWith(prefix)) {
                targetedFilters.put(key.substring(prefix.length()), (String) entry.getValue().get(0));
            }
        }
    }

}
