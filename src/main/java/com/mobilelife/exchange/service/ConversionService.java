package com.mobilelife.exchange.service;

import com.mobilelife.exchange.model.response.ConversionResponse;
import java.math.BigDecimal;

public interface ConversionService {

  public ConversionResponse convert(String to, String from, BigDecimal amount) throws Exception;

}
