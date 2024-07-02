# language: pt

Funcionalidade: Teste de deletar o cliente

  Cenário: Deleta cliente com sucesso
    Dado que informo um cliente que ja esta cadastrado
    Quando deleto esse cliente
    Entao recebo uma resposta que o cliente foi deletado com sucesso

  Cenário: Deleta cliente não cadastrado
    Dado que informo um cliente nao cadastrado
    Quando deleto esse cliente
    Entao recebo uma resposta que o cliente nao foi cadastrado
