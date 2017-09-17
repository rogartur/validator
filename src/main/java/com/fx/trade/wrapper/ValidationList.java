package com.fx.trade.wrapper;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.fx.trade.exception.ValidationException;

@XmlRootElement(name = "validationErrors")
public class ValidationList {

	public ValidationList() {
	}

	private List<ValidationException> validationErrors;

	public List<ValidationException> getValidationErrors() {
		return validationErrors;
	}

	public void setValidationErrors(List<ValidationException> validationErrors) {
		this.validationErrors = validationErrors;
	}
}
