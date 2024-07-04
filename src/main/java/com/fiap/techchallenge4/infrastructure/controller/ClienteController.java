package com.fiap.techchallenge4.infrastructure.controller;

import com.fiap.techchallenge4.infrastructure.controller.dto.AtualizaClienteDTO;
import com.fiap.techchallenge4.infrastructure.controller.dto.ClienteDTO;
import com.fiap.techchallenge4.infrastructure.controller.dto.CriaClienteDTO;
import com.fiap.techchallenge4.useCase.ClienteUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

import static com.fiap.techchallenge4.infrastructure.controller.ClienteController.URL_CLIENTE;

@Tag(
		name = "Clientes",
		description = "Serviço para realizar o gerenciamento de clientes no sistema"
)
@RestController
@RequestMapping(URL_CLIENTE)
public class ClienteController {

	public static final String URL_CLIENTE = "/cliente";
	public static final String URL_CLIENTE_COM_CPF = URL_CLIENTE + "/{cpf}";

	private final ClienteUseCase service;

	public ClienteController(final ClienteUseCase service) {
		this.service = service;
	}

	@Operation(
			summary = "Serviço para cadastrar um cliente"
	)
	@PostMapping
	public ResponseEntity<Void> cadastra(@RequestBody @Valid final CriaClienteDTO dadosCliente) {
		final var cadastrou = this.service.cadastra(dadosCliente);
		if(cadastrou) {
			return ResponseEntity
					.status(HttpStatus.CREATED)
					.build();
		}
		return ResponseEntity
				.status(HttpStatus.CONFLICT)
				.build();
	}

	@Operation(
			summary = "Serviço para atualizar um cliente"
	)
	@PutMapping("/{cpf}")
	public ResponseEntity<Void> atualiza(@PathVariable("cpf") final String cpf,
										 @RequestBody @Valid final AtualizaClienteDTO dadosClientes) {
		final var atualizou = this.service.atualiza(cpf, dadosClientes);
		if(atualizou) {
			return ResponseEntity
					.status(HttpStatus.OK)
					.build();
		}
		return ResponseEntity
				.status(HttpStatus.NO_CONTENT)
				.build();
	}

	@Operation(
			summary = "Serviço para deletar um cliente"
	)
	@DeleteMapping("/{cpf}")
	public ResponseEntity<Void> deleta(@PathVariable("cpf") final String cpf) {
		final var deletou = this.service.deleta(cpf);
		if(deletou) {
			return ResponseEntity
					.status(HttpStatus.OK)
					.build();
		}
		return ResponseEntity
				.status(HttpStatus.NO_CONTENT)
				.build();
	}

	@Operation(
			summary = "Serviço para buscar um cliente"
	)
	@GetMapping("/{cpf}")
	public ResponseEntity<ClienteDTO> busca(@PathVariable("cpf") final String cpf) {
		final var cliente = this.service.busca(cpf);
		if(Objects.nonNull(cliente)) {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(cliente);
		}
		return ResponseEntity
				.status(HttpStatus.NO_CONTENT)
				.build();
	}

}
