package com.bobwares.databus.server.registry.model;

import com.bobwares.databus.common.transformer.transformers.ColumnTransformer;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ColumnTransformerDefinition {

    final String columnTransformerKey;
    final ColumnTransformer columnTransformer;

    public ColumnTransformerDefinition(String columnTransformerKey, ColumnTransformer columnTransformer) {
        this.columnTransformerKey = columnTransformerKey;
        this.columnTransformer = columnTransformer;
    }

    public String getColumnTransformerKey() {
        return columnTransformerKey;
    }

    public ColumnTransformer getColumnTransformer() {
        return columnTransformer;
    }

	@Override
    public boolean equals(Object obj) {
    	return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
    	return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
