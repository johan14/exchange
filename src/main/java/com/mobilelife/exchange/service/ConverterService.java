package com.mobilelife.exchange.service;

import com.mobilelife.exchange.model.ConversionResponse;
import java.math.BigDecimal;

public interface ConverterService {

  public ConversionResponse convert(String to, String from, BigDecimal amount) throws Exception;

}
