package com.reiras.localidademicroservice.domain.deserializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.reiras.localidademicroservice.domain.Localidade;

public class LocalidadeDeserializer extends StdDeserializer<Localidade> {
	private static final long serialVersionUID = 1L;

	public LocalidadeDeserializer() {
		this(null);
	}

	public LocalidadeDeserializer(Class<Localidade> vc) {
		super(vc);
	}

	@Override
	public Localidade deserialize(JsonParser parser, DeserializationContext deserializer) throws IOException {

		Localidade localidade = new Localidade();
		JsonNode rootNode = parser.getCodec().readTree(parser);

		localidade.setIdCidade(rootNode.get("id").asLong());
		localidade.setNomeCidade(rootNode.get("nome").asText());
		localidade.setNomeMesorregiao(rootNode.get("microrregiao").get("mesorregiao").get("nome").asText());

		JsonNode ufNode = rootNode.get("microrregiao").get("mesorregiao").get("UF");
		localidade.setIdEstado(ufNode.get("id").asLong());
		localidade.setSiglaEstado(ufNode.get("sigla").asText());
		localidade.setRegiaoNome(ufNode.get("regiao").get("nome").asText());

		return localidade;
	}
}
