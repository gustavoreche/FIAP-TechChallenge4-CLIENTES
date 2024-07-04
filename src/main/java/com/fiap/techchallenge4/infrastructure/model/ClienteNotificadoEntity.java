package com.fiap.techchallenge4.infrastructure.model;

import com.fiap.techchallenge4.domain.StatusEntregaControllerEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_cliente_notificado")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteNotificadoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cpf;
    @Enumerated(EnumType.STRING)
    private StatusEntregaControllerEnum statusEntrega;
    private LocalDateTime dataDeCriacao;

}
