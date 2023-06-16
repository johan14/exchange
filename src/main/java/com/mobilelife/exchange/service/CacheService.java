package com.mobilelife.exchange.service;

import java.math.BigDecimal;

public interface CacheService {

  public BigDecimal getRate(String currency) throws Exception;
}
