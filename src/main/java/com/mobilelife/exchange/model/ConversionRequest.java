package com.mobilelife.exchange.model;


import com.mobilelife.exchange.Currency;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConversionRequest {

  @NotNull
  private Currency from;
  @NotNull
  private Currency to;
  @NotNull
  private BigDecimal amount;
}
