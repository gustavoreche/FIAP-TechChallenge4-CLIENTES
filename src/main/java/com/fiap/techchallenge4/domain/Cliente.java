package com.fiap.techchallenge4.domain;

import lombok.Getter;

import java.util.Objects;

@Getter
public class Cliente {
    private String cpf;
    private String nome;
    private String enderecoLogradouro;
    private Integer enderecoNumero;
    private String enderecoSiglaEstado;

    private static final String REGEX_SIGLA_ESTADO = "^[A-Za-z]{2}$";

    public Cliente(final String cpf,
                   final String nome,
                   final String enderecoLogradouro,
                   final Integer enderecoNumero,
                   final String enderecoSiglaEstado) {
        if (Objects.isNull(nome) || nome.isEmpty()) {
            throw new IllegalArgumentException("NOME NAO PODE SER NULO OU VAZIO!");
        }
        if (nome.length() < 3 || nome.length() > 50) {
            throw new IllegalArgumentException("O NOME deve ter no mínimo 3 letras e no máximo 50 letras");
        }

        if (Objects.isNull(enderecoLogradouro) || enderecoLogradouro.isEmpty()) {
            throw new IllegalArgumentException("ENDERECO LOGRADOURO NAO PODE SER NULO OU VAZIO!");
        }
        if (enderecoLogradouro.length() < 5 || enderecoLogradouro.length() > 50) {
            throw new IllegalArgumentException("O ENDERECO LOGRADOURO deve ter no mínimo 5 letras e no máximo 50 letras");
        }

        if (Objects.isNull(enderecoNumero) || enderecoNumero <= 0 || enderecoNumero > 99999) {
            throw new IllegalArgumentException("ENDERECO NUMERO NAO PODE SER NULO OU MENOR E IGUAL A ZERO OU MAIOR QUE 99999!");
        }

        if (Objects.isNull(enderecoSiglaEstado) || enderecoSiglaEstado.isEmpty()) {
            throw new IllegalArgumentException("ENDERECO SIGLA ESTADO NAO PODE SER NULO OU VAZIO!");
        }
        if (!enderecoSiglaEstado.matches(REGEX_SIGLA_ESTADO)) {
            throw new IllegalArgumentException("ENDERECO SIGLA ESTADO DO CLIENTE INVÁLIDO!");
        }

        this.cpf = new Cpf(cpf).getNumero();
        this.nome = nome;
        this.enderecoLogradouro = enderecoLogradouro;
        this.enderecoNumero = enderecoNumero;
        this.enderecoSiglaEstado = enderecoSiglaEstado;
    }

}
