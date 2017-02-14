package com.bobwares.databus.common.rest;

public class ErrorMessage {
	
	private String field;
	private String code;
	private String message;
	
	public String getField() {
		return field;
	}
	
	public ErrorMessage setField(String field) {
		this.field = field;
		return this;
	}
	
	public String getCode() {
		return code;
	}
	
	public ErrorMessage setCode(String code) {
		this.code = code;
		return this;
	}
	
	public String getMessage() {
		return message;
	}
	
	public ErrorMessage setMessage(String message) {
		this.message = message;
		return this;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(64);
		if (field != null && field.length() > 0) sb.append("field=").append(field).append(" ");
		if (code != null && code.length() > 0) sb.append("code=").append(code).append(" ");
		if (message != null && message.length() > 0) sb.append("message=").append(message).append(" ");
		if (sb.length() > 0) {
			sb.setLength(sb.length() - 1); //remove trailing space
			return sb.toString();
		}
		
		return super.toString();
	}

}
