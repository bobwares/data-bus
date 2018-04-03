package com.bobwares.databus.common.model;


import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import lombok.Data;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

@Data
public class Field {
    String value;
    String label;
    String defaultValue;
    String property;
    String propertyAlias;
    boolean readOnly;
    boolean required;
    String type;
    String[] options;
    int width;
    boolean hidden;
    String regex;
    String regexMsg;
    String nullOption;
    boolean hasQualifiers;

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public boolean isHasQualifiers() {
        return hasQualifiers;
    }

    public void setHasQualifiers(boolean hasQualifiers) {
        this.hasQualifiers = hasQualifiers;
    }


    String alignment;


    String group;

    public String getPropertyAlias() {
        return propertyAlias;
    }

    public void setPropertyAlias(String propertyAlias) {
        this.propertyAlias = propertyAlias;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getAlignment() {
        return alignment;
    }

    public void setAlignment(String alignment) {
        this.alignment = alignment;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public String getRegexMsg() {
        return regexMsg;
    }

    public void setRegexMsg(String regexMsg) {
        this.regexMsg = regexMsg;
    }

    public String getNullOption() {
        return nullOption;
    }

    public void setNullOption(String nullOption) {
        this.nullOption = nullOption;
    }

    public ImmutableMap<String, Object> mapOf() {
        Map<String, Object> field = Maps.newHashMap();
        if (alignment != null) {
            // alignment is optional, but don't provide a default
            field.put("alignment", alignment);
        }
        field.put("hidden", hidden);
        if (group != null) {
            field.put("group", group);
        }
        if (label != null) {
            field.put("label", label);
        }
        if (nullOption != null) {
            field.put("nullOption", nullOption);
        }
        if (options != null) {
            field.put("options", options);
        }
        field.put("property", property);
        field.put("readOnly", readOnly);
        if (regex != null) {
            field.put("regex", regex);
        }
        if (regexMsg != null) {
            field.put("regexMsg", regexMsg);
        }
        field.put("required", required);
        field.put("type", type);
        field.put("width", width == 0 ? 100 : width);
        return ImmutableMap.copyOf(field);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Field that = (Field) o;
        return Objects.equals(readOnly, that.readOnly) &&
            Objects.equals(required, that.required) &&
            Objects.equals(width, that.width) &&
            Objects.equals(hidden, that.hidden) &&
            Objects.equals(value, that.value) &&
            Objects.equals(label, that.label) &&
            Objects.equals(property, that.property) &&
            Objects.equals(type, that.type) &&
            Objects.deepEquals(options, that.options) &&
            Objects.equals(regex, that.regex) &&
            Objects.equals(regexMsg, that.regexMsg) &&
            Objects.equals(nullOption, that.nullOption) &&
            Objects.equals(alignment, that.alignment) &&
            Objects.equals(group, that.group);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, label, property, readOnly, required, type, options, width, hidden, regex, regexMsg, nullOption, alignment, group);
    }


    public String toString() {
        return "ManageMyField{" +
            "value='" + value + '\'' +
            ", label='" + label + '\'' +
            ", property='" + property + '\'' +
            ", readOnly=" + readOnly +
            ", required=" + required +
            ", type='" + type + '\'' +
            ", options=" + Arrays.toString(options) +
            ", width=" + width +
            ", hidden=" + hidden +
            ", regex='" + regex + '\'' +
            ", regexMsg='" + regexMsg + '\'' +
            ", nullOption='" + nullOption + '\'' +
            ", alignment='" + alignment + '\'' +
            ", group='" + group + '\'' +
            '}';
    }
}
