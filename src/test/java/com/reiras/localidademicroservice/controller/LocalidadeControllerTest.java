package com.reiras.localidademicroservice.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.reiras.localidademicroservice.LocalidadeMicroserviceApplication;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = LocalidadeMicroserviceApplication.class)
@AutoConfigureMockMvc
public class LocalidadeControllerTest {

	@Autowired
	private MockMvc mvc;

	@Test
	public void getIdCidadeByUfAndNomeCidade_givenValidUfAndNomeCidade_thenReturnIdCidade()
			throws Exception {

		mvc.perform(MockMvcRequestBuilders.get("/localidades?uf=SC&nomeCidade=Palhoça"))
		.andExpect(status().isOk())
		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.idCidade", is(4211900)));
	}
	
	@Test
	public void getIdCidadeByUfAndNomeCidade_givenValidUfAndNoExistingNomeCidade_thenReturnEmpty()
			throws Exception {

		mvc.perform(MockMvcRequestBuilders.get("/localidades?uf=SC&nomeCidade=NON-EXISTENT"))
		.andExpect(status().isNotFound())
		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.error", is("Object not found")));
	}
	
	@Test
	public void getIdCidadeByUfAndNomeCidade_givenNoExistingUfAndValidNomeCidade_thenReturnEmpty()
			throws Exception {

		mvc.perform(MockMvcRequestBuilders.get("/localidades?uf=XX&nomeCidade=Palhoça"))
		.andExpect(status().isNotFound())
		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.error", is("Object not found")));
	}
	
	@Test
	public void getIdCidadeByUfAndNomeCidade_givenNoExistingUfAndNomeCidade_thenReturnEmpty()
			throws Exception {

		mvc.perform(MockMvcRequestBuilders.get("/localidades?uf=XX&nomeCidade=NON-EXISTENT"))
		.andExpect(status().isNotFound())
		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.error", is("Object not found")));
	}
	
	
	@Test
	public void findLocalidadeByUf_givenValidUf_thenReturnListOfLocalidades()
			throws Exception {

		mvc.perform(MockMvcRequestBuilders.get("/localidades/SC"))
		.andExpect(status().isOk())
		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$", hasSize(equalTo(295))))
		.andExpect(jsonPath("$[0].siglaEstado", is("SC")));
	}
	
	@Test
	public void findLocalidadeByUf_givenNotValidUf_thenReturnEmptyList()
			throws Exception {

		mvc.perform(MockMvcRequestBuilders.get("/localidades/--"))
		.andExpect(status().isOk())
		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$", hasSize(equalTo(0))));
	}
	
	@Test
	public void findLocalidadeByUf_givenNoExistingUf_thenReturnEmptyList()
			throws Exception {

		mvc.perform(MockMvcRequestBuilders.get("/localidades/XX"))
		.andExpect(status().isOk())
		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$", hasSize(equalTo(0))));
	}
	
	@Test
	public void findLocalidadeByUfCsv_givenValidUf_thenReturnCsvFile()
			throws Exception {		
		MvcResult msvResult = mvc.perform(MockMvcRequestBuilders.get("/localidades/RN/csv"))
		.andExpect(status().isOk())
		.andExpect(content().contentTypeCompatibleWith("text/csv"))
		.andReturn();
		
        assertEquals(msvResult.getResponse().getContentAsByteArray().length, 10000);
        assertTrue(msvResult.getResponse().getContentAsString().contains("idEstado,siglaEstado,regiaoNome,nomeCidade,nomeMesorregiao,nomeFormatado"));
        assertTrue(msvResult.getResponse().getContentAsString().contains("24,RN"));
	}
	
	@Test
	public void findLocalidadeByUfCsv_givenNotValidUf_thenReturnEmptyList()
			throws Exception {		
		MvcResult msvResult = mvc.perform(MockMvcRequestBuilders.get("/localidades/--/csv"))
		.andExpect(status().isOk())
		.andExpect(content().contentTypeCompatibleWith("text/csv"))
		.andReturn();
		
        assertEquals(msvResult.getResponse().getContentAsByteArray().length, 73);
        assertTrue(msvResult.getResponse().getContentAsString().contains("idEstado,siglaEstado,regiaoNome,nomeCidade,nomeMesorregiao,nomeFormatado"));
	}
	
	@Test
	public void findLocalidadeByUfCsv_givenNoExistingUf_thenReturnEmptyList()
			throws Exception {		
		MvcResult msvResult = mvc.perform(MockMvcRequestBuilders.get("/localidades/XX/csv"))
		.andExpect(status().isOk())
		.andExpect(content().contentTypeCompatibleWith("text/csv"))
		.andReturn();
		
        assertEquals(msvResult.getResponse().getContentAsByteArray().length, 73);
        assertTrue(msvResult.getResponse().getContentAsString().contains("idEstado,siglaEstado,regiaoNome,nomeCidade,nomeMesorregiao,nomeFormatado"));
	}
	
	@Test
	public void findLocalidadeByUfJson_givenValidUf_thenReturnJsonFile()
			throws Exception {		
		MvcResult msvResult = mvc.perform(MockMvcRequestBuilders.get("/localidades/TO/json"))
		.andExpect(status().isOk())
		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andReturn();
		
        assertEquals(msvResult.getResponse().getContentAsByteArray().length, 22830);
        assertTrue(msvResult.getResponse().getContentAsString().contains("\"siglaEstado\":\"TO\""));
	}
	
	@Test
	public void findLocalidadeByUfJson_givenNotValidUf_thenReturnEmptyList()
			throws Exception {		
		MvcResult msvResult = mvc.perform(MockMvcRequestBuilders.get("/localidades/--/json"))
		.andExpect(status().isOk())
		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andReturn();
		
        assertEquals(msvResult.getResponse().getContentAsByteArray().length, 2);
        assertTrue(msvResult.getResponse().getContentAsString().contains("[]"));
	}
	
	@Test
	public void findLocalidadeByUfJson_givenNoExistingUf_thenReturnEmptyList()
			throws Exception {		
		MvcResult msvResult = mvc.perform(MockMvcRequestBuilders.get("/localidades/XX/json"))
		.andExpect(status().isOk())
		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andReturn();
		
        assertEquals(msvResult.getResponse().getContentAsByteArray().length, 2);
        assertTrue(msvResult.getResponse().getContentAsString().contains("[]"));
	}
	
}
