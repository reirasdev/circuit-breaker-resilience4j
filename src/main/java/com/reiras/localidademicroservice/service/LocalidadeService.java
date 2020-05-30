package com.reiras.localidademicroservice.service;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import com.reiras.localidademicroservice.dao.Dao;
import com.reiras.localidademicroservice.domain.Localidade;
import com.reiras.localidademicroservice.exception.ObjectNotFoundException;
import com.reiras.localidademicroservice.service.parser.ParserContentType;
import com.reiras.localidademicroservice.service.parser.ParserFactory;

@org.springframework.stereotype.Service
public class LocalidadeService implements Service<Localidade> {

	private static final Logger LOGGER = LoggerFactory.getLogger(LocalidadeService.class);

	@Autowired
	private Dao<Localidade> localidadeRestDao;
	
	@Autowired
	private ParserFactory parserFactory;

	@Cacheable(cacheNames = "stateCity")
	public Localidade findByStateAndCity(String state, String city) {

		Optional<Localidade> localidade = localidadeRestDao.findByStateAndCity(state, city);

		LOGGER.info(new StringBuffer("[findByStateAndCity]")
				.append(" Input=>{state=").append(state).append("}")
				.append("{city=").append(city).append("}")
				.append(" Output=>{").append(localidade).append("}").toString());

		return localidade.orElseThrow(
				() -> new ObjectNotFoundException("Object not found! State: " + state + ", City: " + city));
	}
	
	public List<Localidade> findByState(String state) {

		List<Localidade> localidadesList = localidadeRestDao.findByState(state);

		LOGGER.info(new StringBuffer("[findByState]")
				.append(" Input=>{state=").append(state).append("}")
				.append(" Output=>{").append(localidadesList.getClass())
				.append(":").append(localidadesList.size()).append("items}").toString());

		return localidadesList;
	}
	
	public InputStream findByStateParseFile(String state, ParserContentType contentType) {

		List<Localidade> localidadeList = localidadeRestDao.findByState(state);
		
		InputStream inputStream = parserFactory.getParser(contentType).parse(localidadeList);

		LOGGER.info(new StringBuffer("[findByStateParseFile]")
				.append(" Input=>{state=").append(state).append("}")
				.append("{contentType=").append(contentType).append("}")
				.append(" Output=>{").append(inputStream.getClass()).append("}").toString());

		return inputStream;
	}
}
