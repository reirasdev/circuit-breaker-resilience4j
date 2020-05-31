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
import com.reiras.localidademicroservice.dto.CidadeDto;
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
	@ApiOperation(value = "Given the initials of a state and the city name, this endpoint returns the id of the city. Parameters are case insensitive.")
	public ResponseEntity<CidadeDto> getIdCidadeByUfAndNomeCidade(@RequestParam(value = "uf", defaultValue = "") String uf,
			@RequestParam(value = "nomeCidade", defaultValue = "") String nomeCidade) {
		
		Instant start = Instant.now();
		LOGGER.info(new StringBuffer("[getIdCidadeByUfAndNomeCidade:Start]")
				.append(" Input=>{UF=").append(uf).append("}")
				.append("{nomeCidade=").append(nomeCidade).append("}").toString());

		if (!uf.matches("[a-zA-Z]{2}")) {
			LOGGER.warn(new StringBuffer("[getIdCidadeByUfAndNomeCidade]")
					.append(" Garbage Input=>{UF=").append(uf)
					.append("}: Replacing empty object").toString());
			
			return ResponseEntity.ok().body(new CidadeDto());
		}

		Localidade localidade = localidadeService.findByStateAndCity(uf, nomeCidade);

		LOGGER.info(new StringBuffer("[getIdCidadeByUfAndNomeCidade:End:").append(start.until(Instant.now(), ChronoUnit.MILLIS)).append("ms]")
				.append(" Output=>{").append(localidade.getIdCidade()).append("}").toString());

		return ResponseEntity.ok().body(new CidadeDto(localidade.getIdCidade()));
	}

	@GetMapping(value = "/{UF}")
	@ApiResponses(value = { @ApiResponse(code = 500, message = "Generic Error") })
	@ApiOperation(value = "Given the initials of a state, this endpoint returns all its cities. UF param is case insensitive.")
	public ResponseEntity<List<Localidade>> findLocalidadeByUf(@PathVariable(name = "UF") String uf) {

		Instant start = Instant.now();
		LOGGER.info(new StringBuffer("[findLocalidadeByUf:Start]")
				.append(" Input=>{UF=").append(uf).append("}").toString());
		
		if (!uf.matches("[a-zA-Z]{2}")) {
			LOGGER.warn(new StringBuffer("[findLocalidadeByUf]")
					.append(" Garbage Input=>{UF=").append(uf)
					.append("}: Returning empty list").toString());
			
			return ResponseEntity.ok().body(new ArrayList<Localidade>());
		}

		List<Localidade> localidadesList = localidadeService.findByState(uf);
		
		LOGGER.info(new StringBuffer("[findLocalidadeByUf:End:").append(start.until(Instant.now(), ChronoUnit.MILLIS)).append("ms]")
				.append(" Output=>{").append(localidadesList.getClass()).append(":").append(localidadesList.size()).append("items}").toString());
		
		return ResponseEntity.ok().body(localidadesList);
	}
	
	@GetMapping(value = "/{UF}/csv")
	@ApiResponses(value = { @ApiResponse(code = 500, message = "Error handling file operation / Generic Error") })
	@ApiOperation(value = "Given the initials of a state, this endpoint returns all its cities for download in CSV format. UF param is case insensitive.")
	public @ResponseBody ResponseEntity<Void> findLocalidadeByUfParseCsv(@PathVariable(name = "UF") String uf,
			HttpServletResponse response){
		
		Instant start = Instant.now();
		LOGGER.info(new StringBuffer("[findLocalidadeByUfCsv:Start]")
				.append(" Input=>{UF=").append(uf).append("}").toString());
		
		if (!uf.matches("[a-zA-Z]{2}")) {
			LOGGER.warn(new StringBuffer("[findLocalidadeByUfCsv]")
					.append(" Garbage Input=>{UF=").append(uf)
					.append("}: Replacing with empty value").toString());
			
			uf = "";
		}
		
		InputStream inputStream = localidadeService.findByStateParseFile(uf, ParserContentType.CSV);
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment;filename=localidades.csv");
        
		try {
			FileCopyUtils.copy(inputStream, response.getOutputStream());
		} catch (IOException e) {
			LOGGER.error("[findLocalidadeByUfCsv] Error returning file for download", e.getMessage());
			throw new FileHandlingException("Error returning CSV for download", e);
		}
       
		
        LOGGER.info(new StringBuffer("[findLocalidadeByUfCsv:End:").append(start.until(Instant.now(), ChronoUnit.MILLIS)).append("ms]")
				.append(" Output=>{").append(inputStream.getClass()).append("}").toString());
		
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping(value = "/{UF}/json")
	@ApiResponses(value = { @ApiResponse(code = 500, message = "Error handling file operation / Generic Error") })
	@ApiOperation(value = "Given the initials of a state, this endpoint returns all its cities for download in JSON format. UF param is case insensitive.")
	public @ResponseBody ResponseEntity<Void> findLocalidadeByUfParseJson(@PathVariable(name = "UF") String uf,
			HttpServletResponse response){
		
		Instant start = Instant.now();
		LOGGER.info(new StringBuffer("[findLocalidadeByUfParseJson:Start]")
				.append(" Input=>{UF=").append(uf).append("}").toString());
		
		if (!uf.matches("[a-zA-Z]{2}")) {
			LOGGER.warn(new StringBuffer("[findLocalidadeByUfParseJson]")
					.append(" Garbage Input=>{UF=").append(uf)
					.append("}: Replacing with empty value").toString());
			
			uf = "";
		}
		
		InputStream inputStream = localidadeService.findByStateParseFile(uf, ParserContentType.JSON);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setHeader("Content-Disposition", "attachment;filename=localidades.json");
        
		try {
			FileCopyUtils.copy(inputStream, response.getOutputStream());
		} catch (IOException e) {
			LOGGER.error("[findLocalidadeByUfParseJson] Error returning file for download", e.getMessage());
			throw new FileHandlingException("Error returning JSON for download", e);
		}
		
        LOGGER.info(new StringBuffer("[findLocalidadeByUfParseJson:End:").append(start.until(Instant.now(), ChronoUnit.MILLIS)).append("ms]")
				.append(" Output=>{").append(inputStream.getClass()).append("}").toString());
		
		return ResponseEntity.noContent().build();
	}

}
