package com.fiap.techchallenge4.integrados;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.techchallenge4.domain.StatusEntregaControllerEnum;
import com.fiap.techchallenge4.infrastructure.consumer.response.AtualizaClienteConsumerDTO;
import com.fiap.techchallenge4.infrastructure.model.ClienteEntity;
import com.fiap.techchallenge4.infrastructure.repository.ClienteNotificadoRepository;
import com.fiap.techchallenge4.infrastructure.repository.ClienteRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.function.StreamBridge;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ConsumerRecebeAtualizacaoStatusIT {

    @Autowired
    private StreamBridge streamBridge;

    @Autowired
    ClienteRepository repositoryCliente;

    @Autowired
    ClienteNotificadoRepository repositoryClienteNotificado;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void inicializaLimpezaDoDatabase() {
        this.repositoryCliente.deleteAll();
        this.repositoryClienteNotificado.deleteAll();
    }

    @AfterAll
    void finalizaLimpezaDoDatabase() {
        this.repositoryCliente.deleteAll();
        this.repositoryClienteNotificado.deleteAll();
    }

    @Test
    public void notificaCliente_EMTRANSPORTE_sucesso() throws ExecutionException, InterruptedException {

        this.repositoryCliente.save(ClienteEntity.builder()
                .cpf("92084815061")
                .nome("Nome de teste")
                .enderecoLogradouro("Rua de teste")
                .enderecoNumero(100)
                .enderecoSiglaEstado("SP")
                .dataDeCriacao(LocalDateTime.now())
                .build());

        var producer = CompletableFuture.runAsync(() -> {
            this.streamBridge
                    .send("cliente-atualiza-status", new AtualizaClienteConsumerDTO(
                            "92084815061",
                            StatusEntregaControllerEnum.EM_TRANSPORTE
                            )
                    );
        });

        producer.get();
        Thread.sleep(2000);

        var clienteNotificado = this.repositoryClienteNotificado.findAll().get(0);

        Assertions.assertEquals("92084815061", clienteNotificado.getCpf());
        Assertions.assertEquals(StatusEntregaControllerEnum.EM_TRANSPORTE, clienteNotificado.getStatusEntrega());
        Assertions.assertNotNull(clienteNotificado.getDataDeCriacao());
    }

    @Test
    public void notificaCliente_EMTRANSPORTE_clienteNaoEncontrado() throws ExecutionException, InterruptedException {
        var producer = CompletableFuture.runAsync(() -> {
            this.streamBridge
                    .send("cliente-atualiza-status", new AtualizaClienteConsumerDTO(
                                    "92084815061",
                                    StatusEntregaControllerEnum.EM_TRANSPORTE
                            )
                    );
        });

        producer.get();
        Thread.sleep(2000);

        Assertions.assertEquals(0, this.repositoryClienteNotificado.findAll().size());
    }

    @Test
    public void notificaCliente_ENTREGUE_sucesso() throws ExecutionException, InterruptedException {

        this.repositoryCliente.save(ClienteEntity.builder()
                .cpf("92084815061")
                .nome("Nome de teste")
                .enderecoLogradouro("Rua de teste")
                .enderecoNumero(100)
                .enderecoSiglaEstado("SP")
                .dataDeCriacao(LocalDateTime.now())
                .build());

        var producer = CompletableFuture.runAsync(() -> {
            this.streamBridge
                    .send("cliente-atualiza-status", new AtualizaClienteConsumerDTO(
                                    "92084815061",
                                    StatusEntregaControllerEnum.ENTREGUE
                            )
                    );
        });

        producer.get();
        Thread.sleep(2000);

        var clienteNotificado = this.repositoryClienteNotificado.findAll().get(0);

        Assertions.assertEquals("92084815061", clienteNotificado.getCpf());
        Assertions.assertEquals(StatusEntregaControllerEnum.ENTREGUE, clienteNotificado.getStatusEntrega());
        Assertions.assertNotNull(clienteNotificado.getDataDeCriacao());
    }

    @Test
    public void notificaCliente_ENTREGUE_clienteNaoEncontrado() throws ExecutionException, InterruptedException {
        var producer = CompletableFuture.runAsync(() -> {
            this.streamBridge
                    .send("cliente-atualiza-status", new AtualizaClienteConsumerDTO(
                                    "92084815061",
                                    StatusEntregaControllerEnum.ENTREGUE
                            )
                    );
        });

        producer.get();
        Thread.sleep(2000);

        Assertions.assertEquals(0, this.repositoryClienteNotificado.findAll().size());
    }

}
