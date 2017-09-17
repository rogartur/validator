package com.fx.trade.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.fx.trade.exception.ValidationException;
import com.fx.trade.wrapper.TradeDataWrapper;
import com.fx.trade.wrapper.ValidationList;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(path = "/api/v1")
@Api(tags = { "validator" })
public class FXValidatorEndpoint extends AbstractRestEndpoint {
	
	@Timed
	@RequestMapping(path = "/validate", method = RequestMethod.POST, consumes = "application/json")
	@ApiOperation(value = "Validate FX operation.", notes = "Returns code of the operation and/or errors.")
	public ResponseEntity<?> validateAll(@Valid @RequestBody TradeDataWrapper tradeDataWrapper, Errors errors) {
		
		ValidationList response = new ValidationList();

		if (errors.hasErrors()) {

			log.info("FXValidatorEndpoint: Validated request contains errors!");

			List<ValidationException> errorList = new ArrayList<ValidationException>();

			errorList.addAll(errors.getAllErrors().stream().map(x -> getValidationError((FieldError) x))
					.collect(Collectors.toCollection(ArrayList::new)));

			log.info("Errors list: ");
			log.info(StringUtils.join(errorList, ","));

			response.setValidationErrors(errorList);

			return ResponseEntity.badRequest().body(response);
		}

		log.info("FXValidatorEndpoint: Request validated, no errors");

		return ResponseEntity.ok().body(getMessage("input.valid"));
	}
}
