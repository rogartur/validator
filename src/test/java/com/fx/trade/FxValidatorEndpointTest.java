package com.fx.trade;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fx.trade.rest.FXValidatorEndpoint;

import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@WebMvcTest(value = FXValidatorEndpoint.class)
@TestPropertySource(locations="classpath:test.properties")
public class FxValidatorEndpointTest {

	@Autowired
	private MockMvc mockMvc;

	@Value("${incorrect.customer.json}")
	private String incorrectCustomerJSON;
	
	@Value("${incorrect.customer.response}")
	private String incorrectCustomerResponse;
	
	@Value("${incorrect.value.date.json}")
	private String incorrectValueDateJSON;
	
	@Value("${incorrect.value.date.response}")
	private String incorrectValueDateResponse;
	
	@Value("${incorrect.currency.json}")
	private String incorrectCurrencyJSON;
	
	@Value("${incorrect.currency.response}")
	private String incorrectCurrencyResponse;
	
	@Value("${correct.json}")
	private String correctJSON;

	@Test
	public void postIncorrectCustomer_expectError() throws Exception {

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/validate")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(incorrectCustomerJSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		ObjectMapper mapper = new ObjectMapper();
	    JsonNode responseObj = mapper.readTree(result.getResponse().getContentAsString());
	    JsonNode expectedObj = mapper.readTree(incorrectCustomerResponse);
		
		assertEquals(expectedObj.toString(), responseObj.toString(), false);

	}
	
	@Test
	public void postIncorrectValueDate_expectError() throws Exception {

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/validate")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(incorrectValueDateJSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		ObjectMapper mapper = new ObjectMapper();
	    JsonNode responseObj = mapper.readTree(result.getResponse().getContentAsString());
	    JsonNode expectedObj = mapper.readTree(incorrectValueDateResponse);
		
		assertEquals(expectedObj.toString(), responseObj.toString(), false);

	}
	
	@Test
	public void postIncorrectCurrency_expectError() throws Exception {

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/validate")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(incorrectCurrencyJSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		ObjectMapper mapper = new ObjectMapper();
	    JsonNode responseObj = mapper.readTree(result.getResponse().getContentAsString());
	    JsonNode expectedObj = mapper.readTree(incorrectCurrencyResponse);
		
		assertEquals(expectedObj.toString(), responseObj.toString(), false);

	}
	
	@Test
	public void postCorrectJSON_expectOK() throws Exception {

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/validate")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(correctJSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		assertEquals(result.getResponse().getStatus(), HttpStatus.OK.value());

	}

}
