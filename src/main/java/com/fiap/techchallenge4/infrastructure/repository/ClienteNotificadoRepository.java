package com.fiap.techchallenge4.infrastructure.repository;

import com.fiap.techchallenge4.infrastructure.model.ClienteNotificadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteNotificadoRepository extends JpaRepository<ClienteNotificadoEntity, Long> {
}
