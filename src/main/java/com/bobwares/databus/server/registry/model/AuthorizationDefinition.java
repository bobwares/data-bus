package com.bobwares.databus.server.registry.model;


import com.bobwares.databus.server.authorization.AuthorizationProvider;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;
import java.util.Map;

public class AuthorizationDefinition {

    private String authKey;
    private String authorizationProviderKey;
    private AuthorizationProvider authorizationProvider;
    private List<String> roles;
    private String permission;
    private String ipRestriction;
    private List<String> profiles;
    private Long targetId;
    private String targetTable;
    private boolean impersonated;
    private boolean authenticated = true;
    private boolean encryptedParameters;
    private Map<String,Object> params;

    public String getAuthKey() {
        return authKey;
    }

    public AuthorizationDefinition setAuthKey(String authKey) {
        this.authKey = authKey;
		return this;
    }

    public String getAuthorizationProviderKey() {
        return authorizationProviderKey;
    }

    public AuthorizationDefinition setAuthorizationProviderKey(String authorizationProviderKey) {
        this.authorizationProviderKey = authorizationProviderKey;
		return this;
    }

    public AuthorizationProvider getAuthorizationProvider() {
        return authorizationProvider;
    }

    public AuthorizationDefinition setAuthorizationProvider(AuthorizationProvider authorizationProvider) {
        this.authorizationProvider = authorizationProvider;
		return this;
    }

    public List<String> getRoles() {
        return roles;
    }

    public AuthorizationDefinition setRoles(List<String> roles) {
        this.roles = roles;
		return this;
    }

    public String getPermission() {
		return permission;
	}

	public AuthorizationDefinition setPermission(String permission) {
		this.permission = permission;
		return this;
	}

	public String getIpRestriction() {
		return ipRestriction;
	}

	public AuthorizationDefinition setIpRestriction(String ipRestriction) {
		this.ipRestriction = ipRestriction;
		return this;
	}

	public List<String> getProfiles() {
		return profiles;
	}

	public AuthorizationDefinition setProfiles(List<String> profiles) {
		this.profiles = profiles;
		return this;
	}

	public boolean getImpersonated() {
		return impersonated;
	}

	public AuthorizationDefinition setImpersonated(boolean impersonated) {
		this.impersonated = impersonated;
		return this;
	}

	public boolean getAuthenticated() {
		return authenticated;
	}

	public AuthorizationDefinition setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
		return this;
	}

	public boolean getEncryptedParameters() {
		return encryptedParameters;
	}

	public AuthorizationDefinition setEncryptedParameters(boolean encryptedParameters) {
		this.encryptedParameters = encryptedParameters;
		return this;
	}

	public Long getTargetId() {
		return targetId;
	}

	public AuthorizationDefinition setTargetId(Long targetId) {
		this.targetId = targetId;
		return this;
	}

	public String getTargetTable() {
		return targetTable;
	}

	public AuthorizationDefinition setTargetTable(String targetTable) {
		this.targetTable = targetTable;
		return this;
	}

    public Map<String, Object> getParams() {
        return params;
    }

    public AuthorizationDefinition setParams(Map<String, Object> params) {
        this.params = params;
		return this;
    }

	public String getParameter(String parameterName) {
    	Object obj = this.getParams().get(parameterName);
        return obj != null
        	? obj.toString()
        	: null
        ;
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
