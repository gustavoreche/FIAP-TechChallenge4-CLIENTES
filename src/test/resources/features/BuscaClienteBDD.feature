# language: pt

Funcionalidade: Teste de buscar o cliente

  Cenário: Busca cliente com sucesso
    Dado que busco um cliente que ja esta cadastrado
    Quando busco esse cliente
    Entao recebo uma resposta que o cliente foi encontrado com sucesso

  Cenário: Busca cliente não cadastrado
    Dado que busco um cliente nao cadastrado
    Quando busco esse cliente
    Entao recebo uma resposta que o cliente nao foi encontrado
