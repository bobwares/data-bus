package com.bobwares.databus.common.rest;

import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class ErrorMessageException extends RuntimeException {

	private Collection<ErrorMessage> errorMessages = new ArrayList<>();
	private HttpStatus status = HttpStatus.BAD_REQUEST;

	public ErrorMessageException() {
		super();
	}

	public ErrorMessageException(Throwable cause) {
		super(cause);
	}

	public ErrorMessageException(String field, String code, String message) {
		super();
		addErrorMessage(field, code, message);
	}

	public ErrorMessageException(ErrorMessage errorMessage) {
		super();
		addErrorMessage(errorMessage);
	}

	@Override
	public String getMessage() {
		StringBuilder sb = new StringBuilder();
		for (ErrorMessage em : errorMessages) {
			sb.append(em.toString()).append("; ");
		}
		if (sb.length() > 0) sb.setLength(sb.length() - 2); //remove trailing "; "
		return sb.toString();
	}

	public Collection<ErrorMessage> getErrorMessages() {
		return errorMessages;
	}

	public ErrorMessageException addErrorMessages(Collection<ErrorMessage> errorMessages) {
		if (errorMessages != null) this.errorMessages.addAll(errorMessages);
		return this;
	}

	public ErrorMessageException addErrorMessage(ErrorMessage errorMessage) {
		if (errorMessage != null) this.errorMessages.add(errorMessage);
		return this;
	}

	public ErrorMessageException addErrorMessage(String field, String code, String message) {
		this.errorMessages.add(new ErrorMessage()
			.setField(field)
			.setCode(code)
			.setMessage(message)
		);
		return this;
	}



	public HttpStatus getStatus() {
		return status;
	}

	public ErrorMessageException setStatus(HttpStatus status) {
		this.status = status;
		return this;
	}

	public void throwIfHasErrors() {
		if (errorMessages != null && !errorMessages.isEmpty()) {
			throw this;
		}
	}

	public static void throwIfHasErrors(Collection<ErrorMessage> errorMessages) {
		if (errorMessages != null && !errorMessages.isEmpty()) {
			throw new ErrorMessageException()
				.addErrorMessages(errorMessages)
			;
		}
	}

	public static void throwIfHasErrors(Collection<ErrorMessage> errorMessages, HttpStatus status) {
		if (errorMessages != null && !errorMessages.isEmpty()) {
			throw new ErrorMessageException()
				.addErrorMessages(errorMessages)
				.setStatus(status)
			;
		}
	}

}
