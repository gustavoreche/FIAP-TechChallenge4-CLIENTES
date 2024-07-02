package com.fiap.techchallenge4.unitario;

import com.fiap.techchallenge4.infrastructure.controller.dto.AtualizaClienteDTO;
import com.fiap.techchallenge4.infrastructure.controller.dto.CriaClienteDTO;
import com.fiap.techchallenge4.infrastructure.model.ClienteEntity;
import com.fiap.techchallenge4.infrastructure.repository.ClienteRepository;
import com.fiap.techchallenge4.useCase.impl.ClienteUseCaseImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Stream;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ClienteUseCaseTest {

    @Test
    public void cadastra_salvaNaBaseDeDados() {
        // preparação
        var repository = Mockito.mock(ClienteRepository.class);

        Mockito.when(repository.save(Mockito.any()))
                .thenReturn(
                        new ClienteEntity(
                                "12345678909",
                                "Nome de teste",
                                "Rua de teste",
                                123,
                                "SP",
                                LocalDateTime.now()
                        )
                );

        var service = new ClienteUseCaseImpl(repository);

        // execução
        service.cadastra(
                new CriaClienteDTO(
                        "12345678909",
                        "Nome de teste",
                        "Rua de teste",
                        123,
                        "SP"
                )
        );

        // avaliação
        verify(repository, times(1)).findById(Mockito.any());
        verify(repository, times(1)).save(Mockito.any());
    }

    @Test
    public void cadastra_clienteJaCadastrado_naoSalvaNaBaseDeDados() {
        // preparação
        var repository = Mockito.mock(ClienteRepository.class);

        Mockito.when(repository.findById(Mockito.any()))
                .thenReturn(
                        Optional.of(
                                new ClienteEntity(
                                        "12345678909",
                                        "Nome de teste",
                                        "Rua de teste",
                                        123,
                                        "SP",
                                        LocalDateTime.now()
                                )
                        )
                );

        var service = new ClienteUseCaseImpl(repository);

        // execução
        service.cadastra(
                new CriaClienteDTO(
                        "12345678909",
                        "Nome de teste",
                        "Rua de teste",
                        123,
                        "SP"
                )
        );

        // avaliação
        verify(repository, times(1)).findById(Mockito.any());
        verify(repository, times(0)).save(Mockito.any());
    }

    @Test
    public void atualiza_salvaNaBaseDeDados() {
        // preparação
        var repository = Mockito.mock(ClienteRepository.class);

        Mockito.when(repository.save(Mockito.any()))
                .thenReturn(
                        new ClienteEntity(
                                "12345678909",
                                "Nome de teste",
                                "Rua de teste",
                                123,
                                "SP",
                                LocalDateTime.now()
                        )
                );
        Mockito.when(repository.findById(Mockito.any()))
                .thenReturn(
                        Optional.of(
                                new ClienteEntity(
                                        "12345678909",
                                        "Nome de teste",
                                        "Rua de teste",
                                        123,
                                        "SP",
                                        LocalDateTime.now()
                                )
                        )
                );

        var service = new ClienteUseCaseImpl(repository);

        // execução
        service.atualiza(
                "12345678909",
                new AtualizaClienteDTO(
                        "Nome de teste",
                        "Rua de teste",
                        123,
                        "SP"
                )
        );

        // avaliação
        verify(repository, times(1)).findById(Mockito.any());
        verify(repository, times(1)).save(Mockito.any());
    }

    @Test
    public void atualiza_clienteNaoEstaCadastrado_naoSalvaNaBaseDeDados() {
        // preparação
        var repository = Mockito.mock(ClienteRepository.class);

        Mockito.when(repository.findById(Mockito.any()))
                .thenReturn(
                        Optional.empty()
                );

        var service = new ClienteUseCaseImpl(repository);

        // execução
        service.atualiza(
                "12345678909",
                new AtualizaClienteDTO(
                        "Nome de teste",
                        "Rua de teste",
                        123,
                        "SP"
                )
        );

        // avaliação
        verify(repository, times(1)).findById(Mockito.any());
        verify(repository, times(0)).save(Mockito.any());
    }

    @Test
    public void deleta_deletaNaBaseDeDados() {
        // preparação
        var repository = Mockito.mock(ClienteRepository.class);

        Mockito.doNothing().when(repository).deleteById(Mockito.any());
        Mockito.when(repository.findById(Mockito.any()))
                .thenReturn(
                        Optional.of(
                                new ClienteEntity(
                                        "12345678909",
                                        "Nome de teste",
                                        "Rua de teste",
                                        123,
                                        "SP",
                                        LocalDateTime.now()
                                )
                        )
                );

        var service = new ClienteUseCaseImpl(repository);

        // execução
        service.deleta(
                "12345678909"
        );

        // avaliação
        verify(repository, times(1)).findById(Mockito.any());
        verify(repository, times(1)).deleteById(Mockito.any());
    }

    @Test
    public void deleta_clienteNaoEstaCadastrado_naoDeletaNaBaseDeDados() {
        // preparação
        var repository = Mockito.mock(ClienteRepository.class);

        Mockito.when(repository.findById(Mockito.any()))
                .thenReturn(
                        Optional.empty()
                );

        var service = new ClienteUseCaseImpl(repository);

        // execução
        service.deleta(
                "12345678909"
        );

        // avaliação
        verify(repository, times(1)).findById(Mockito.any());
        verify(repository, times(0)).deleteById(Mockito.any());
    }

    @Test
    public void busca_buscaNaBaseDeDados() {
        // preparação
        var repository = Mockito.mock(ClienteRepository.class);

        Mockito.when(repository.findById(Mockito.any()))
                .thenReturn(
                        Optional.of(
                                new ClienteEntity(
                                        "12345678909",
                                        "Nome de teste",
                                        "Rua de teste",
                                        123,
                                        "SP",
                                        LocalDateTime.now()
                                )
                        )
                );

        var service = new ClienteUseCaseImpl(repository);

        // execução
        service.busca(
                "12345678909"
        );

        // avaliação
        verify(repository, times(1)).findById(Mockito.any());
    }

    @Test
    public void busca_clienteNaoEstaCadastrado_naoEncontraNaBaseDeDados() {
        // preparação
        var repository = Mockito.mock(ClienteRepository.class);

        Mockito.when(repository.findById(Mockito.any()))
                .thenReturn(
                        Optional.empty()
                );

        var service = new ClienteUseCaseImpl(repository);

        // execução
        service.busca(
                "12345678909"
        );

        // avaliação
        verify(repository, times(1)).findById(Mockito.any());
    }

    @ParameterizedTest
    @MethodSource("requestValidandoCampos")
    public void cadastra_camposInvalidos_naoSalvaNaBaseDeDados(String cpf,
                                                               String nome,
                                                               String enderecoLogradouro,
                                                               Integer enderecoNumero,
                                                               String enderecoSiglaEstado) {
        // preparação
        var repository = Mockito.mock(ClienteRepository.class);

        Mockito.when(repository.save(Mockito.any()))
                .thenReturn(
                        new ClienteEntity(
                                "12345678909",
                                "Nome de teste",
                                "Rua de teste",
                                123,
                                "SP",
                                LocalDateTime.now()
                        )
                );

        var service = new ClienteUseCaseImpl(repository);

        // execução e avaliação
        var excecao = Assertions.assertThrows(RuntimeException.class, () -> {
            service.cadastra(
                    new CriaClienteDTO(
                            cpf,
                            nome,
                            enderecoLogradouro,
                            enderecoNumero,
                            enderecoSiglaEstado
                    )
            );
        });
        verify(repository, times(0)).save(Mockito.any());
        verify(repository, times(0)).findById(Mockito.any());
    }

    @ParameterizedTest
    @MethodSource("requestValidandoCampos")
    public void atualiza_camposInvalidos_naoSalvaNaBaseDeDados(String cpf,
                                                               String nome,
                                                               String enderecoLogradouro,
                                                               Integer enderecoNumero,
                                                               String enderecoSiglaEstado) {
        // preparação
        var repository = Mockito.mock(ClienteRepository.class);

        Mockito.when(repository.save(Mockito.any()))
                .thenReturn(
                        new ClienteEntity(
                                "12345678909",
                                "Nome de teste",
                                "Rua de teste",
                                123,
                                "SP",
                                LocalDateTime.now()
                        )
                );

        Mockito.when(repository.findById(Mockito.any()))
                .thenReturn(
                        Optional.of(
                                new ClienteEntity(
                                        "12345678909",
                                        "Nome de teste",
                                        "Rua de teste",
                                        123,
                                        "SP",
                                        LocalDateTime.now()
                                )
                        )
                );

        var service = new ClienteUseCaseImpl(repository);

        // execução e avaliação
        var excecao = Assertions.assertThrows(RuntimeException.class, () -> {
            service.atualiza(
                    cpf,
                    new AtualizaClienteDTO(
                            nome,
                            enderecoLogradouro,
                            enderecoNumero,
                            enderecoSiglaEstado
                    )
                );
        });
        verify(repository, times(0)).findById(Mockito.any());
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
    public void deleta_camposInvalidos_naoDeletaNaBaseDeDados(String cpf) {
        // preparação
        var repository = Mockito.mock(ClienteRepository.class);

        var service = new ClienteUseCaseImpl(repository);

        // execução e avaliação
        var excecao = Assertions.assertThrows(RuntimeException.class, () -> {
            service.deleta(
                    cpf.equals("-1000") ? null : cpf
            );
        });
        verify(repository, times(0)).findById(Mockito.any());
        verify(repository, times(0)).deleteById(Mockito.any());
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
    public void busca_camposInvalidos_naoBuscaNaBaseDeDados(String cpf) {
        // preparação
        var repository = Mockito.mock(ClienteRepository.class);

        var service = new ClienteUseCaseImpl(repository);

        // execução e avaliação
        var excecao = Assertions.assertThrows(RuntimeException.class, () -> {
            service.busca(
                    cpf.equals("-1000") ? null : cpf
            );
        });
        verify(repository, times(0)).findById(Mockito.any());
        verify(repository, times(0)).deleteById(Mockito.any());
    }

    private static Stream<Arguments> requestValidandoCampos() {
        return Stream.of(
                Arguments.of(null, "Nome de teste", "Endereco de teste", 1234, "SP"),
                Arguments.of("", "Nome de teste", "Endereco de teste", 1234, "SP"),
                Arguments.of(" ", "Nome de teste", "Endereco de teste", 1234, "SP"),
                Arguments.of("teste", "Nome de teste", "Endereco de teste", 1234, "SP"),
                Arguments.of("1234567891", "Nome de teste", "Endereco de teste", 1234, "SP"),
                Arguments.of("123456789123", "Nome de teste", "Endereco de teste", 1234, "SP"),
                Arguments.of("12345678909", null, "Endereco de teste", 1234, "SP"),
                Arguments.of("12345678909", "", "Endereco de teste", 1234, "SP"),
                Arguments.of("12345678909", " ", "Endereco de teste", 1234, "SP"),
                Arguments.of("12345678909", "ab", "Endereco de teste", 1234, "SP"),
                Arguments.of("12345678909", "aaaaaaaaaabbbbbbbbbbaaaaaaaaaabbbbbbbbbbaaaaaaaaaad", "Endereco de teste", 1234, "SP"),
                Arguments.of("12345678909", "Nome de teste", null, 1234, "SP"),
                Arguments.of("12345678909", "Nome de teste", "", 1234, "SP"),
                Arguments.of("12345678909", "Nome de teste", " ", 1234, "SP"),
                Arguments.of("12345678909", "Nome de teste", "ab", 1234, "SP"),
                Arguments.of("12345678909", "Nome de teste", "aaaaaaaaaabbbbbbbbbbaaaaaaaaaabbbbbbbbbbaaaaaaaaaad", 1234, "SP"),
                Arguments.of("12345678909", "Nome de teste", "Endereco de teste", null, "SP"),
                Arguments.of("12345678909", "Nome de teste", "Endereco de teste", -1, "SP"),
                Arguments.of("12345678909", "Nome de teste", "Endereco de teste", 0, "SP"),
                Arguments.of("12345678909", "Nome de teste", "Endereco de teste", 100000, "SP"),
                Arguments.of("12345678909", "Nome de teste", "Endereco de teste", 1234, null),
                Arguments.of("12345678909", "Nome de teste", "Endereco de teste", 1234, ""),
                Arguments.of("12345678909", "Nome de teste", "Endereco de teste", 1234, " "),
                Arguments.of("12345678909", "Nome de teste", "Endereco de teste", 1234, "a"),
                Arguments.of("12345678909", "Nome de teste", "Endereco de teste", 1234, "aaa")
        );
    }

}
