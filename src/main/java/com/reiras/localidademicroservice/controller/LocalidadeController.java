package com.reiras.localidademicroservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.reiras.localidademicroservice.domain.Localidade;
import com.reiras.localidademicroservice.service.LocalidadeService;

@RestController
@RequestMapping(value = "/localidades")
public class LocalidadeController {

	private static final Logger LOGGER = LoggerFactory.getLogger(LocalidadeController.class);

	@Autowired
	LocalidadeService localidadeService;

	@GetMapping(value = "/{UF}")
	public ResponseEntity<Long> getIdCidadeBySiglaEstadoAndNomeCidade(@PathVariable(name = "UF") String siglaEstado,
			@RequestParam(value = "nomeCidade", defaultValue = "") String nomeCidade) {

		if (!siglaEstado.matches("[a-zA-Z]{2}")) {
			LOGGER.warn(new StringBuffer("[getIdCidadeBySiglaEstadoAndNomeCidade]")
					.append(" Garbage Input=>{UF=").append(siglaEstado)
					.append("}: Replacing with empty value").toString());
			
			siglaEstado = "";
		}

		Localidade localidade = localidadeService.findLocalidadeBySiglaEstadoAndNomeCidade(siglaEstado, nomeCidade);

		LOGGER.info(new StringBuffer("[getIdCidadeBySiglaEstadoAndNomeCidade]")
				.append(" Input=>{siglaEstado=").append(siglaEstado).append("}")
				.append("{nomeCidade=").append(nomeCidade).append("}")
				.append(" Output=>{").append(localidade.getIdCidade()).append("}").toString());

		return ResponseEntity.ok().body(localidade.getIdCidade());
	}

	@GetMapping(value = "/{UF}/json")
	public ResponseEntity<List<Localidade>> findLocalidadeBySiglaEstadoParseJson(@PathVariable(name = "UF") String siglaEstado) {

		if (!siglaEstado.matches("[a-zA-Z]{2}")) {
			LOGGER.warn(new StringBuffer("[findLocalidadeBySiglaEstadoParseJson]")
					.append(" Garbage Input=>{UF=").append(siglaEstado)
					.append("}: Returning empty list").toString());
			
			return ResponseEntity.ok().body(new ArrayList<Localidade>());
		}

		List<Localidade> localidadesList = localidadeService.findLocalidadeBySiglaEstado(siglaEstado);
		
		LOGGER.info(new StringBuffer("[findLocalidadeBySiglaEstadoParseJson]")
				.append(" Input=>{UF=").append(siglaEstado).append("}")
				.append(" Output=>{").append(localidadesList.getClass()).append("}").toString());
		
		return ResponseEntity.ok().body(localidadesList);
	}

}
