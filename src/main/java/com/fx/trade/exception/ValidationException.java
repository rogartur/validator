package com.fx.trade.exception;

public class ValidationException {

	public final String field;
	public final String exception;

	public ValidationException(String field, String exception) {
		this.field = field;
		this.exception = exception;
	}

	@Override
	public String toString() {
		return "ValidationException [field=" + field + ", exception=" + exception + "]";
	}
	
}
