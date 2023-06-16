package com.mobilelife.exchange.service;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

import com.mobilelife.exchange.exception.ConversionException;
import com.mobilelife.exchange.model.Currency;
import com.mobilelife.exchange.service.impl.ConversionServiceImpl;
import com.mobilelife.exchange.utils.CommonUtil;
import java.math.BigDecimal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ConversionServiceTest {

  @InjectMocks
  private ConversionService conversionService = new ConversionServiceImpl();
  @Mock
  private CacheService cacheService;

  @Test
  void testConversionWhenDifferentCurrencies() throws Exception {

    try (MockedStatic<CommonUtil> utilities = Mockito.mockStatic(CommonUtil.class)) {
      utilities.when(CommonUtil::getBaseCurrency).thenReturn("USD");
      when(cacheService.getRate(anyString())).thenReturn(BigDecimal.ONE);
      Assertions.assertNotNull(
          conversionService.convert(Currency.ALL.getValue(), Currency.AZN.getValue(),
              BigDecimal.ONE));
    }
  }

  @Test
  void testConversionWhenSameCurrencies() throws Exception {

    try (MockedStatic<CommonUtil> utilities = Mockito.mockStatic(CommonUtil.class)) {
      utilities.when(CommonUtil::getBaseCurrency).thenReturn("USD");
      when(cacheService.getRate(anyString())).thenReturn(BigDecimal.ONE);
      Assertions.assertNotNull(
          conversionService.convert(Currency.ALL.getValue(), Currency.ALL.getValue(),
              BigDecimal.ONE));
    }
  }

  @Test
  void testConversionWhenDifferentCurrenciesFromBaseCurrency() throws Exception {

    try (MockedStatic<CommonUtil> utilities = Mockito.mockStatic(CommonUtil.class)) {
      utilities.when(CommonUtil::getBaseCurrency).thenReturn("USD");
      when(cacheService.getRate(anyString())).thenReturn(BigDecimal.ONE);
      Assertions.assertNotNull(
          conversionService.convert(Currency.USD.getValue(), Currency.ALL.getValue(),
              BigDecimal.ONE));
    }
  }

  @Test
  void testConversionWhenDifferentCurrenciesToBaseCurrency() throws Exception {

    try (MockedStatic<CommonUtil> utilities = Mockito.mockStatic(CommonUtil.class)) {
      utilities.when(CommonUtil::getBaseCurrency).thenReturn("USD");
      when(cacheService.getRate(anyString())).thenReturn(BigDecimal.ONE);
      Assertions.assertNotNull(
          conversionService.convert(Currency.ALL.getValue(), Currency.USD.getValue(),
              BigDecimal.ONE));
    }
  }

  @Test
  void testConversionWhenMissingRates() throws Exception {

    try (MockedStatic<CommonUtil> utilities = Mockito.mockStatic(CommonUtil.class)) {
      utilities.when(CommonUtil::getBaseCurrency).thenReturn("USD");
      when(cacheService.getRate(anyString())).thenReturn(null);
      Assertions.assertThrows(ConversionException.class,
          () -> conversionService.convert(Currency.ALL.getValue(), Currency.USD.getValue(),
              BigDecimal.ONE));

    }
  }
}
