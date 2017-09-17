package com.fx.trade.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class CurrencyISOCheckService {

	protected final Logger log = LoggerFactory.getLogger(this.getClass());

	private final RestTemplate restTemplate;

	public CurrencyISOCheckService() {
		this.restTemplate = new RestTemplate();
	}

	/**
	 * 
	 * @param currency
	 * @param date
	 * @return
	 */
	public boolean isISOCurrency(String currency) throws RestClientException {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<?> entity = new HttpEntity<>(headers);
		HttpStatus responseStatus;

		try {
			ResponseEntity<?> response = this.restTemplate.exchange(
					"https://restcountries.eu/rest/v2/currency/{currency}", HttpMethod.GET, entity, String.class,
					currency);
			responseStatus = response.getStatusCode();
		} catch (final HttpClientErrorException e) {
			responseStatus = e.getStatusCode();
		}

		log.info("CurrencyISOCheckService: Response from live service: {}", responseStatus);

		return responseStatus == HttpStatus.OK;
	}

}
