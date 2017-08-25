package com.bobwares.databus.common.renderer.pdf;

import com.google.common.collect.ImmutableMap;
import com.bobwares.databus.common.model.fieldtype.FieldTypeDefinition;
import com.bobwares.databus.common.util.FieldTypeUtil;

import java.util.Map;
import java.util.Objects;

import static com.google.common.collect.ImmutableMap.copyOf;

public class Column {

    private static final String ALIGNMENT_KEY = "alignment";
    private static final String GROUP_KEY = "group";
    private static final String LABEL_KEY = "label";
    private static final String NULL_OPTION_KEY = "nullOption";
    private static final String PROPERTY_KEY = "property";
    private static final String TYPE_KEY = "type";
    private static final String WIDTH_KEY = "width";

    private final ImmutableMap<String, Object> field;
    private final int index;

    private FieldTypeDefinition fieldType;

    public Column(Map<String, Object> field, int index) {
        this.field = copyOf(field);
        this.index = index;
    }

    public Alignment getAlignment() {
        Alignment alignment = null;
        // is it overridden?
        Object value = field.get(ALIGNMENT_KEY);
        if (value != null) {
            alignment = Alignment.lookup(String.valueOf(value));
        }
        return alignment != null ? alignment : getType().getAlignment();
    }

    public String getGroup() {
        return Objects.toString(field.get(GROUP_KEY), null);
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return Objects.toString(field.get(LABEL_KEY),null);
    }

    public String getNullOption() {
        return Objects.toString(field.get(NULL_OPTION_KEY), null);
    }

    public String getProperty() {
        return Objects.toString(field.get(PROPERTY_KEY),null);
    }

    public float getWidth() {
        float width = Float.parseFloat(String.valueOf(field.get(WIDTH_KEY)));
        assert (width > 0) : "column width must be greater than zero";
        return width;
    }

    public FieldTypeDefinition getType() {
    	if (fieldType == null) {
    		String typeName = String.valueOf(field.get(TYPE_KEY));
    		this.fieldType = FieldTypeUtil.getFieldType(typeName);
    	}
    	assert (fieldType != null) : "column type cannot be null";
    	return fieldType;
    }
}
