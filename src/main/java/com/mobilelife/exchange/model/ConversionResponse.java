package com.mobilelife.exchange.model;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConversionResponse {

  private BigDecimal result;
  private String to;
  private String from;
}
