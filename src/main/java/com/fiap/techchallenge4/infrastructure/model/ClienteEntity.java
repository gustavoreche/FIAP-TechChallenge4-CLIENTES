package com.fiap.techchallenge4.infrastructure.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_cliente")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteEntity {

    @Id
    private String cpf;
    private String nome;
    private String enderecoLogradouro;
    private Integer enderecoNumero;
    private String enderecoSiglaEstado;
    private LocalDateTime dataDeCriacao;

}
