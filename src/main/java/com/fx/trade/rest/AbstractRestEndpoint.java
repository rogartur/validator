package com.fx.trade.rest;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.client.RestClientException;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fx.trade.exception.RestException;
import com.fx.trade.exception.ValidationException;
import com.fx.trade.validator.TradeDataListValidator;

public abstract class AbstractRestEndpoint {

	protected final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	protected MessageSource messageSource;

	@Value("${currency.access.key}")
	private String accessKey;
	
	@InitBinder("tradeDataWrapper")
	protected void tradeDataListBinder(WebDataBinder binder) {
		binder.setValidator(new TradeDataListValidator(accessKey));
	}

	@ExceptionHandler({ InvalidFormatException.class })
	public ResponseEntity<?> handleException(InvalidFormatException ex) {
		RestException response = new RestException(ex, getMessage("invalid.input.message"));
		return ResponseEntity.badRequest().body(response);
	}

	@ExceptionHandler({ HttpMessageNotReadableException.class })
	public ResponseEntity<?> handleException(HttpMessageNotReadableException ex) {
		RestException response = new RestException(ex, getMessage("malformed.json.message"));
		return ResponseEntity.badRequest().body(response);
	}

	@ExceptionHandler({ RestClientException.class })
	public ResponseEntity<?> handleException(RestClientException ex) {
		RestException response = new RestException(ex, getMessage("rest.exception.message"));
		return ResponseEntity.badRequest().body(response);
	}

	public String getMessage(String code) {
		return messageSource.getMessage(code, null, new Locale("en"));
	}

	public ValidationException getValidationError(FieldError err) {
		ValidationException error = new ValidationException(err.getField(), getMessage(err.getCode()));
		return error;
	}
}