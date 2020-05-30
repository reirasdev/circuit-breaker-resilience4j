package com.reiras.localidademicroservice.service;

import java.io.InputStream;
import java.util.List;

import com.reiras.localidademicroservice.service.parser.ParserContentType;

public interface Service<T> {

	public T findByStateAndCity(String state, String city);

	public List<T> findByState(String state);

	public InputStream findByStateParseFile(String state, ParserContentType contentType);
}
