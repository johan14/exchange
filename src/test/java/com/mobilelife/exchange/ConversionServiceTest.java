package com.mobilelife.exchange;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

import com.mobilelife.exchange.model.Currency;
import com.mobilelife.exchange.service.CacheService;
import com.mobilelife.exchange.service.ConversionService;
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

  @Mock
  CommonUtil commonUtil;
  @InjectMocks
  private ConversionService conversionService = new ConversionServiceImpl();
  @Mock
  private CacheService cacheService;

  @Test
  void testConversion() throws Exception {

    try (MockedStatic<CommonUtil> utilities = Mockito.mockStatic(CommonUtil.class)) {
      utilities.when(CommonUtil::getBaseCurrency).thenReturn("USD");
      when(cacheService.getRate(anyString())).thenReturn(BigDecimal.ONE);
      Assertions.assertNotNull(
          conversionService.convert(Currency.ALL.getValue(), Currency.AZN.getValue(),
              BigDecimal.ONE));
    }
  }
}
