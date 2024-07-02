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
import static com.fiap.techchallenge4.infrastructure.controller.ClienteController.URL_CLIENTE_COM_CPF;
import static io.restassured.RestAssured.given;


public class BuscaClienteSteps {

    private Response response;
    private String cpf;

    private String pegaCpf() {
        final var currentTime = System.currentTimeMillis();
        final var timeString = Long.toString(currentTime);
        return timeString.substring(1, 12);
    }

    @Dado("que busco um cliente que ja esta cadastrado")
    public void queBuscoUmClienteQueJaEstaCadastrado() {
        this.cpf = this.pegaCpf();
        final var request = new CriaClienteDTO(
                this.cpf,
                "Cliente Teste",
                "Rua teste",
                1234,
                "SP"
        );

        RestAssured.baseURI = "http://localhost:8083";
        this.response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post(URL_CLIENTE);
    }

    @Dado("que busco um cliente nao cadastrado")
    public void queBuscoUmClienteNaoCadastrado() {
        this.cpf = this.pegaCpf();
    }


    @Quando("busco esse cliente")
    public void buscoEsseCliente() {
        RestAssured.baseURI = "http://localhost:8083";
        this.response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get(URL_CLIENTE_COM_CPF.replace("{cpf}", this.cpf));
    }

    @Entao("recebo uma resposta que o cliente foi encontrado com sucesso")
    public void receboUmaRespostaQueOClienteFoiEncontradoComSucesso() {
        this.response
                .prettyPeek()
                .then()
                .statusCode(HttpStatus.OK.value())
        ;
    }

    @Entao("recebo uma resposta que o cliente nao foi encontrado")
    public void receboUmaRespostaQueOClienteNaoFoiEncontrado() {
        this.response
                .prettyPeek()
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value())
        ;
    }

}
