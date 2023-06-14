package com.mobilelife.exchange.model;


import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConversionRequest {

  private String from;
  private String to;
  private BigDecimal amount;
}
