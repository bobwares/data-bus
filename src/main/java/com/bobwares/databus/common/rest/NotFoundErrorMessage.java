package com.bobwares.databus.common.rest;

public class NotFoundErrorMessage extends ErrorMessage {

	private NotFoundErrorMessage() {
		setCode(ErrorMessageCode.NOT_FOUND.name());
	}

	public NotFoundErrorMessage(String field) {
		this();
		setField(field);
		setMessage(field + " not found");
	}

	public NotFoundErrorMessage(String field, String message) {
		this();
		setField(field);
		setMessage(message);
	}

}
