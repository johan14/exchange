# Exchange

This is an currency exchange application which utilizes external web services for currency conversions.

## Table of Contents

- [Intro](#intro)
- [Installation](#installation)
- [Usage](#usage)
- [Features](#features)
- [Structure](#structure)
- [API Documentation](#api-documentation)
- [Testing Coverage](#testing-coverage)

## Intro

This is a Spring Boot application which serves HTTP requests in order to convert between currencies at latest rates. The application utilizes multiple exchange rates providers. 

## Installation

Running these commands:

`mvn clean install`

`java -jar target/exchange-0.0.1-SNAPSHOT.jar`

## Usage

After running the application, you can hit `localhost:8081/api/v1/convert` endpoint with a POST request, which body is as below: 

```
{
"from" : string ("EUR", "USD", etc) ,
"to" : string ("EUR", "USD", etc) ,
"amount" : number 
}
```

## Features

By validating inputs such as currency names and amount, /convert endpoint returns the amount calculated by rates which are stored in a **Caffeine cache**. If one of the providers API is down for any reason, a **circuit breaker** is used for falling back into another provider API. 

For every request, there is an **async** update of the cache in case the cache is empty, and also there is a scheduled job in place which updates the cache once in a while. 

## Structure

Sequence diagram: 

![image](https://github.com/johan14/exchange/assets/28931298/103f494a-2164-426d-b753-1abd30db5aa0)


## API Documentation

This application uses **Swagger** for API documentation. In this case there is only one endpoint:


![image](https://github.com/johan14/exchange/assets/28931298/8639ea9b-ecd7-497a-8411-3ad26ae2e932)


## Testing Coverage

There is a 92% testing coverage using **Mockito** and **JUnit** libraries for mocking and asserting.


![image](https://github.com/johan14/exchange/assets/28931298/026a46da-7fd2-419d-adff-dd1b46ec0837)




