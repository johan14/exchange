package com.mobilelife.exchange.client;

import com.mobilelife.exchange.clients.RestClientImpl;
import com.mobilelife.exchange.exception.ConversionException;
import com.mobilelife.exchange.model.response.FallbackRatesResponse;
import com.mobilelife.exchange.model.response.RatesResponse;
import java.math.BigDecimal;
import java.net.URI;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.NoOpCache;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
public class RestClientTest {

  @Mock
  private RestTemplate restTemplate;
  @Mock
  private CacheManager cacheManager;
  @Value("${spring.cache.cache-names}")
  private String cacheName;
  @InjectMocks
  private RestClientImpl restClient;

  @Test
  void getRatesFromWS() {

    RatesResponse ratesResponse = new RatesResponse();
    ratesResponse.setData(Map.of("USD", BigDecimal.ONE));
    ResponseEntity<RatesResponse> ratesResponseResponseEntity = ResponseEntity.ok(ratesResponse);
    Mockito.when(cacheManager.getCache(cacheName)).thenReturn(new NoOpCache("name"));
    Mockito.when(restTemplate.getForEntity(Mockito.any(URI.class), Mockito.any(Class.class)))
        .thenReturn(ratesResponseResponseEntity);
    Assertions.assertDoesNotThrow(() -> restClient.getRatesFromWS());
  }

  @Test
  void getRatesFromWSWhenCacheNull() {

    RatesResponse ratesResponse = new RatesResponse();
    ratesResponse.setData(Map.of("USD", BigDecimal.ONE));
    ResponseEntity<RatesResponse> ratesResponseResponseEntity = ResponseEntity.ok(ratesResponse);
    Mockito.when(cacheManager.getCache(cacheName)).thenReturn(null);
    Mockito.when(restTemplate.getForEntity(Mockito.any(URI.class), Mockito.any(Class.class)))
        .thenReturn(ratesResponseResponseEntity);
    Assertions.assertThrows(ConversionException.class, () -> restClient.getRatesFromWS());
  }

  @Test
  void getRatesFromFallBackWS() {

    FallbackRatesResponse ratesResponse = new FallbackRatesResponse();
    ratesResponse.setRates(Map.of("USD", BigDecimal.ONE));
    ResponseEntity<FallbackRatesResponse> ratesResponseResponseEntity = ResponseEntity.ok(
        ratesResponse);
    Mockito.when(cacheManager.getCache(cacheName)).thenReturn(new NoOpCache("name"));
    Mockito.when(restTemplate.getForEntity(Mockito.any(URI.class), Mockito.any(Class.class)))
        .thenReturn(ratesResponseResponseEntity);
    Assertions.assertDoesNotThrow(() -> restClient.getRatesFromFallbackWS(new Throwable()));
  }

  @Test
  void getRatesFromFallBackWSWhenCacheNull() {

    FallbackRatesResponse ratesResponse = new FallbackRatesResponse();
    ratesResponse.setRates(Map.of("USD", BigDecimal.ONE));
    ResponseEntity<FallbackRatesResponse> ratesResponseResponseEntity = ResponseEntity.ok(
        ratesResponse);
    Mockito.when(cacheManager.getCache(cacheName)).thenReturn(null);
    Mockito.when(restTemplate.getForEntity(Mockito.any(URI.class), Mockito.any(Class.class)))
        .thenReturn(ratesResponseResponseEntity);
    Assertions.assertThrows(ConversionException.class,
        () -> restClient.getRatesFromFallbackWS(new Throwable()));
  }


}
