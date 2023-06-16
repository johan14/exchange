package com.mobilelife.exchange.service.impl;

import com.mobilelife.exchange.clients.RestClient;
import com.mobilelife.exchange.service.CacheService;
import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
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
  public BigDecimal getRate(String currency) throws Exception {
    log.info("Getting results without using cache.");
    CompletableFuture<Map<String, BigDecimal>> completableFuture = restClient.getRatesFromWS();
    return completableFuture.get().get(currency);
  }

  //@Scheduled(fixedRate = 1, timeUnit = TimeUnit.MINUTES)
  @Async
  void populateCache() throws Exception {
    restClient.getRatesFromWS();
  }

}
