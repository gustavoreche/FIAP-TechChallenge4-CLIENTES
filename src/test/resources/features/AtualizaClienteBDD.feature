# language: pt

Funcionalidade: Teste de atualização de cliente

  Cenário: Atualiza cliente com sucesso
    Dado que tenho os dados validos de um cliente que ja esta cadastrado
    Quando atualizo esse cliente
    Entao recebo uma resposta que o cliente foi atualizado com sucesso

  Cenário: Atualiza cliente não cadastrado
    Dado que tenho os dados validos de um cliente
    Quando atualizo esse cliente
    Entao recebo uma resposta que o cliente nao esta cadastrado
