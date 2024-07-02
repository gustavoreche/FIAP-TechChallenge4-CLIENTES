package com.fiap.techchallenge4.bdd;

import com.fiap.techchallenge4.infrastructure.controller.dto.AtualizaClienteDTO;
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


public class AtualizaClienteSteps {

    private Response response;
    private AtualizaClienteDTO request;
    private String cpf;

    private String pegaCpf() {
        final var currentTime = System.currentTimeMillis();
        final var timeString = Long.toString(currentTime);
        return timeString.substring(1, 12);
    }

    @Dado("que tenho os dados validos de um cliente que ja esta cadastrado")
    public void tenhoOsDadosValidosDeUmClienteQueJaEstaCadastrado() {
        this.cpf = this.pegaCpf();
        final var request = new CriaClienteDTO(
                this.cpf,
                "Cliente Teste",
                "Rua teste",
                1234,
                "SP"
        );

        RestAssured.baseURI = "http://localhost:8083";
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post(URL_CLIENTE);

        this.request = new AtualizaClienteDTO(
                "Cliente Teste",
                "Rua teste",
                9876,
                "RJ"
        );
    }

    @Dado("que tenho os dados validos de um cliente")
    public void tenhoOsDadosValidosDeUmCliente() {
        this.cpf = this.pegaCpf();
        this.request = new AtualizaClienteDTO(
                "Cliente Teste",
                "Rua teste",
                9876,
                "RJ"
        );
    }


    @Quando("atualizo esse cliente")
    public void atualizoEsseCliente() {
        RestAssured.baseURI = "http://localhost:8083";
        this.response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(this.request)
                .when()
                .put(URL_CLIENTE_COM_CPF.replace("{cpf}", this.cpf));
    }

    @Entao("recebo uma resposta que o cliente foi atualizado com sucesso")
    public void receboUmaRespostaQueOClienteFoiAtualizadoComSucesso() {
        this.response
                .prettyPeek()
                .then()
                .statusCode(HttpStatus.OK.value())
        ;
    }

    @Entao("recebo uma resposta que o cliente nao esta cadastrado")
    public void receboUmaRespostaQueOClienteNaoEstaCadastrado() {
        this.response
                .prettyPeek()
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value())
        ;
    }

}
