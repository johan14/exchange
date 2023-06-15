package com.mobilelife.exchange.model.response;

import java.math.BigDecimal;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FallbackRatesResponse {

  Map<String, BigDecimal> rates;

}
