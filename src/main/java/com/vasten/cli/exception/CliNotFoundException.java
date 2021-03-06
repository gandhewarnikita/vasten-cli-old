package com.vasten.cli.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import com.vasten.cli.error.ValidationError;

import java.util.List;

import org.springframework.http.HttpStatus;

@ResponseStatus(code=HttpStatus.NOT_FOUND)
public class CliNotFoundException extends RuntimeException {

private static final long serialVersionUID = 1L;
	
	private List<ValidationError> validateErrors;

	public CliNotFoundException(String exception, List<ValidationError> validateErrors) {
		super(exception);
		this.validateErrors = validateErrors;
	}

	public List<ValidationError> getValidateErrors() {
		return validateErrors;
	}

	public void setValidateErrors(List<ValidationError> validateErrors) {
		this.validateErrors = validateErrors;
	}
}
