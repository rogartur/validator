Copyright (c) Artur Róg 2017 : FX transactions validator

# Overview

Application contains RESTful service designed to validate group of FX transactions.
Application utilizes Spring Boot and it's libraries to create the endpoints. There is no repository layer, 
as application does not store any data.

## Technologies used

- Spring Boot 1.5.6.RELEASE
- Maven (Build tool)
- Java 1.8
- Tomcat 8 (Embedded server)
- Swagger 2.7.0 (Online documentation)
- Metrics 3.1.2
- jUnit 4.12 (Unit tests)

## REST Services

### Validation endpoint

*validate*
```
POST /api/v1/validate
```	
- body param: json
- produces: json/xml

*metrics*
```
GET /metrics
```	
- produces: json

*shutdown*
```
POST /shutdown
```	
- produces: json

# REST Server

Spring Boot comes with out of the box Tomcat embedded server, avaiable after building the project.

## Running Tomcat Embedded Server

```
mvn clean install
cd target
java -jar validator-v1.jar
```

## Graceful shutdown of Tomcat server

Use of Spring Actuator allows for the Tomcat server to be shut down gracefully. 
Shutdown is enabled by default via command:
```
curl -XPOST https://localhost:8080/shutdown -k
```
Successful call results in JSON response from server:
```
{"message": "Shutting down, bye..."}
```
In the production system, this endpoint should be secured by Spring Security authorization.

## Unit tests

*FxValidatorEndpointTest* provides few basic Unit Tests around */validate* endpoint.
Tests are started automatically on maven build.

Sample build output:

```
Results :

Tests run: 5, Failures: 0, Errors: 0, Skipped: 0
```

# Services usage

### /validate

Validation service consumes JSON list in the following format:

```
{"dataList":[  
   {  
      "customer":"",
      "ccyPair":"",
      "type":"",
      "style":"",
      "direction":"",
      "strategy":"",
      "tradeDate ":"",
      "amount1":0,
      "amount2":0,
      "rate":0,
      "valueDate":"",
      "deliveryDate":"",
      "expiryDate":"",
      "excerciseStartDate":"",
      "payCcy":"",
      "premium":,
      "premiumCcy":"",
      "premiumType":"",
      "premiumDate":"",
      "legalEntity":"",
      "trader":""
   }
]}
```

Fields are validated according to business logic rules. In case of errors occurence, endpoint returns JSON information,
with the object where error occurred, field, and error message. Sample response containing HTTP Response Code 400, and
JSON:

```
{"validationErrors": [
      {
      "field": "dataList[3].tradeDate",
      "exception": "Field tradeDate is empty"
   },
      {
      "field": "dataList[4].amount1",
      "exception": "Field amount1 is empty"
   }
]}
```

Service responds to a correct JSON by sending HTTP Response Code 200 and adding simple information:

```
Valid.
```

Application also sends all of the response codes and messages to the console. 

Validation endpoint can easily by tested thanks to Swagger-ui, providing web interface with description of validation endpoint
and client to test it with live data. Swagger-ui is accessible in the browser, at the address:
```
http://localhost:8080/swagger-ui.html
```

### /metrics

Validation endpoint is configured to send the information about processed requests back to the application, as well as general
application data, for further reviewing at the following endpoint:

```
http://localhost:8080/metrics
```

# External services

Application uses couple of external endpoints from third-party providers to help validate data in dynamic way. 

## CurrencyISOCheckService

This service call endpoint:

```
https://restcountries.eu/rest/v2/currency/{currency}
```

Which validates country code according to ISO 4217 standard. If the HTTP status is ok, then the currency is valid to use.

## CurrencyWorkingDayService

This is a dummy service to check if given date is working date of a currency. Due to fact, that this service is not really
providing this information, we're just checking for a success status in response object.

```
http://apilayer.net/api/live?access_key={accesskey}&currencies={currency}&date={date}
```

Access key is exposed in property files, and is valid only for a number of calls, due to service limitations. 

# Extending application

In case of necessity to extend currently existing validation rules, it is possible to add those to the *TradeDataListValidator* 
class, analogically to what's in there at the moment. 

In case of adding new FX POJO classes like *TradeData*, it is possible to add new Binder configurations to the *AbstractRestEndpoint*.
