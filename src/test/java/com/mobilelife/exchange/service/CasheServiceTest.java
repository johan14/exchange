package com.mobilelife.exchange.service;

import com.mobilelife.exchange.clients.RestClient;
import com.mobilelife.exchange.service.impl.CacheServiceImpl;
import java.math.BigDecimal;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CasheServiceTest {

  @InjectMocks
  CacheService cacheService = new CacheServiceImpl();

  @Mock
  RestClient restClient;

  @Test
  void getRate() throws Exception {

    Mockito.when(restClient.getRatesFromWS()).thenReturn(Map.of("USD", BigDecimal.ONE));
    Assertions.assertEquals(BigDecimal.ONE, cacheService.getRate("USD"));
  }

  @Test
  void getRateInvalidCurrency() throws Exception {

    Mockito.when(restClient.getRatesFromWS()).thenReturn(Map.of("USD", BigDecimal.ONE));
    Assertions.assertNull(cacheService.getRate("EUR"));
  }
}
