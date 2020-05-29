package com.reiras.localidademicroservice.service;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.reiras.localidademicroservice.dao.Dao;
import com.reiras.localidademicroservice.domain.Localidade;
import com.reiras.localidademicroservice.exception.ObjectNotFoundException;
import com.reiras.localidademicroservice.service.parser.ParserContentType;
import com.reiras.localidademicroservice.service.parser.ParserFactory;

@Service
public class LocalidadeService {

	private static final Logger LOGGER = LoggerFactory.getLogger(LocalidadeService.class);

	@Autowired
	private Dao restDao;
	
	@Autowired
	private ParserFactory parserFactory;

	@Cacheable(cacheNames = "ufCidade")
	public Localidade findLocalidadeBySiglaEstadoAndNomeCidade(String siglaEstado, String nomeCidade) {

		Optional<Localidade> localidade = restDao.findLocalidadeBySiglaEstadoAndNomeCidade(siglaEstado, nomeCidade);

		LOGGER.info(new StringBuffer("[findLocalidadeBySiglaEstadoAndNomeCidade]")
				.append(" Input=>{siglaEstado=").append(siglaEstado).append("}")
				.append("{nomeCidade=").append(nomeCidade).append("}")
				.append(" Output=>{").append(localidade).append("}").toString());

		return localidade.orElseThrow(
				() -> new ObjectNotFoundException("Object not found! State: " + siglaEstado + ", City: " + nomeCidade));
	}
	
	public List<Localidade> findLocalidadeBySiglaEstado(String siglaEstado) {

		List<Localidade> localidadeList = restDao.findLocalidadeBySiglaEstado(siglaEstado);

		LOGGER.info(new StringBuffer("[findLocalidadeBySiglaEstado]")
				.append(" Input=>{siglaEstado=").append(siglaEstado).append("}")
				.append(" Output=>{").append(localidadeList.getClass()).append("}").toString());

		return localidadeList;
	}
	
	public InputStream findLocalidadeBySiglaEstadoParseFile(String siglaEstado, ParserContentType contentType) {

		List<Localidade> localidadeList = restDao.findLocalidadeBySiglaEstado(siglaEstado);
		
		InputStream inputStream = parserFactory.getParser(contentType).parse(localidadeList);

		LOGGER.info(new StringBuffer("[findLocalidadeBySiglaEstadoParseFile]")
				.append(" Input=>{siglaEstado=").append(siglaEstado).append("}")
				.append("{contentType=").append(contentType).append("}")
				.append(" Output=>{").append(inputStream).append("}").toString());

		return inputStream;
	}
}
