package com.mobilelife.exchange.service.impl;

import com.mobilelife.exchange.clients.RestClient;
import com.mobilelife.exchange.service.CacheService;
import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
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
  @Autowired
  RestClient restClient;


  @Cacheable(cacheNames = "rates")
  public BigDecimal getRate(String currency) {
    log.info("Getting results without using cache.");
    return restClient.getRatesFromWS().get(currency);
  }

  //@Scheduled(fixedRate = 1, timeUnit = TimeUnit.MINUTES)
  @Async
  void populateCache() {
    restClient.getRatesFromWS();
  }

}
