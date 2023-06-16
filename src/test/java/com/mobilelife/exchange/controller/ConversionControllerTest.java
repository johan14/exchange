package com.mobilelife.exchange.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobilelife.exchange.model.Currency;
import com.mobilelife.exchange.model.request.ConversionRequest;
import com.mobilelife.exchange.model.response.ConversionResponse;
import com.mobilelife.exchange.service.ConversionService;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class ConversionControllerTest {

  private final String ENDPOINT = "/api/v1/convert";
  @InjectMocks
  ConversionController conversionController;
  @Mock
  ConversionService conversionService;
  private MockMvc mockMvc;

  @BeforeEach
  public void setup() {
    mockMvc = MockMvcBuilders.standaloneSetup(conversionController).build();
  }

  @Test
  public void testConvertEndpointWhenValidInput() throws Exception {
    ConversionRequest conversionRequest = new ConversionRequest(Currency.ALL, Currency.EUR,
        BigDecimal.ONE);
    when(conversionService.convert(conversionRequest.getTo().getValue(),
        conversionRequest.getFrom().getValue(), conversionRequest.getAmount())).thenReturn(
        new ConversionResponse(BigDecimal.ONE, Currency.FKP.getValue(), Currency.RSD.getValue()));
    ObjectMapper objectMapper = new ObjectMapper();
    String requestJson = objectMapper.writeValueAsString(conversionRequest);
    String responseJson = objectMapper.writeValueAsString(
        new ConversionResponse(BigDecimal.ONE, Currency.FKP.getValue(), Currency.RSD.getValue()));
    mockMvc.perform(post(ENDPOINT)
            .content(requestJson)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json(responseJson));
  }

  @Test
  public void testConvertEndpointWhenInvalidInput() throws Exception {
    ConversionRequest conversionRequest = new ConversionRequest(Currency.ALL, Currency.EUR,
        null);
    ObjectMapper objectMapper = new ObjectMapper();
    String requestJson = objectMapper.writeValueAsString(conversionRequest);

    mockMvc.perform(post(ENDPOINT)
            .content(requestJson)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is(400));
  }

}
