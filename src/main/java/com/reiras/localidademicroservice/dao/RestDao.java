package com.reiras.localidademicroservice.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.reiras.localidademicroservice.domain.Localidade;

@Component
public class RestDao implements Dao {

	@Autowired
	RestTemplate restTemplate;
	
	private static final String CITIES_ENDPOINT_URL = "https://servicodados.ibge.gov.br/api/v1/localidades/estados/{UF}/municipios";

	@Override
	public Optional<Localidade> findLocalidadeBySiglaEstadoAndNomeCidade(String siglaEstado, String name) {
		
		List<Localidade> localidadesList = this.findLocalidadeBySiglaEstado(siglaEstado);
		Object[] localidadeArray = localidadesList.stream().filter(localidade -> localidade.getNomeCidade().equalsIgnoreCase(name)).toArray();
		
		if(localidadeArray.length == 0)
			return Optional.empty();
			
		Localidade localidade = (Localidade) localidadeArray[0];
		return Optional.of(localidade);		
	}

	@Override
	public List<Localidade> findLocalidadeBySiglaEstado(String siglaEstado) {
		
		ResponseEntity<List<Localidade>> response = restTemplate.exchange(
				CITIES_ENDPOINT_URL,
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<List<Localidade>>() { },
				siglaEstado);
		return response.getBody();
	}
	
}