package com.fiap.techchallenge4.infrastructure.consumer;

import com.fiap.techchallenge4.infrastructure.consumer.response.AtualizaClienteConsumerDTO;
import com.fiap.techchallenge4.useCase.ClienteNotificadoUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
public class ConsumerRecebeAtualizacaoStatus {

    private final ClienteNotificadoUseCase service;

    public ConsumerRecebeAtualizacaoStatus(final ClienteNotificadoUseCase service) {
        this.service = service;
    }

    @Bean
    public Consumer<AtualizaClienteConsumerDTO> input() {
        return evento -> {
            this.service.notificaCliente(evento);
            System.out.println("Evento consumido com sucesso!");
        };
    }


}
