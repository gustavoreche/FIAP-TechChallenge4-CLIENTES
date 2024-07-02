# language: pt

Funcionalidade: Teste de cadastro de cliente

  Cenário: Cadastra cliente com sucesso
    Dado que tenho dados validos de um cliente
    Quando cadastro esse cliente
    Entao recebo uma resposta que o cliente foi cadastrado com sucesso

  Cenário: Cadastra cliente já cadastrado
    Dado que tenho dados validos de um cliente que ja esta cadastrado
    Quando cadastro esse cliente
    Entao recebo uma resposta que o cliente ja esta cadastrado
