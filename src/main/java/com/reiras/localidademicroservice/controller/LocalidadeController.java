package com.reiras.localidademicroservice.controller;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.reiras.localidademicroservice.domain.Localidade;
import com.reiras.localidademicroservice.exception.FileHandlingException;
import com.reiras.localidademicroservice.service.Service;
import com.reiras.localidademicroservice.service.parser.ParserContentType;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/localidades")
public class LocalidadeController {

	private static final Logger LOGGER = LoggerFactory.getLogger(LocalidadeController.class);

	@Autowired
	private Service<Localidade> localidadeService;

	@GetMapping
	@ApiResponses(value = { @ApiResponse(code = 404, message = "Object not found"), @ApiResponse(code = 500, message = "Generic Error") })
	@ApiOperation(value = "Given the initials a state and a city name, this endpoint returns the id of the city. Parameters are case insensitive.")
	public ResponseEntity<Long> getIdCidadeBySiglaEstadoAndNomeCidade(@RequestParam(value = "uf", defaultValue = "") String state,
			@RequestParam(value = "cidade", defaultValue = "") String city) {
		
		Instant start = Instant.now();
		LOGGER.info(new StringBuffer("[getIdCidadeBySiglaEstadoAndNomeCidade:Start]")
				.append(" Input=>{siglaEstado=").append(state).append("}")
				.append("{nomeCidade=").append(city).append("}").toString());

		if (!state.matches("[a-zA-Z]{2}")) {
			LOGGER.warn(new StringBuffer("[getIdCidadeBySiglaEstadoAndNomeCidade]")
					.append(" Garbage Input=>{UF=").append(state)
					.append("}: Replacing with empty value").toString());
			
			state = "";
		}

		Localidade localidade = localidadeService.findByStateAndCity(state, city);

		LOGGER.info(new StringBuffer("[getIdCidadeBySiglaEstadoAndNomeCidade:End:").append(start.until(Instant.now(), ChronoUnit.MILLIS)).append("ms]")
				.append(" Output=>{").append(localidade.getIdCidade()).append("}").toString());

		return ResponseEntity.ok().body(localidade.getIdCidade());
	}

	@GetMapping(value = "/{UF}")
	@ApiResponses(value = { @ApiResponse(code = 500, message = "Generic Error") })
	@ApiOperation(value = "Given the initials of a state, this endpoint returns all its cities. UF param is case insensitive.")
	public ResponseEntity<List<Localidade>> findLocalidadeBySiglaEstado(@PathVariable(name = "UF") String state) {

		Instant start = Instant.now();
		LOGGER.info(new StringBuffer("[findLocalidadeBySiglaEstado:Start]")
				.append(" Input=>{UF=").append(state).append("}").toString());
		
		if (!state.matches("[a-zA-Z]{2}")) {
			LOGGER.warn(new StringBuffer("[findLocalidadeBySiglaEstadoParseJson]")
					.append(" Garbage Input=>{UF=").append(state)
					.append("}: Returning empty list").toString());
			
			return ResponseEntity.ok().body(new ArrayList<Localidade>());
		}

		List<Localidade> localidadesList = localidadeService.findByState(state);
		
		LOGGER.info(new StringBuffer("[findLocalidadeBySiglaEstado:End:").append(start.until(Instant.now(), ChronoUnit.MILLIS)).append("ms]")
				.append(" Output=>{").append(localidadesList.getClass()).append(":").append(localidadesList.size()).append("items}").toString());
		
		return ResponseEntity.ok().body(localidadesList);
	}
	
	@GetMapping(value = "/{UF}/csv")
	@ApiResponses(value = { @ApiResponse(code = 500, message = "Error handling file operation / Generic Error") })
	@ApiOperation(value = "Given the initials of a state, this endpoint returns all its cities for download in CSV format. UF param is case insensitive.")
	public @ResponseBody ResponseEntity<Void> findLocalidadeBySiglaEstadoParseCsv(@PathVariable(name = "UF") String state,
			HttpServletResponse response){
		
		Instant start = Instant.now();
		LOGGER.info(new StringBuffer("[findLocalidadeBySiglaEstadoParseCsv:Start]")
				.append(" Input=>{UF=").append(state).append("}").toString());
		
		if (!state.matches("[a-zA-Z]{2}")) {
			LOGGER.warn(new StringBuffer("[findLocalidadeBySiglaEstadoParseCsv]")
					.append(" Garbage Input=>{UF=").append(state)
					.append("}: Replacing with empty value").toString());
			
			state = "";
		}
		
		InputStream inputStream = localidadeService.findByStateParseFile(state, ParserContentType.CSV);
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment;filename=localidades.csv");
        
		try {
			FileCopyUtils.copy(inputStream, response.getOutputStream());
		} catch (IOException e) {
			LOGGER.error("[findLocalidadeBySiglaEstadoParseCsv] Error returning file for download", e.getMessage());
			throw new FileHandlingException("Error returning CSV for download", e);
		}
       
		
        LOGGER.info(new StringBuffer("[findLocalidadeBySiglaEstadoParseCsv:End:").append(start.until(Instant.now(), ChronoUnit.MILLIS)).append("ms]")
				.append(" Output=>{").append(inputStream.getClass()).append("}").toString());
		
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping(value = "/{UF}/json")
	@ApiResponses(value = { @ApiResponse(code = 500, message = "Error handling file operation / Generic Error") })
	@ApiOperation(value = "Given the initials of a state, this endpoint returns all its cities for download in JSON format. UF param is case insensitive.")
	public @ResponseBody ResponseEntity<Void> findLocalidadeBySiglaEstadoParseJson(@PathVariable(name = "UF") String state,
			HttpServletResponse response){
		
		Instant start = Instant.now();
		LOGGER.info(new StringBuffer("[findLocalidadeBySiglaEstadoParseJson:Start]")
				.append(" Input=>{UF=").append(state).append("}").toString());
		
		if (!state.matches("[a-zA-Z]{2}")) {
			LOGGER.warn(new StringBuffer("[findLocalidadeBySiglaEstadoParseJson]")
					.append(" Garbage Input=>{UF=").append(state)
					.append("}: Replacing with empty value").toString());
			
			state = "";
		}
		
		InputStream inputStream = localidadeService.findByStateParseFile(state, ParserContentType.JSON);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setHeader("Content-Disposition", "attachment;filename=localidades.json");
        
		try {
			FileCopyUtils.copy(inputStream, response.getOutputStream());
		} catch (IOException e) {
			LOGGER.error("[findLocalidadeBySiglaEstadoParseJson] Error returning file for download", e.getMessage());
			throw new FileHandlingException("Error returning JSON for download", e);
		}
		
        LOGGER.info(new StringBuffer("[findLocalidadeBySiglaEstadoParseJson:End:").append(start.until(Instant.now(), ChronoUnit.MILLIS)).append("ms]")
				.append(" Output=>{").append(inputStream.getClass()).append("}").toString());
		
		return ResponseEntity.noContent().build();
	}

}
