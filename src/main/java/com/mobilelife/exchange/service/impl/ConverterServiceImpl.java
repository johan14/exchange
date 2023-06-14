package com.mobilelife.exchange.service.impl;

import com.mobilelife.exchange.exception.ConversionException;
import com.mobilelife.exchange.model.ConversionResponse;
import com.mobilelife.exchange.service.CacheService;
import com.mobilelife.exchange.service.ConverterService;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
public class ConverterServiceImpl implements ConverterService {

  @Autowired
  CacheService cacheService;
  @Autowired
  CacheManager cacheManager;
  @Value("${api.baseCurrency}")
  private String baseCurrency;

  public ConversionResponse convert(String to, String from, BigDecimal amount) throws Exception {

    ConversionResponse conversionResponse;

    BigDecimal toRate = cacheService.getRate(to);
    BigDecimal fromRate = cacheService.getRate(from);

    if (toRate != null && fromRate != null) {

      if (!to.equals(baseCurrency) && !from.equals(baseCurrency)) {

        BigDecimal fromBaseAmount = BigDecimal.ONE.divide(fromRate, RoundingMode.HALF_DOWN)
            .multiply(amount);
        BigDecimal result = fromBaseAmount.multiply(toRate);
        conversionResponse = new ConversionResponse(result.setScale(2, RoundingMode.HALF_DOWN), to,
            from);
      } else if (from.equals(baseCurrency)) {
        BigDecimal result = amount.multiply(toRate);
        conversionResponse = new ConversionResponse(result.setScale(2, RoundingMode.HALF_DOWN), to,
            from);
      } else {
        BigDecimal result = amount.multiply(
            BigDecimal.ONE.divide(fromRate, RoundingMode.HALF_DOWN));
        conversionResponse = new ConversionResponse(result.setScale(2, RoundingMode.HALF_DOWN), to,
            from);
      }
    } else {
      throw new ConversionException("These currencies are not present for calculation.");
    }
    return conversionResponse;
  }
}