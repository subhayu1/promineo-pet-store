package com.rimal.petstore.controller.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
@Slf4j
public class GlobalErrorHandler {

  @ExceptionHandler(NoSuchElementException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public Map<String, String> handleNoSuchElementException(NoSuchElementException e) {
    log.info("Handled NoSuchElementException: {}", e.getMessage());
    return Collections.singletonMap("message", e.toString());
  }

  // Other global exception handlers can be added here
}



