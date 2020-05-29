package com.reiras.localidademicroservice.service.parser;

public interface ParserFactory {

	public Parser getParser(ParserContentType contentType);
}
