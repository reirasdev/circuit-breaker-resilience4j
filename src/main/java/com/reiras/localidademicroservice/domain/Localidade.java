package com.reiras.localidademicroservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.reiras.localidademicroservice.domain.deserializer.LocalidadeDeserializer;

@JsonDeserialize(using = LocalidadeDeserializer.class)
public class Localidade {

	private long idEstado;
	private String siglaEstado;
	private String regiaoNome;
	private long idCidade;
	private String nomeCidade;
	private String nomeMesorregiao;

	public long getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(long idEstado) {
		this.idEstado = idEstado;
	}

	public String getSiglaEstado() {
		return siglaEstado;
	}

	public void setSiglaEstado(String siglaEstado) {
		this.siglaEstado = siglaEstado;
	}

	public String getRegiaoNome() {
		return regiaoNome;
	}

	public void setRegiaoNome(String regiaoNome) {
		this.regiaoNome = regiaoNome;
	}

	@JsonIgnore
	public long getIdCidade() {
		return idCidade;
	}

	public void setIdCidade(long idCidade) {
		this.idCidade = idCidade;
	}

	public String getNomeCidade() {
		return nomeCidade;
	}

	public void setNomeCidade(String nomeCidade) {
		this.nomeCidade = nomeCidade;
	}

	public String getNomeMesorregiao() {
		return nomeMesorregiao;
	}

	public void setNomeMesorregiao(String nomeMesorregiao) {
		this.nomeMesorregiao = nomeMesorregiao;
	}

	public String getNomeFormatado() {
		return nomeCidade + "/" + siglaEstado;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Localidade [idEstado=");
		builder.append(idEstado);
		builder.append(", siglaEstado=");
		builder.append(siglaEstado);
		builder.append(", regiaoNome=");
		builder.append(regiaoNome);
		builder.append(", idCidade=");
		builder.append(idCidade);
		builder.append(", nomeCidade=");
		builder.append(nomeCidade);
		builder.append(", nomeMesorregiao=");
		builder.append(nomeMesorregiao);
		builder.append(", nomeFormatado()=");
		builder.append(getNomeFormatado());
		builder.append("]");
		return builder.toString();
	}

}
