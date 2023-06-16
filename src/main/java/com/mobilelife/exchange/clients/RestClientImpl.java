package com.mobilelife.exchange.clients;

import com.mobilelife.exchange.exception.ConversionException;
import com.mobilelife.exchange.model.response.FallbackRatesResponse;
import com.mobilelife.exchange.model.response.RatesResponse;
import com.mobilelife.exchange.utils.CommonUtil;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import java.math.BigDecimal;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class RestClientImpl implements RestClient {

  @Autowired
  private CacheManager cacheManager;
  @Autowired
  private RestTemplate restTemplate;
  @Value("${api.url}")
  private String url;
  @Value("${api.apiKey}")
  private String apiKey;
  @Value("${api.urlFallback}")
  private String urlFallback;
  @Value("${api.apiKeyFallback}")
  private String apiKeyFallback;
  @Value("${spring.cache.cache-names}")
  private String cacheName;
  @Value("${api.baseCurrency}")
  private String baseCurrency;
  @Value("${api.baseCurrencyFallback}")
  private String baseCurrencyFallback;

  @Async
  @CircuitBreaker(name = "ratesWS", fallbackMethod = "getRatesFromFallbackWS")
  public CompletableFuture<Map<String, BigDecimal>> getRatesFromWS() throws Exception {
    Cache cache = cacheManager.getCache(cacheName);
    if (cache != null) {
      log.info("Clearing cache");
      cache.clear();
    }
    URI uri = URI.create(url + "/latest?apikey=" + apiKey + "&base_currency=" + baseCurrency);
    ResponseEntity<RatesResponse> responseEntity = restTemplate.getForEntity(uri,
        RatesResponse.class);
    RatesResponse ratesResponse = responseEntity.getBody();
    if (cache != null && ratesResponse != null && ratesResponse.getData() != null) {
      ratesResponse.getData().forEach(cache::put);
      CommonUtil.setBaseCurrency(baseCurrency);
      return CompletableFuture.completedFuture(ratesResponse.getData());
    } else {
      throw new ConversionException("Cannot get values");
    }
  }

  @Async
  public CompletableFuture<Map<String, BigDecimal>> getRatesFromFallbackWS(Throwable throwable)
      throws Exception {
    log.error(throwable.getMessage());
    log.info("Executing fallback method");
    Cache cache = cacheManager.getCache(cacheName);
    if (cache != null) {
      log.info("Clearing cache");
      cache.clear();
    }
    URI uri = URI.create(urlFallback + "/latest?access_key=" + apiKeyFallback);
    ResponseEntity<FallbackRatesResponse> responseEntity = restTemplate.getForEntity(uri,
        FallbackRatesResponse.class);
    FallbackRatesResponse ratesResponse = responseEntity.getBody();
    if (cache != null && ratesResponse != null && ratesResponse.getRates() != null) {
      ratesResponse.getRates().forEach(cache::put);
      CommonUtil.setBaseCurrency(baseCurrencyFallback);
      return CompletableFuture.completedFuture(ratesResponse.getRates());
    }
    throw new ConversionException("Cannot get values");
  }

}
