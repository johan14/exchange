package com.mobilelife.exchange.controller;

import static org.mockito.Mockito.when;

import com.mobilelife.exchange.model.Currency;
import com.mobilelife.exchange.model.request.ConversionRequest;
import com.mobilelife.exchange.model.response.ConversionResponse;
import com.mobilelife.exchange.service.ConversionService;
import java.math.BigDecimal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ConversionControllerTest {

  @InjectMocks
  ConversionController conversionController;

  @Mock
  ConversionService conversionService;

  @Test
  public void convertValidConversionRequest() throws Exception {

    ConversionRequest conversionRequest = new ConversionRequest(Currency.ALL, Currency.EUR,
        BigDecimal.ONE);
    when(conversionService.convert(conversionRequest.getTo().getValue(),
        conversionRequest.getFrom().getValue(), conversionRequest.getAmount())).thenReturn(
        new ConversionResponse());
    ConversionResponse conversionResponse = conversionController.convert(conversionRequest);
    Assertions.assertNotNull(
        conversionResponse);
  }

}
