package com.fiap.techchallenge4.infrastructure.consumer.response;

import com.fiap.techchallenge4.domain.StatusEntregaControllerEnum;

public record AtualizaClienteConsumerDTO(
		String cpf,
		StatusEntregaControllerEnum statusEntrega
) {}
