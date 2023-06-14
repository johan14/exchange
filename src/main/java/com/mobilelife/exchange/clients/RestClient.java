package com.mobilelife.exchange.clients;

import com.mobilelife.exchange.model.BackupRatesResponse;
import com.mobilelife.exchange.model.RatesResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import java.math.BigDecimal;
import java.net.URI;
import java.util.Collections;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class RestClient {

  @Autowired
  CacheManager cacheManager;
  @Autowired
  RestTemplate restTemplate;
  @Value("${api.url}")
  private String url;
  @Value("${api.apiKey}")
  private String apiKey;
  @Value("${api.urlBackup}")
  private String urlBackup;
  @Value("${api.apiKeyBackup}")
  private String apiKeyBackup;
  @Value("${spring.cache.cache-names}")
  private String cacheName;
  @Value("${api.baseCurrency}")
  private String baseCurrency;

  @CircuitBreaker(name = "ratesWS", fallbackMethod = "getRatesFromBackupWS")
  public Map<String, BigDecimal> getRatesFromWS() {
    Cache cache = cacheManager.getCache(cacheName);
    URI uri = URI.create(url + "/latest?apikey=" + apiKey + "&base_currency=" + baseCurrency);
    ResponseEntity<RatesResponse> responseEntity = restTemplate.getForEntity(uri,
        RatesResponse.class);
    RatesResponse ratesResponse = responseEntity.getBody();
    if (cache != null && ratesResponse != null && ratesResponse.getData() != null) {
      ratesResponse.getData().forEach(cache::put);
      return ratesResponse.getData();
    }
    return Collections.emptyMap();
  }

  public Map<String, BigDecimal> getRatesFromBackupWS(Throwable throwable) {
    log.error(throwable.getMessage());
    Cache cache = cacheManager.getCache(cacheName);
    URI uri = URI.create(urlBackup + "/latest?access_key=" + apiKeyBackup);
    ResponseEntity<BackupRatesResponse> responseEntity = restTemplate.getForEntity(uri,
        BackupRatesResponse.class);
    BackupRatesResponse ratesResponse = responseEntity.getBody();
    if (cache != null && ratesResponse != null && ratesResponse.getRates() != null) {
      ratesResponse.getRates().forEach(cache::put);
      return ratesResponse.getRates();
    }
    return Collections.emptyMap();
  }

}
