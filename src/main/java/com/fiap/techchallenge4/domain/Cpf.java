package com.fiap.techchallenge4.domain;

import lombok.Getter;

import java.util.Objects;

@Getter
public class Cpf {
    private String numero;

    private static final String REGEX_CPF = "(^\\d{3}\\.?\\d{3}\\.?\\d{3}-?\\d{2}$)";

    public Cpf(final String numero) {
        if (Objects.isNull(numero) || numero.isEmpty()) {
            throw new IllegalArgumentException("CPF NAO PODE SER NULO OU VAZIO!");
        }
        if (!numero.matches(REGEX_CPF)) {
            throw new IllegalArgumentException("CPF DO CLIENTE INVÁLIDO!");
        }

        this.numero = numero;
    }

}
