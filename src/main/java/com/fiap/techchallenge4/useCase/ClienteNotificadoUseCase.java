package com.fiap.techchallenge4.useCase;

import com.fiap.techchallenge4.infrastructure.consumer.response.AtualizaClienteConsumerDTO;

public interface ClienteNotificadoUseCase {

    void notificaCliente(final AtualizaClienteConsumerDTO evento);

}
