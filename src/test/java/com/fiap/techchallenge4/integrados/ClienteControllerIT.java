package com.fiap.techchallenge4.integrados;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.techchallenge4.infrastructure.controller.dto.AtualizaClienteDTO;
import com.fiap.techchallenge4.infrastructure.controller.dto.ClienteDTO;
import com.fiap.techchallenge4.infrastructure.controller.dto.CriaClienteDTO;
import com.fiap.techchallenge4.infrastructure.model.ClienteEntity;
import com.fiap.techchallenge4.infrastructure.repository.ClienteRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static com.fiap.techchallenge4.infrastructure.controller.ClienteController.URL_CLIENTE;
import static com.fiap.techchallenge4.infrastructure.controller.ClienteController.URL_CLIENTE_COM_CPF;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@AutoConfigureMockMvc
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ClienteControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ClienteRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void inicializaLimpezaDoDatabase() {
        this.repository.deleteAll();
    }

    @AfterAll
    void finalizaLimpezaDoDatabase() {
        this.repository.deleteAll();
    }

    @Test
    public void cadastra_deveRetornar201_salvaNaBaseDeDados() throws Exception {

        var request = new CriaClienteDTO(
                "92084815061",
                "Nome de teste",
                "Rua de teste",
                100,
                "SP"
        );
        var objectMapper = this.objectMapper
                .writer()
                .withDefaultPrettyPrinter();
        var jsonRequest = objectMapper.writeValueAsString(request);

        this.mockMvc
                .perform(MockMvcRequestBuilders.post(URL_CLIENTE)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .isCreated()
                )
                .andReturn();

        var cliente = this.repository.findAll().get(0);

        Assertions.assertEquals("92084815061", cliente.getCpf());
        Assertions.assertEquals("Nome de teste", cliente.getNome());
        Assertions.assertEquals("Rua de teste", cliente.getEnderecoLogradouro());
        Assertions.assertEquals(100, cliente.getEnderecoNumero());
        Assertions.assertEquals("SP", cliente.getEnderecoSiglaEstado());
        Assertions.assertNotNull(cliente.getDataDeCriacao());
    }

    @Test
    public void cadastra_deveRetornar409_naoSalvaNaBaseDeDados() throws Exception {

        this.repository.save(ClienteEntity.builder()
                .cpf("92084815061")
                .nome("Nome de teste")
                .enderecoLogradouro("Rua de teste")
                .enderecoNumero(100)
                .enderecoSiglaEstado("SP")
                .dataDeCriacao(LocalDateTime.now())
                .build());

        var request = new CriaClienteDTO(
                "92084815061",
                "Nome de teste",
                "Rua de teste",
                100,
                "SP"
        );
        var objectMapper = this.objectMapper
                .writer()
                .withDefaultPrettyPrinter();
        var jsonRequest = objectMapper.writeValueAsString(request);

        this.mockMvc
                .perform(MockMvcRequestBuilders.post(URL_CLIENTE)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .isConflict()
                )
                .andReturn();

        var cliente = this.repository.findAll().get(0);

        Assertions.assertEquals(1, this.repository.findAll().size());
        Assertions.assertEquals("92084815061", cliente.getCpf());
        Assertions.assertEquals("Nome de teste", cliente.getNome());
        Assertions.assertEquals("Rua de teste", cliente.getEnderecoLogradouro());
        Assertions.assertEquals(100, cliente.getEnderecoNumero());
        Assertions.assertEquals("SP", cliente.getEnderecoSiglaEstado());
        Assertions.assertNotNull(cliente.getDataDeCriacao());
    }

    @Test
    public void atualiza_deveRetornar200_salvaNaBaseDeDados() throws Exception {

        this.repository.save(ClienteEntity.builder()
                .cpf("92084815061")
                .nome("Nome de teste")
                .enderecoLogradouro("Avenida de teste")
                .enderecoNumero(100)
                .enderecoSiglaEstado("RJ")
                .dataDeCriacao(LocalDateTime.now())
                .build());

        var request = new AtualizaClienteDTO(
                "Nome de teste",
                "Rua de teste",
                100,
                "SP"
        );
        var objectMapper = this.objectMapper
                .writer()
                .withDefaultPrettyPrinter();
        var jsonRequest = objectMapper.writeValueAsString(request);

        this.mockMvc
                .perform(MockMvcRequestBuilders.put(URL_CLIENTE_COM_CPF.replace("{cpf}", "92084815061"))
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .isOk()
                )
                .andReturn();

        var cliente = this.repository.findAll().get(0);

        Assertions.assertEquals("92084815061", cliente.getCpf());
        Assertions.assertEquals("Nome de teste", cliente.getNome());
        Assertions.assertEquals("Rua de teste", cliente.getEnderecoLogradouro());
        Assertions.assertEquals(100, cliente.getEnderecoNumero());
        Assertions.assertEquals("SP", cliente.getEnderecoSiglaEstado());
        Assertions.assertNotNull(cliente.getDataDeCriacao());
    }

    @Test
    public void atualiza_deveRetornar204_naoSalvaNaBaseDeDados() throws Exception {

        var request = new AtualizaClienteDTO(
                "Nome de teste",
                "Rua de teste",
                100,
                "SP"
        );
        var objectMapper = this.objectMapper
                .writer()
                .withDefaultPrettyPrinter();
        var jsonRequest = objectMapper.writeValueAsString(request);

        this.mockMvc
                .perform(MockMvcRequestBuilders.put(URL_CLIENTE_COM_CPF.replace("{cpf}", "92084815061"))
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .isNoContent()
                )
                .andReturn();

        Assertions.assertEquals(0, this.repository.findAll().size());
    }

    @Test
    public void deleta_deveRetornar200_deletaNaBaseDeDados() throws Exception {

        this.repository.save(ClienteEntity.builder()
                .cpf("92084815061")
                .nome("Nome de teste")
                .enderecoLogradouro("Avenida de teste")
                .enderecoNumero(100)
                .enderecoSiglaEstado("RJ")
                .dataDeCriacao(LocalDateTime.now())
                .build());

        this.mockMvc
                .perform(MockMvcRequestBuilders.delete(URL_CLIENTE_COM_CPF.replace("{cpf}", "92084815061"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .isOk()
                )
                .andReturn();

        Assertions.assertEquals(0, this.repository.findAll().size());
    }

    @Test
    public void deleta_deveRetornar204_naoDeletaNaBaseDeDados() throws Exception {

        this.mockMvc
                .perform(MockMvcRequestBuilders.delete(URL_CLIENTE_COM_CPF.replace("{cpf}", "92084815061"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .isNoContent()
                )
                .andReturn();

        Assertions.assertEquals(0, this.repository.findAll().size());
    }

    @Test
    public void busca_deveRetornar200_buscaNaBaseDeDados() throws Exception {

        this.repository.save(ClienteEntity.builder()
                .cpf("92084815061")
                .nome("Nome de teste")
                .enderecoLogradouro("Rua de teste")
                .enderecoNumero(100)
                .enderecoSiglaEstado("SP")
                .dataDeCriacao(LocalDateTime.now())
                .build());

        var response = this.mockMvc
                .perform(MockMvcRequestBuilders.get(URL_CLIENTE_COM_CPF.replace("{cpf}", "92084815061"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .isOk()
                )
                .andReturn();
        var responseAppString = response.getResponse().getContentAsString();
        var responseApp = this.objectMapper
                .readValue(responseAppString, new TypeReference<ClienteDTO>() {});

        Assertions.assertEquals("92084815061", responseApp.cpf());
        Assertions.assertEquals("Nome de teste", responseApp.nome());
        Assertions.assertEquals("Rua de teste", responseApp.enderecoLogradouro());
        Assertions.assertEquals(100, responseApp.enderecoNumero());
        Assertions.assertEquals("SP", responseApp.enderecoSiglaEstado());
        Assertions.assertNotNull(responseApp.dataDeCriacao());
    }

    @Test
    public void busca_deveRetornar204_naoEcontraNaBaseDeDados() throws Exception {

        this.mockMvc
                .perform(MockMvcRequestBuilders.get(URL_CLIENTE_COM_CPF.replace("{cpf}", "92084815061"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .isNoContent()
                )
                .andReturn();

        Assertions.assertEquals(0, this.repository.findAll().size());
    }

    @ParameterizedTest
    @MethodSource("requestValidandoCampos")
    public void cadastra_camposInvalidos_naoBuscaNaBaseDeDados(String cpf,
                                                               String nome,
                                                               String enderecoLogradouro,
                                                               Integer enderecoNumero,
                                                               String enderecoSiglaEstado) throws Exception {
        var request = new CriaClienteDTO(
                cpf,
                nome,
                enderecoLogradouro,
                enderecoNumero,
                enderecoSiglaEstado
        );
        var objectMapper = this.objectMapper
                .writer()
                .withDefaultPrettyPrinter();
        var jsonRequest = objectMapper.writeValueAsString(request);

        this.mockMvc
                .perform(MockMvcRequestBuilders.post(URL_CLIENTE)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .isBadRequest()
                );
    }

    @ParameterizedTest
    @MethodSource("requestValidandoCampos")
    public void atualiza_camposInvalidos_naoBuscaNaBaseDeDados(String cpf,
                                                               String nome,
                                                               String enderecoLogradouro,
                                                               Integer enderecoNumero,
                                                               String enderecoSiglaEstado) throws Exception {
        var request = new AtualizaClienteDTO(
                nome,
                enderecoLogradouro,
                enderecoNumero,
                enderecoSiglaEstado
        );
        var objectMapper = this.objectMapper
                .writer()
                .withDefaultPrettyPrinter();
        var jsonRequest = objectMapper.writeValueAsString(request);

        this.mockMvc
                .perform(MockMvcRequestBuilders.put(URL_CLIENTE_COM_CPF.replace("{cpf}", cpf))
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .isBadRequest()
                );
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "-1000",
            " ",
            "teste",
            "1234567891",
            "123456789123"
    })
    public void deleta_camposInvalidos_naoDeletaNaBaseDeDados(String cpf) throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.delete(URL_CLIENTE_COM_CPF.replace("{cpf}", cpf))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .isBadRequest()
                );
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "-1000",
            " ",
            "teste",
            "1234567891",
            "123456789123"
    })
    public void busca_camposInvalidos_naoBuscaNaBaseDeDados(String cpf) throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get(URL_CLIENTE_COM_CPF.replace("{cpf}", cpf))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .isBadRequest()
                );
    }

    private static Stream<Arguments> requestValidandoCampos() {
        return Stream.of(
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
