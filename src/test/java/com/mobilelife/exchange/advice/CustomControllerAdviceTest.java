package com.mobilelife.exchange.advice;

import com.mobilelife.exchange.model.response.ExceptionResponse;
import java.util.Objects;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class CustomControllerAdviceTest {

  private CustomControllerAdvice customControllerAdvice = new CustomControllerAdvice();

  @Test
  void handleException() {
    Exception e = new Exception();
    ResponseEntity<ExceptionResponse> response = customControllerAdvice.handleException(e);
    Assertions.assertEquals("Error", Objects.requireNonNull(response.getBody()).getMessage());
  }

  @Test
  void handleBindingErrors() {
    Exception e = new Exception();
    ResponseEntity<ExceptionResponse> response = customControllerAdvice.handleBindingErrors(e);
    Assertions.assertEquals("Error with request body",
        Objects.requireNonNull(response.getBody()).getMessage());
  }
}
