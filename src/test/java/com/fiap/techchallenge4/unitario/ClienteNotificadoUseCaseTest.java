package com.fiap.techchallenge4.unitario;

import com.fiap.techchallenge4.domain.StatusEntregaControllerEnum;
import com.fiap.techchallenge4.infrastructure.consumer.response.AtualizaClienteConsumerDTO;
import com.fiap.techchallenge4.infrastructure.controller.dto.ClienteDTO;
import com.fiap.techchallenge4.infrastructure.repository.ClienteNotificadoRepository;
import com.fiap.techchallenge4.useCase.ClienteUseCase;
import com.fiap.techchallenge4.useCase.impl.ClienteNotificadoUseCaseImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ClienteNotificadoUseCaseTest {

    @Test
    public void notificaCliente_EMTRANSPORTE_sucesso() {
        // preparação
        var serviceCliente = Mockito.mock(ClienteUseCase.class);
        var repository = Mockito.mock(ClienteNotificadoRepository.class);

        Mockito.when(serviceCliente.busca(Mockito.any()))
                .thenReturn(
                        new ClienteDTO(
                                "12345678909",
                                "Nome de teste",
                                "Rua de teste",
                                123,
                                "SP",
                                LocalDateTime.now()
                        )
                );

        var service = new ClienteNotificadoUseCaseImpl(repository, serviceCliente);

        // execução
        service.notificaCliente(
                new AtualizaClienteConsumerDTO(
                        "12345678909",
                        StatusEntregaControllerEnum.EM_TRANSPORTE
                )
        );

        // avaliação
        verify(serviceCliente, times(1)).busca(Mockito.any());
        verify(repository, times(1)).save(Mockito.any());
    }

    @Test
    public void notificaCliente_EMTRANSPORTE_clienteNaoEncontrado() {
        // preparação
        var serviceCliente = Mockito.mock(ClienteUseCase.class);
        var repository = Mockito.mock(ClienteNotificadoRepository.class);

        Mockito.when(serviceCliente.busca(Mockito.any()))
                .thenReturn(null);

        var service = new ClienteNotificadoUseCaseImpl(repository, serviceCliente);

        // execução
        service.notificaCliente(
                new AtualizaClienteConsumerDTO(
                        "12345678909",
                        StatusEntregaControllerEnum.EM_TRANSPORTE
                )
        );

        // avaliação
        verify(serviceCliente, times(1)).busca(Mockito.any());
        verify(repository, times(0)).save(Mockito.any());
    }

    @Test
    public void notificaCliente_ENTREGUE_sucesso() {
        // preparação
        var serviceCliente = Mockito.mock(ClienteUseCase.class);
        var repository = Mockito.mock(ClienteNotificadoRepository.class);

        Mockito.when(serviceCliente.busca(Mockito.any()))
                .thenReturn(
                        new ClienteDTO(
                                "12345678909",
                                "Nome de teste",
                                "Rua de teste",
                                123,
                                "SP",
                                LocalDateTime.now()
                        )
                );

        var service = new ClienteNotificadoUseCaseImpl(repository, serviceCliente);

        // execução
        service.notificaCliente(
                new AtualizaClienteConsumerDTO(
                        "12345678909",
                        StatusEntregaControllerEnum.ENTREGUE
                )
        );

        // avaliação
        verify(serviceCliente, times(1)).busca(Mockito.any());
        verify(repository, times(1)).save(Mockito.any());
    }

    @Test
    public void notificaCliente_ENTREGUE_clienteNaoEncontrado() {
        // preparação
        var serviceCliente = Mockito.mock(ClienteUseCase.class);
        var repository = Mockito.mock(ClienteNotificadoRepository.class);

        Mockito.when(serviceCliente.busca(Mockito.any()))
                .thenReturn(null);

        var service = new ClienteNotificadoUseCaseImpl(repository, serviceCliente);

        // execução
        service.notificaCliente(
                new AtualizaClienteConsumerDTO(
                        "12345678909",
                        StatusEntregaControllerEnum.ENTREGUE
                )
        );

        // avaliação
        verify(serviceCliente, times(1)).busca(Mockito.any());
        verify(repository, times(0)).save(Mockito.any());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "-1000",
            "",
            " ",
            "teste",
            "1234567891",
            "123456789123"
    })
    public void notificaCliente_EMTRANSPORTE_naoSalvaNaBaseDeDados(String cpf) {
        // preparação
        var serviceCliente = Mockito.mock(ClienteUseCase.class);
        var repository = Mockito.mock(ClienteNotificadoRepository.class);

        Mockito.when(serviceCliente.busca(Mockito.any()))
                .thenReturn(
                        new ClienteDTO(
                                "12345678909",
                                "Nome de teste",
                                "Rua de teste",
                                123,
                                "SP",
                                LocalDateTime.now()
                        )
                );

        var service = new ClienteNotificadoUseCaseImpl(repository, serviceCliente);

        // execução e avaliação
        var excecao = Assertions.assertThrows(RuntimeException.class, () -> {
            service.notificaCliente(
                    new AtualizaClienteConsumerDTO(
                            cpf.equals("-1000") ? null : cpf,
                            StatusEntregaControllerEnum.EM_TRANSPORTE
                    )
            );
        });
        verify(serviceCliente, times(0)).busca(Mockito.any());
        verify(repository, times(0)).save(Mockito.any());
    }

}
