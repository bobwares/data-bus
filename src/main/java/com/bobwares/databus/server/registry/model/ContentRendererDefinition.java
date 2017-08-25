package com.bobwares.databus.server.registry.model;

import com.bobwares.databus.common.renderer.ContentRenderer;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ContentRendererDefinition {

    final String contentRendererKey;
    final ContentRenderer contentRenderer;

    public ContentRendererDefinition(String contentRendererKey, ContentRenderer contentRenderer) {
        this.contentRendererKey = contentRendererKey;
        this.contentRenderer = contentRenderer;
    }

    public String getContentRendererKey() {
        return contentRendererKey;
    }

    public ContentRenderer getContentRenderer() {
        return contentRenderer;
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
