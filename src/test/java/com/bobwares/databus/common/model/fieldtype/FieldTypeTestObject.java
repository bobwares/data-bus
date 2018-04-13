package com.bobwares.databus.common.model.fieldtype;

import com.bobwares.databus.common.renderer.pdf.Alignment;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.util.Map;

public class FieldTypeTestObject implements FieldTypeDefinition {
    @Override
    public String getName() {
        return null;
    }

    @Override
    public Alignment getAlignment() {
        return null;
    }

    @Override
    public String format(String input) {
        return null;
    }

    @Override
    public MapSqlParameterSource addField(MapSqlParameterSource paramSource, Map<String, String> filters, String property) {
        return null;
    }

    @Override
    public MapSqlParameterSource addFilter(MapSqlParameterSource paramSource, Map<String, String> filters, String property) {
        return null;
    }
}
