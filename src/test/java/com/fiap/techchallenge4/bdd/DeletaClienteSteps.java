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


public class DeletaClienteSteps {

    private Response response;
    private String cpf;

    private String pegaCpf() {
        final var currentTime = System.currentTimeMillis();
        final var timeString = Long.toString(currentTime);
        return timeString.substring(1, 12);
    }

    @Dado("que informo um cliente que ja esta cadastrado")
    public void queInformoUmClienteQueJaEstaCadastrado() {
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

    @Dado("que informo um cliente nao cadastrado")
    public void queInformoUmClienteNaoCadastrado() {
        this.cpf = this.pegaCpf();
    }


    @Quando("deleto esse cliente")
    public void deletoEsseCliente() {
        RestAssured.baseURI = "http://localhost:8083";
        this.response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .delete(URL_CLIENTE_COM_CPF.replace("{cpf}", this.cpf));
    }

    @Entao("recebo uma resposta que o cliente foi deletado com sucesso")
    public void receboUmaRespostaQueOClienteFoiDeletadoComSucesso() {
        this.response
                .prettyPeek()
                .then()
                .statusCode(HttpStatus.OK.value())
        ;
    }

    @Entao("recebo uma resposta que o cliente nao foi cadastrado")
    public void receboUmaRespostaQueOClienteNaoFoiCadastrado() {
        this.response
                .prettyPeek()
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value())
        ;
    }

}
