package com.reiras.localidademicroservice.dto;

public class CidadeDto {

	private long idCidade;

	public CidadeDto() {

	}

	public CidadeDto(long idCidade) {
		this.idCidade = idCidade;
	}

	public long getIdCidade() {
		return idCidade;
	}

	public void setIdCidade(long idCidade) {
		this.idCidade = idCidade;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CidadeDto [idCidade=");
		builder.append(idCidade);
		builder.append("]");
		return builder.toString();
	}

}
