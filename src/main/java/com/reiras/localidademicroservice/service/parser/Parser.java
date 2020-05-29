package com.reiras.localidademicroservice.service.parser;

import java.io.InputStream;
import java.util.List;

import com.reiras.localidademicroservice.domain.Localidade;

public interface Parser {

	public InputStream parse(List<Localidade> localidadeList);
}
