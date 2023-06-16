package com.mobilelife.exchange.controller;

import com.mobilelife.exchange.model.request.ConversionRequest;
import com.mobilelife.exchange.model.response.ConversionResponse;
import com.mobilelife.exchange.service.ConversionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/convert")
public class ConversionController {

  @Autowired
  ConversionService conversionService;

  @PostMapping
  public ResponseEntity<ConversionResponse> convert(
      @RequestBody @Valid ConversionRequest conversionRequest)
      throws Exception {
    ConversionResponse conversionResponse = conversionService.convert(
        conversionRequest.getTo().getValue(),
        conversionRequest.getFrom().getValue(),
        conversionRequest.getAmount());
    return ResponseEntity.status(HttpStatus.OK).body(conversionResponse);
  }
}
