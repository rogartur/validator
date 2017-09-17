package com.fx.trade.exception;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RestException {

	public final String error;
	public final String description;

	public RestException(Exception ex, String description) {
		this.error = ex.getMessage();
		this.description = description;
	}
}
