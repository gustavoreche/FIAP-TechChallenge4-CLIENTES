package com.fiap.techchallenge4.bdd;

import com.fiap.techchallenge4.infrastructure.controller.dto.CriaClienteDTO;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static com.fiap.techchallenge4.infrastructure.controller.ClienteController.URL_CLIENTE;
import static io.restassured.RestAssured.given;


public class CadastraClienteSteps {

    private Response response;
    private CriaClienteDTO request;
    private final String cpf = pegaCpf();

    private String pegaCpf() {
        final var currentTime = System.currentTimeMillis();
        final var timeString = Long.toString(currentTime);
        return timeString.substring(1, 12);
    }

    @Dado("que tenho dados validos de um cliente")
    public void tenhoDadosValidosDeUmCliente() {
        this.request = new CriaClienteDTO(
                this.cpf,
                "Cliente Teste",
                "Rua teste",
                1234,
                "SP"
        );
    }

    @Dado("que tenho dados validos de um cliente que ja esta cadastrado")
    public void tenhoDadosValidosDeUmClienteQueJaEstaCadastrado() {
        this.request = new CriaClienteDTO(
                this.cpf,
                "Cliente Teste",
                "Rua teste",
                1234,
                "SP"
        );

        this.cadastroEsseCliente();
    }

    @Quando("cadastro esse cliente")
    public void cadastroEsseCliente() {
        RestAssured.baseURI = "http://localhost:8083";
        this.response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(this.request)
                .when()
                .post(URL_CLIENTE);
    }

    @Entao("recebo uma resposta que o cliente foi cadastrado com sucesso")
    public void receboUmaRespostaQueOClienteFoiCadastradoComSucesso() {
        this.response
                .prettyPeek()
                .then()
                .statusCode(HttpStatus.CREATED.value())
        ;
    }

    @Entao("recebo uma resposta que o cliente ja esta cadastrado")
    public void receboUmaRespostaQueOClienteJaEstaCadastrado() {
        this.response
                .prettyPeek()
                .then()
                .statusCode(HttpStatus.CONFLICT.value())
        ;
    }

}
