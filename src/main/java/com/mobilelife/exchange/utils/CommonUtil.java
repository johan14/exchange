package com.mobilelife.exchange.utils;

public class CommonUtil {

  private static String baseCurrency;

  public static String getBaseCurrency() {
    return baseCurrency;
  }

  public static void setBaseCurrency(String baseCurrency) {
    CommonUtil.baseCurrency = baseCurrency;
  }
}
