package com.reiras.localidademicroservice.exception;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;

@ControllerAdvice
public class RestExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(RestExceptionHandler.class);

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException exception, HttpServletRequest request) {

		StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(),
				"Object not found", exception.getMessage(), request.getRequestURI());

		LOGGER.error(err.toString(), exception);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}

	@ExceptionHandler(FileHandlingException.class)
	public ResponseEntity<StandardError> fileHandling(FileHandlingException exception, HttpServletRequest request) {

		StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.INTERNAL_SERVER_ERROR.value(),
				"Error handling file operation", exception.getMessage(), request.getRequestURI());

		LOGGER.error(err.toString(), exception);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
	}

	@ExceptionHandler(CallNotPermittedException.class)
	public ResponseEntity<StandardError> circuitBreaker(CallNotPermittedException exception,
			HttpServletRequest request) {

		StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.SERVICE_UNAVAILABLE.value(),
				"Circuit breaker is OPEN. External API is not available", exception.getMessage(),
				request.getRequestURI());

		LOGGER.error(err.toString(), exception);
		return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(err);
	}

}
