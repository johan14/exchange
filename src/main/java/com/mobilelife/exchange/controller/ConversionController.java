package com.mobilelife.exchange.controller;

import com.mobilelife.exchange.model.request.ConversionRequest;
import com.mobilelife.exchange.model.response.ConversionResponse;
import com.mobilelife.exchange.service.ConversionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConversionController {

  @Autowired
  ConversionService conversionService;

  @PostMapping("/convert")
  public ConversionResponse convert(@RequestBody @Valid ConversionRequest conversionRequest)
      throws Exception {
    return conversionService.convert(conversionRequest.getTo().getValue(),
        conversionRequest.getFrom().getValue(),
        conversionRequest.getAmount());
  }
}
