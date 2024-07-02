package com.fiap.techchallenge4.performance;

import io.gatling.javaapi.core.ActionBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import java.time.Duration;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class PerformanceTestSimulation extends Simulation {

    private final HttpProtocolBuilder httpProtocol = http
            .baseUrl("http://localhost:8083");

    ActionBuilder cadastraClienteRequest = http("cadastra cliente")
            .post("/cliente")
            .header("Content-Type", "application/json")
            .body(StringBody("""
                              {
                                "cpf": "${cpf}",
                                "nome": "Client Teste",
                                "enderecoLogradouro": "Rua teste",
                                "enderecoNumero": 1234,
                                "enderecoSiglaEstado": "SP"
                              }
                    """))
            .check(status().is(201));

    ActionBuilder atualizaClienteRequest = http("atualiza cliente")
            .put("/cliente/${cpf}")
            .header("Content-Type", "application/json")
            .body(StringBody("""
                              {
                                "nome": "Client Teste",
                                "enderecoLogradouro": "Rua teste",
                                "enderecoNumero": 1234,
                                "enderecoSiglaEstado": "SP"
                              }
                    """))
            .check(status().is(200));

    ActionBuilder deletaClienteRequest = http("deleta cliente")
            .delete("/cliente/${cpf}")
            .header("Content-Type", "application/json")
            .check(status().is(200));

    ActionBuilder buscaClienteRequest = http("busca cliente")
            .get("/cliente/${cpf}")
            .header("Content-Type", "application/json")
            .check(status().is(200));

    ScenarioBuilder cenarioCadastraCliente = scenario("Cadastra cliente")
            .exec(session -> {
                final var currentTime = System.currentTimeMillis();
                final var timeString = Long.toString(currentTime);
                final var finalNumber = timeString.substring(1, 12);
                return session.set("cpf", finalNumber);
            })
            .exec(cadastraClienteRequest);

    ScenarioBuilder cenarioAtualizaCliente = scenario("Atualiza cliente")
            .exec(session -> {
                final var currentTime = System.currentTimeMillis() + 123456789L;
                final var timeString = Long.toString(currentTime);
                final var finalNumber = timeString.substring(1, 12);
                return session.set("cpf", finalNumber);
            })
            .exec(cadastraClienteRequest)
            .exec(atualizaClienteRequest);

    ScenarioBuilder cenarioDeletaCliente = scenario("Deleta cliente")
            .exec(session -> {
                final var currentTime = System.currentTimeMillis() + 333333333L;
                final var timeString = Long.toString(currentTime);
                final var finalNumber = timeString.substring(1, 12);
                return session.set("cpf", finalNumber);
            })
            .exec(cadastraClienteRequest)
            .exec(deletaClienteRequest);

    ScenarioBuilder cenarioBuscaCliente = scenario("Busca cliente")
            .exec(session -> {
                final var currentTime = System.currentTimeMillis() + 777777777L;
                final var timeString = Long.toString(currentTime);
                final var finalNumber = timeString.substring(1, 12);
                return session.set("cpf", finalNumber);
            })
            .exec(cadastraClienteRequest)
            .exec(buscaClienteRequest);

    {
        setUp(
                cenarioCadastraCliente.injectOpen(
                        rampUsersPerSec(1)
                                .to(10)
                                .during(Duration.ofSeconds(10)),
                        constantUsersPerSec(10)
                                .during(Duration.ofSeconds(20)),
                        rampUsersPerSec(10)
                                .to(1)
                                .during(Duration.ofSeconds(10))),
                cenarioAtualizaCliente.injectOpen(
                        rampUsersPerSec(1)
                                .to(10)
                                .during(Duration.ofSeconds(10)),
                        constantUsersPerSec(10)
                                .during(Duration.ofSeconds(20)),
                        rampUsersPerSec(10)
                                .to(1)
                                .during(Duration.ofSeconds(10))),
                cenarioDeletaCliente.injectOpen(
                        rampUsersPerSec(1)
                                .to(10)
                                .during(Duration.ofSeconds(10)),
                        constantUsersPerSec(10)
                                .during(Duration.ofSeconds(20)),
                        rampUsersPerSec(10)
                                .to(1)
                                .during(Duration.ofSeconds(10))),
                cenarioBuscaCliente.injectOpen(
                        rampUsersPerSec(1)
                                .to(10)
                                .during(Duration.ofSeconds(10)),
                        constantUsersPerSec(10)
                                .during(Duration.ofSeconds(20)),
                        rampUsersPerSec(10)
                                .to(1)
                                .during(Duration.ofSeconds(10)))
        )
                .protocols(httpProtocol)
                .assertions(
                        global().responseTime().max().lt(600),
                        global().failedRequests().count().is(0L));
    }
}