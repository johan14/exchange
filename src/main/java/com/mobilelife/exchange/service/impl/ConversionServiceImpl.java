package com.mobilelife.exchange.service.impl;

import com.mobilelife.exchange.exception.ConversionException;
import com.mobilelife.exchange.model.response.ConversionResponse;
import com.mobilelife.exchange.service.CacheService;
import com.mobilelife.exchange.service.ConversionService;
import com.mobilelife.exchange.utils.CommonUtil;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
public class ConversionServiceImpl implements ConversionService {

  @Autowired
  CacheService cacheService;
  @Autowired
  CacheManager cacheManager;

  public ConversionResponse convert(String toCurrency, String fromCurrency, BigDecimal amount)
      throws Exception {

    ConversionResponse conversionResponse;

    BigDecimal toRate = cacheService.getRate(toCurrency);
    BigDecimal fromRate = cacheService.getRate(fromCurrency);

    if (toRate != null && fromRate != null) {

      if (fromCurrency.equals(toCurrency)) {
        return new ConversionResponse(amount, toCurrency, fromCurrency);
      }

      if (!toCurrency.equals(CommonUtil.getBaseCurrency()) && !fromCurrency.equals(
          CommonUtil.getBaseCurrency())) {

        BigDecimal fromBaseAmount = BigDecimal.ONE.divide(fromRate, RoundingMode.HALF_DOWN)
            .multiply(amount);
        BigDecimal result = fromBaseAmount.multiply(toRate);
        conversionResponse = new ConversionResponse(result.setScale(2, RoundingMode.HALF_DOWN),
            toCurrency,
            fromCurrency);
      } else if (fromCurrency.equals(CommonUtil.getBaseCurrency())) {
        BigDecimal result = amount.multiply(toRate);
        conversionResponse = new ConversionResponse(result.setScale(2, RoundingMode.HALF_DOWN),
            toCurrency,
            fromCurrency);
      } else {
        BigDecimal result = amount.multiply(
            BigDecimal.ONE.divide(fromRate, RoundingMode.HALF_DOWN));
        conversionResponse = new ConversionResponse(result.setScale(2, RoundingMode.HALF_DOWN),
            toCurrency,
            fromCurrency);
      }
    } else {
      throw new ConversionException("These currency values are not present for calculation.");
    }
    return conversionResponse;
  }
}
