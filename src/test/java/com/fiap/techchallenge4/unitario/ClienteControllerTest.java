package com.fiap.techchallenge4.unitario;

import com.fiap.techchallenge4.infrastructure.controller.ClienteController;
import com.fiap.techchallenge4.infrastructure.controller.dto.AtualizaClienteDTO;
import com.fiap.techchallenge4.infrastructure.controller.dto.ClienteDTO;
import com.fiap.techchallenge4.infrastructure.controller.dto.CriaClienteDTO;
import com.fiap.techchallenge4.useCase.impl.ClienteUseCaseImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;

public class ClienteControllerTest {

    @Test
    public void cadastra_deveRetornar201_salvaNaBaseDeDados() {
        // preparação
        var service = Mockito.mock(ClienteUseCaseImpl.class);
        Mockito.when(service.cadastra(
                                any(CriaClienteDTO.class)
                        )
                )
                .thenReturn(
                        true
                );

        var controller = new ClienteController(service);

        // execução
        var produto = controller.cadastra(
                new CriaClienteDTO(
                        "92084815061",
                        "Nome de Teste",
                        "Endereço de Teste",
                        1234,
                        "SP"
                )
        );

        // avaliação
        Assertions.assertEquals(HttpStatus.CREATED, produto.getStatusCode());
    }

    @Test
    public void cadastra_deveRetornar409_naoSalvaNaBaseDeDados() {
        // preparação
        var service = Mockito.mock(ClienteUseCaseImpl.class);
        Mockito.when(service.cadastra(
                        any(CriaClienteDTO.class)
                        )
                )
                .thenReturn(
                        false
                );

        var controller = new ClienteController(service);

        // execução
        var produto = controller.cadastra(
                new CriaClienteDTO(
                        "92084815061",
                        "Nome de Teste",
                        "Endereço de Teste",
                        1234,
                        "SP"
                )
        );

        // avaliação
        Assertions.assertEquals(HttpStatus.CONFLICT, produto.getStatusCode());
    }

    @Test
    public void atualiza_deveRetornar200_salvaNaBaseDeDados() {
        // preparação
        var service = Mockito.mock(ClienteUseCaseImpl.class);
        Mockito.when(service.atualiza(
                                any(String.class),
                                any(AtualizaClienteDTO.class)
                        )
                )
                .thenReturn(
                        true
                );

        var controller = new ClienteController(service);

        // execução
        var produto = controller.atualiza(
                "92084815061",
                new AtualizaClienteDTO(
                        "Nome de Teste",
                        "Endereço de Teste",
                        1234,
                        "SP"
                )
        );

        // avaliação
        Assertions.assertEquals(HttpStatus.OK, produto.getStatusCode());
    }

    @Test
    public void atualiza_deveRetornar204_naoSalvaNaBaseDeDados() {
        // preparação
        var service = Mockito.mock(ClienteUseCaseImpl.class);
        Mockito.when(service.atualiza(
                                any(String.class),
                                any(AtualizaClienteDTO.class)
                        )
                )
                .thenReturn(
                        false
                );

        var controller = new ClienteController(service);

        // execução
        var produto = controller.atualiza(
                "92084815061",
                new AtualizaClienteDTO(
                        "Nome de Teste",
                        "Endereço de Teste",
                        1234,
                        "SP"
                )
        );

        // avaliação
        Assertions.assertEquals(HttpStatus.NO_CONTENT, produto.getStatusCode());
    }

    @Test
    public void deleta_deveRetornar200_deletaNaBaseDeDados() {
        // preparação
        var service = Mockito.mock(ClienteUseCaseImpl.class);
        Mockito.when(service.deleta(
                                any(String.class)
                        )
                )
                .thenReturn(
                        true
                );

        var controller = new ClienteController(service);

        // execução
        var produto = controller.deleta(
                "92084815061"
        );

        // avaliação
        Assertions.assertEquals(HttpStatus.OK, produto.getStatusCode());
    }

    @Test
    public void deleta_deveRetornar204_naoDeletaNaBaseDeDados() {
        // preparação
        var service = Mockito.mock(ClienteUseCaseImpl.class);
        Mockito.when(service.deleta(
                                any(String.class)
                        )
                )
                .thenReturn(
                        false
                );

        var controller = new ClienteController(service);

        // execução
        var produto = controller.deleta(
                "92084815061"
        );

        // avaliação
        Assertions.assertEquals(HttpStatus.NO_CONTENT, produto.getStatusCode());
    }

    @Test
    public void busca_deveRetornar200_buscaNaBaseDeDados() {
        // preparação
        var service = Mockito.mock(ClienteUseCaseImpl.class);
        Mockito.when(service.busca(
                        "92084815061"
                        )
                )
                .thenReturn(
                        new ClienteDTO(
                                "92084815061",
                                "Nome de Teste",
                                "Endereço de Teste",
                                1234,
                                "SP",
                                LocalDateTime.now()
                        )
                );

        var controller = new ClienteController(service);

        // execução
        var produto = controller.busca(
                "92084815061"
        );

        // avaliação
        Assertions.assertEquals(HttpStatus.OK, produto.getStatusCode());
    }

    @Test
    public void busca_deveRetornar204_naoEncontraNaBaseDeDados() {
        // preparação
        var service = Mockito.mock(ClienteUseCaseImpl.class);
        Mockito.when(service.busca(
                        "92084815061"
                        )
                )
                .thenReturn(
                        null
                );

        var controller = new ClienteController(service);

        // execução
        var produto = controller.busca(
                "92084815061"
        );

        // avaliação
        Assertions.assertEquals(HttpStatus.NO_CONTENT, produto.getStatusCode());
    }

    @ParameterizedTest
    @MethodSource("requestValidandoCampos")
    public void cadastra_camposInvalidos_naoBuscaNaBaseDeDados(String cpf,
                                                               String nome,
                                                               String enderecoLogradouro,
                                                               Integer enderecoNumero,
                                                               String enderecoSiglaEstado) {
        // preparação
        var service = Mockito.mock(ClienteUseCaseImpl.class);
        Mockito.doThrow(
                        new IllegalArgumentException("Campos inválidos!")
                )
                .when(service)
                .cadastra(
                        any(CriaClienteDTO.class)
                );

        var controller = new ClienteController(service);

        // execução e avaliação
        var excecao = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            controller.cadastra(
                    new CriaClienteDTO(
                            cpf,
                            nome,
                            enderecoLogradouro,
                            enderecoNumero,
                            enderecoSiglaEstado
                    )
            );
        });
    }

    @ParameterizedTest
    @MethodSource("requestValidandoCampos")
    public void atualiza_camposInvalidos_naoBuscaNaBaseDeDados(String cpf,
                                                               String nome,
                                                               String enderecoLogradouro,
                                                               Integer enderecoNumero,
                                                               String enderecoSiglaEstado) {
        // preparação
        var service = Mockito.mock(ClienteUseCaseImpl.class);
        Mockito.doThrow(
                        new IllegalArgumentException("Campos inválidos!")
                )
                .when(service)
                .atualiza(
                        any(String.class),
                        any(AtualizaClienteDTO.class)
                );

        var controller = new ClienteController(service);

        // execução e avaliação
        var excecao = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            controller.atualiza(
                    Objects.isNull(cpf) ? "-1L" : cpf,
                    new AtualizaClienteDTO(
                            nome,
                            enderecoLogradouro,
                            enderecoNumero,
                            enderecoSiglaEstado
                    )
            );
        });
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
        var service = Mockito.mock(ClienteUseCaseImpl.class);
        Mockito.doThrow(
                        new IllegalArgumentException("Campos inválidos!")
                )
                .when(service)
                .deleta(
                        any(String.class)
                );

        var controller = new ClienteController(service);

        // execução e avaliação
        var excecao = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            controller.deleta(
                    cpf
            );
        });
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
        var service = Mockito.mock(ClienteUseCaseImpl.class);
        Mockito.doThrow(
                        new IllegalArgumentException("Campos inválidos!")
                )
                .when(service)
                .busca(
                        any(String.class)
                );

        var controller = new ClienteController(service);

        // execução e avaliação
        var excecao = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            controller.busca(
                    cpf
            );
        });
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
