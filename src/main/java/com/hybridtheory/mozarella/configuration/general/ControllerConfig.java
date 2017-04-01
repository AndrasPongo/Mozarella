package com.hybridtheory.mozarella.configuration.general;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.hybridtheory.mozarella.api.StudentController;

@ControllerAdvice
@EnableWebMvc
public class ControllerConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);

	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public void handle(HttpMessageNotReadableException e) {
		LOGGER.warn("Returning HTTP 400 Bad Request", e);
		throw e;
	}
}
