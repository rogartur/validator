package com.fx.trade.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class CurrencyWorkingDayService {

	protected final Logger log = LoggerFactory.getLogger(this.getClass());

	private final RestTemplate restTemplate;

	private final String accessKey;

	public CurrencyWorkingDayService(String accessKey) {
		this.restTemplate = new RestTemplate();
		this.accessKey = accessKey;
	}

	/**
	 * This is dummy service as valid, open-source one couldn't be found in
	 * time. Logic is still the same nonetheless. For the exercise purpose,
	 * always return true in the response in case of service malfunction. In
	 * order to check if this works, enable response.getSuccess() as a response
	 * parameter and enable correct application key in application.properties
	 * 
	 * @param currency
	 * @param date
	 * @return
	 */
	public boolean isWorkingDay(String currency, String date) throws RestClientException {
//		CurrencyResponse response = this.restTemplate.getForObject(
//				"http://apilayer.net/api/live?access_key={accesskey}&currencies={currency}&date={date}",
//				CurrencyResponse.class, accessKey, currency, date);
//		log.info("CurrencyWorkingDayService: Response from live service: {}", response);
//		return response.getSuccess();
		return true;
	}

	public static class CurrencyResponse {

		private boolean success;

		public CurrencyResponse() {
		}

		public boolean getSuccess() {
			return success;
		}

		public void setSuccess(boolean success) {
			this.success = success;
		}
	}
}
