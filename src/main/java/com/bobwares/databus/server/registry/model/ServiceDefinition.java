package com.bobwares.databus.server.registry.model;

import com.bobwares.databus.common.service.DataBusService;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ServiceDefinition {

    final String serviceKey;
    final DataBusService dataBusService;

    public ServiceDefinition(String serviceKey, DataBusService dataBusService) {
        this.serviceKey = serviceKey;
        this.dataBusService = dataBusService;
    }

    public DataBusService getDataBusService() {
        return dataBusService;
    }

    public String getServiceKey() {
        return serviceKey;
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
