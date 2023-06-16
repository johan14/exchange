package com.mobilelife.exchange.clients;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface RestClient {

  public CompletableFuture<Map<String, BigDecimal>> getRatesFromWS() throws Exception;

  public CompletableFuture<Map<String, BigDecimal>> getRatesFromFallbackWS(Throwable throwable)
      throws Exception;

}
