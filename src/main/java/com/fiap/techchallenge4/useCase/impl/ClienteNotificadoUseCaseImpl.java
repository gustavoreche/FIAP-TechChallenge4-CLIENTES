package com.fiap.techchallenge4.useCase.impl;

import com.fiap.techchallenge4.domain.Cpf;
import com.fiap.techchallenge4.infrastructure.consumer.response.AtualizaClienteConsumerDTO;
import com.fiap.techchallenge4.infrastructure.model.ClienteNotificadoEntity;
import com.fiap.techchallenge4.infrastructure.repository.ClienteNotificadoRepository;
import com.fiap.techchallenge4.useCase.ClienteNotificadoUseCase;
import com.fiap.techchallenge4.useCase.ClienteUseCase;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class ClienteNotificadoUseCaseImpl implements ClienteNotificadoUseCase {

    private final ClienteNotificadoRepository repository;
    private final ClienteUseCase service;

    public ClienteNotificadoUseCaseImpl(final ClienteNotificadoRepository repository,
                                        final ClienteUseCase service) {
        this.repository = repository;
        this.service = service;
    }

    @Override
    public void notificaCliente(AtualizaClienteConsumerDTO evento) {
        final var cpfObjeto = new Cpf(evento.cpf());

        final var clienteNaBase = this.service.busca(cpfObjeto.getNumero());
        if(Objects.isNull(clienteNaBase)) {
            System.out.println("Cliente não está cadastrado");
            return;
        }

        final var clienteNotificadoEntity = ClienteNotificadoEntity.builder()
                .cpf(clienteNaBase.cpf())
                .statusEntrega(evento.statusEntrega())
                .dataDeCriacao(LocalDateTime.now())
                .build();

        this.repository.save(clienteNotificadoEntity);

        System.err.println("!!!!!!!!!!!!!!!NOTIFICA CLIENTE!!!!!!!!!");
        System.err.println("Querido cliente " + clienteNaBase.nome() + ", seu status foi atualizado para " + evento.statusEntrega());
    }

}
