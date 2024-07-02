package com.fiap.techchallenge4.infrastructure.controller.dto;

import java.time.LocalDateTime;

public record ClienteDTO(

		String cpf,
		String nome,
		String enderecoLogradouro,
		Integer enderecoNumero,
		String enderecoSiglaEstado,
		LocalDateTime dataDeCriacao
) {}
