package com.fiap.techchallenge4.useCase.impl;

import com.fiap.techchallenge4.domain.Cliente;
import com.fiap.techchallenge4.domain.Cpf;
import com.fiap.techchallenge4.infrastructure.controller.dto.AtualizaClienteDTO;
import com.fiap.techchallenge4.infrastructure.controller.dto.ClienteDTO;
import com.fiap.techchallenge4.infrastructure.controller.dto.CriaClienteDTO;
import com.fiap.techchallenge4.infrastructure.model.ClienteEntity;
import com.fiap.techchallenge4.infrastructure.repository.ClienteRepository;
import com.fiap.techchallenge4.useCase.ClienteUseCase;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ClienteUseCaseImpl implements ClienteUseCase {

    private final ClienteRepository repository;

    public ClienteUseCaseImpl(final ClienteRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean cadastra(final CriaClienteDTO dadosCliente) {
        final var cliente = new Cliente(
                dadosCliente.cpf(),
                dadosCliente.nome(),
                dadosCliente.enderecoLogradouro(),
                dadosCliente.enderecoNumero(),
                dadosCliente.enderecoSiglaEstado()
        );

        final var clienteNaBase = this.repository.findById(cliente.getCpf());
        if(clienteNaBase.isEmpty()) {
            var clienteEntity = new ClienteEntity(
                    cliente.getCpf(),
                    cliente.getNome(),
                    cliente.getEnderecoLogradouro(),
                    cliente.getEnderecoNumero(),
                    cliente.getEnderecoSiglaEstado(),
                    LocalDateTime.now()
            );

            this.repository.save(clienteEntity);
            return true;
        }
        System.out.println("Cliente já cadastrado");
        return false;

    }

    @Override
    public boolean atualiza(final String cpf,
                            final AtualizaClienteDTO dadosClientes) {
        final var cliente = new Cliente(
                cpf,
                dadosClientes.nome(),
                dadosClientes.enderecoLogradouro(),
                dadosClientes.enderecoNumero(),
                dadosClientes.enderecoSiglaEstado()
        );

        final var clienteNaBase = this.repository.findById(cpf);
        if(clienteNaBase.isEmpty()) {
            System.out.println("Cliente não está cadastrado");
            return false;
        }

        var clienteEntity = new ClienteEntity(
                cliente.getCpf(),
                cliente.getNome(),
                cliente.getEnderecoLogradouro(),
                cliente.getEnderecoNumero(),
                cliente.getEnderecoSiglaEstado(),
                LocalDateTime.now()
        );

        this.repository.save(clienteEntity);
        return true;

    }

    @Override
    public boolean deleta(final String cpf) {
        final var cpfObjeto = new Cpf(cpf);

        final var clienteNaBase = this.repository.findById(cpfObjeto.getNumero());
        if(clienteNaBase.isEmpty()) {
            System.out.println("Cliente não está cadastrado");
            return false;
        }
        this.repository.deleteById(cpfObjeto.getNumero());
        return true;

    }

    @Override
    public ClienteDTO busca(final String cpf) {
        final var cpfObjeto = new Cpf(cpf);

        final var clienteNaBase = this.repository.findById(cpfObjeto.getNumero());
        if(clienteNaBase.isEmpty()) {
            System.out.println("Cliente não está cadastrado");
            return null;
        }
        final var clienteEntity = clienteNaBase.get();

        return new ClienteDTO(
                clienteEntity.getCpf(),
                clienteEntity.getNome(),
                clienteEntity.getEnderecoLogradouro(),
                clienteEntity.getEnderecoNumero(),
                clienteEntity.getEnderecoSiglaEstado(),
                clienteEntity.getDataDeCriacao()
        );

    }

}
