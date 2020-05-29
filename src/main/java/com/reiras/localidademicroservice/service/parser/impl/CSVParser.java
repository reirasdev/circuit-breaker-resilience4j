package com.reiras.localidademicroservice.service.parser.impl;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.reiras.localidademicroservice.domain.Localidade;
import com.reiras.localidademicroservice.service.parser.Parser;

@Component("CSV")
public class CSVParser implements Parser {

	private static final Logger LOGGER = LoggerFactory.getLogger(CSVParser.class);
	
	private static final String FIELD_SEPARATOR = ",";

	@Override
	public InputStream parse(List<Localidade> localidadesList) {

		StringBuffer strCsv = new StringBuffer("idEstado,siglaEstado,regiaoNome,nomeCidade,nomeMesorregiao,nomeFormatado\n");
		for(Localidade localidade : localidadesList) {
			strCsv.append(localidade.getIdEstado());
			strCsv.append(FIELD_SEPARATOR);
			strCsv.append(localidade.getSiglaEstado());
			strCsv.append(FIELD_SEPARATOR);
			strCsv.append(localidade.getRegiaoNome());
			strCsv.append(FIELD_SEPARATOR);
			strCsv.append(localidade.getNomeCidade());
			strCsv.append(FIELD_SEPARATOR);
			strCsv.append(localidade.getNomeMesorregiao());
			strCsv.append(FIELD_SEPARATOR);
			strCsv.append(localidade.getNomeFormatado());
			strCsv.append("\n");
		}	
		
		InputStream inputStream = new BufferedInputStream(new ByteArrayInputStream(strCsv.toString().getBytes()));

		LOGGER.info(new StringBuffer("[parse]")
				.append(" Input=>{localidadesList=").append(localidadesList.getClass())
				.append(":").append(localidadesList.size()).append("items}")
				.append(" Output=>{").append(inputStream.getClass()).append("}").toString());

		return inputStream;
	}

}
