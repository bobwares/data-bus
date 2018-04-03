package com.bobwares.databus.common.renderer.pdf;

import com.bobwares.databus.common.model.fieldtype.FieldTypeDefinition;
import com.bobwares.databus.common.util.FieldTypeUtil;

import java.util.Date;

/**
 * This class duplicates functionality in /content/common/feature/share/filters.js, in order to support
 * report generation to PDF on the backend.
 */
public class Filters {

    private Filters() {
    }

    public static String filter(String type, Object value) {
    	return filter(FieldTypeUtil.getFieldType(type), value);
    }

    public static String filter(FieldTypeDefinition fieldType, Object value) {
        if (value == null) return "";

        String strValue = value instanceof Date
        	? String.valueOf(((Date)value).getTime())
        	: String.valueOf(value)
        ;
        if (strValue == null || strValue.length() == 0) return "";

        return fieldType.format(strValue);
    }

}

