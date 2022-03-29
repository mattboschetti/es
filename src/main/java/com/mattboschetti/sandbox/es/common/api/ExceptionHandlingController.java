package com.mattboschetti.sandbox.es.common.api;

import com.mattboschetti.sandbox.es.common.eventstore.EventStore;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlingController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = EventStore.ConcurrencyException.class)
    public ResponseEntity<String> handleConcurrencyException(Throwable throwable, WebRequest webRequest) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Resource state has changed, retry");
    }

}
