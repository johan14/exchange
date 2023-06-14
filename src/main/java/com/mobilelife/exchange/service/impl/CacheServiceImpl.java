package com.mobilelife.exchange.service.impl;

import com.mobilelife.exchange.model.RatesResponse;
import com.mobilelife.exchange.service.CacheService;
import java.math.BigDecimal;
import java.net.URI;
import java.util.Collections;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class CacheServiceImpl implements CacheService {

  @Autowired
  RestTemplate restTemplate;
  @Autowired
  CacheManager cacheManager;
  @Value("${api.url}")
  private String url;
  @Value("${api.apiKey}")
  private String apiKey;
  @Value("${spring.cache.cache-names}")
  private String cacheName;
  @Value("${api.baseCurrency}")
  private String baseCurrency;

  @Cacheable(cacheNames = "rates")
  public BigDecimal getRate(String currency) {
    log.info("Getting results without using cache.");
    return getRatesFromWS().get(currency);
  }

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

  //@Scheduled(fixedRate = 1, timeUnit = TimeUnit.MINUTES)
  @Async
  void populateCache() {
    getRatesFromWS();
  }

}
