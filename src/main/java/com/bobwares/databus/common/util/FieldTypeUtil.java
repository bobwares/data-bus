package com.bobwares.databus.common.util;

import com.bobwares.core.bean.Registry;
import com.bobwares.databus.common.model.fieldtype.FieldTypeDefinition;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class FieldTypeUtil {

	public static FieldTypeDefinition getFieldType(String name) {
		Map<String, FieldTypeDefinition> FIELD_TYPE_MAP = buildFieldTypeMap();
		return FIELD_TYPE_MAP.get(name.toLowerCase());
	}

    protected static Map<String, FieldTypeDefinition> buildFieldTypeMap() {
    	Map<String, FieldTypeDefinition> fieldTypeMap = new HashMap<>();
		Collection<FieldTypeDefinition> fieldTypes = Registry.getBeans("fieldTypeDefinitionRegistry", FieldTypeDefinition.class);
		for (FieldTypeDefinition fieldType : fieldTypes) {
			fieldTypeMap.put(fieldType.getName().toLowerCase(), fieldType);
		}
    	return fieldTypeMap;
    }

}
