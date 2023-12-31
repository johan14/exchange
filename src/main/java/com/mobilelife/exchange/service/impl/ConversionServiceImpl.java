package com.mobilelife.exchange.service.impl;

import com.mobilelife.exchange.exception.ConversionException;
import com.mobilelife.exchange.model.response.ConversionResponse;
import com.mobilelife.exchange.service.CacheService;
import com.mobilelife.exchange.service.ConversionService;
import com.mobilelife.exchange.utils.ExchangeUtil;
import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConversionServiceImpl implements ConversionService {

  private final CacheService cacheService;

  public ConversionResponse convert(String toCurrency, String fromCurrency, BigDecimal amount)
      throws Exception {

    BigDecimal toRate = cacheService.getRate(toCurrency);
    BigDecimal fromRate = cacheService.getRate(fromCurrency);

    if (toRate != null && fromRate != null) {
      if (fromCurrency.equals(toCurrency)) {
        return new ConversionResponse(amount, toCurrency, fromCurrency);
      }

      BigDecimal result = ExchangeUtil.calculateConversionResult(fromCurrency, toCurrency, amount,
          fromRate, toRate);
      return new ConversionResponse(result.setScale(2, RoundingMode.HALF_DOWN), toCurrency,
          fromCurrency);
    } else {
      throw new ConversionException("These currency values are not present for calculation.");
    }
  }
}
