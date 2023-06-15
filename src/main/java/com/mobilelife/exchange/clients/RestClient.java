package com.mobilelife.exchange.clients;

import java.math.BigDecimal;
import java.util.Map;

public interface RestClient {

  public Map<String, BigDecimal> getRatesFromWS();

  public Map<String, BigDecimal> getRatesFromFallbackWS(Throwable throwable);

}
