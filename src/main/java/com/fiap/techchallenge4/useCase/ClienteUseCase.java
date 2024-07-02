package com.fiap.techchallenge4.useCase;

import com.fiap.techchallenge4.infrastructure.controller.dto.AtualizaClienteDTO;
import com.fiap.techchallenge4.infrastructure.controller.dto.ClienteDTO;
import com.fiap.techchallenge4.infrastructure.controller.dto.CriaClienteDTO;

public interface ClienteUseCase {

    boolean cadastra(final CriaClienteDTO cliente);

    boolean atualiza(final String cpf,
                     final AtualizaClienteDTO dadosClientes);

    boolean deleta(final String cpf);

    ClienteDTO busca(final String cpf);

}
