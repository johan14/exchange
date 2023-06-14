package com.mobilelife.exchange.service;

import java.math.BigDecimal;
import java.util.Map;

public interface CacheService {

  public Map<String, BigDecimal> getRatesFromWS();

  public BigDecimal getRate(String currency);
}
