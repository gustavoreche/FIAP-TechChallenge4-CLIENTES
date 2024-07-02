package com.fiap.techchallenge4.infrastructure.repository;

import com.fiap.techchallenge4.infrastructure.model.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<ClienteEntity, String> {
}
