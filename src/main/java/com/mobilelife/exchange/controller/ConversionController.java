package com.mobilelife.exchange.controller;

import com.mobilelife.exchange.model.ConversionRequest;
import com.mobilelife.exchange.model.ConversionResponse;
import com.mobilelife.exchange.service.ConverterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConversionController {

  @Autowired
  ConverterService converterService;

  @PostMapping("/convert")
  public ConversionResponse convert(@RequestBody @Valid ConversionRequest conversionRequest)
      throws Exception {
    return converterService.convert(conversionRequest.getTo().getValue(),
        conversionRequest.getFrom().getValue(),
        conversionRequest.getAmount());
  }
}
