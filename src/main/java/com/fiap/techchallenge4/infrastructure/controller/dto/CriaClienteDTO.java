package com.fiap.techchallenge4.infrastructure.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;

public record CriaClienteDTO(

		@JsonInclude(JsonInclude.Include.NON_NULL)
		String cpf,

		@JsonInclude(JsonInclude.Include.NON_NULL)
		String nome,

		@JsonInclude(JsonInclude.Include.NON_NULL)
		String enderecoLogradouro,

		@JsonInclude(JsonInclude.Include.NON_NULL)
		Integer enderecoNumero,

		@JsonInclude(JsonInclude.Include.NON_NULL)
		String enderecoSiglaEstado
) {}
