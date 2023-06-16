package com.mobilelife.exchange.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ExchangeUtil {

  public static BigDecimal calculateConversionResult(String fromCurrency, String toCurrency,
      BigDecimal amount,
      BigDecimal fromRate, BigDecimal toRate) {

    if (!toCurrency.equals(CommonUtil.getBaseCurrency()) && !fromCurrency.equals(
        CommonUtil.getBaseCurrency())) {
      BigDecimal fromBaseAmount = BigDecimal.ONE.divide(fromRate, RoundingMode.HALF_DOWN)
          .multiply(amount);
      return fromBaseAmount.multiply(toRate);
    } else if (fromCurrency.equals(CommonUtil.getBaseCurrency())) {
      return amount.multiply(toRate);
    } else {
      return amount.multiply(BigDecimal.ONE.divide(fromRate, RoundingMode.HALF_DOWN));
    }
  }
}
