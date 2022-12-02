
insert or ignore into cozinha (id, nome) values (1, 'Tailandesa');
insert or ignore into cozinha (id, nome) values (2, 'Indiana');
insert or ignore into cozinha (id, nome) values (3, 'Brasileira');
insert or ignore into cozinha (id, nome) values (4, 'Argentina');

insert or ignore into estado (id, nome) values (1, 'Minas Gerais');
insert or ignore into estado (id, nome) values (2, 'São Paulo');
insert or ignore into estado (id, nome) values (3, 'Ceará');
--
insert or ignore into cidade (id, nome, estado_id) values (1, 'Uberlândia', 1);
insert or ignore into cidade (id, nome, estado_id) values (2, 'Belo Horizonte', 1);
insert or ignore into cidade (id, nome, estado_id) values (3, 'São Paulo', 2);
insert or ignore into cidade (id, nome, estado_id) values (4, 'Campinas', 2);
insert or ignore into cidade (id, nome, estado_id) values (5, 'Fortaleza', 3);
--
insert or ignore into restaurante (id, codigo, nome, taxa_frete, aberto, cozinha_id, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro,
data_cadastro, data_atualizacao, ativo) values  (7, 'dd766d5d-afc7-4f45-90b4-34ba1c9de8fa', 'Villa Welligton..', 10.00, 1, 1, 1, '38400-999', 'Rua João Pinheiro', '1000', 'Centro',  strftime("%Y-%m-%d %H:%M:%f", DATETIME('now')) , strftime("%Y-%m-%d %H:%M:%f", DATETIME('now')),1);
insert or ignore into restaurante (id, codigo, nome, taxa_frete, aberto, cozinha_id, data_cadastro, data_atualizacao, ativo) values (1, '98b7b4a2-70bf-47c4-b34a-b6f116234acf', 'Thai Gourmet', 10, 1, 1,            strftime("%Y-%m-%d %H:%M:%f", DATETIME('now')), strftime("%Y-%m-%d %H:%M:%f",DATETIME('now')),1);
insert or ignore into restaurante (id, codigo, nome, taxa_frete, aberto, cozinha_id, data_cadastro, data_atualizacao, ativo) values (2, '24b82713-c8d1-45a1-a18c-43fbe220997d', 'Thai Delivery', 9.50, 1, 1,         strftime("%Y-%m-%d %H:%M:%f", DATETIME('now')), strftime("%Y-%m-%d %H:%M:%f",DATETIME('now')),1);
insert or ignore into restaurante (id, codigo, nome, taxa_frete, aberto, cozinha_id, data_cadastro, data_atualizacao, ativo) values (3, 'ed5e7cc9-fd56-4e40-b945-54436b5ab2ea', 'Tuk Tuk Comida Indiana', 15, 0, 1,  strftime("%Y-%m-%d %H:%M:%f", DATETIME('now')), strftime("%Y-%m-%d %H:%M:%f",DATETIME('now')),1);
insert or ignore into restaurante (id, codigo, nome, taxa_frete, aberto, cozinha_id, data_cadastro, data_atualizacao, ativo) values (4, '5c569ae7-397d-41b6-909d-c2c4f1277028', 'Java Steakhouse', 12, 1, 3,         strftime("%Y-%m-%d %H:%M:%f", DATETIME('now')), strftime("%Y-%m-%d %H:%M:%f",DATETIME('now')),1);
insert or ignore into restaurante (id, codigo, nome, taxa_frete, aberto, cozinha_id, data_cadastro, data_atualizacao, ativo) values (5, '9aa94dcb-6d9e-4b21-8935-54396c039519', 'Lanchonete do Tio Sam', 11, 1, 4,   strftime("%Y-%m-%d %H:%M:%f", DATETIME('now')), strftime("%Y-%m-%d %H:%M:%f",DATETIME('now')),1);
insert or ignore into restaurante (id, codigo, nome, taxa_frete, aberto, cozinha_id, data_cadastro, data_atualizacao, ativo) values (6, 'f2c5f756-6d3a-4a0f-b246-d2d6f2f1b3a3', 'Bar da Maria', 6, 1, 4,             strftime("%Y-%m-%d %H:%M:%f", DATETIME('now')), strftime("%Y-%m-%d %H:%M:%f",DATETIME('now')),1);


insert or ignore into forma_pagamento (id, descricao) values (1, 'Cartão de crédito');
insert or ignore into forma_pagamento (id, descricao) values (2, 'Cartão de débito');
insert or ignore into forma_pagamento (id, descricao) values (3, 'Dinheiro');
--
insert or ignore into grupo (nome) values ('Gerente'), ('Vendedor'), ('Secretária'), ('Cadastrador');

insert or ignore into usuario (id, nome, email, senha, data_cadastro) values
(1, 'João da Silva', 'joao.ger@algafood.com', '123', strftime("%Y-%m-%d %H:%M:%f", DATETIME('now'))),
(2, 'Maria Joaquina', 'maria.vnd@algafood.com', '123', strftime("%Y-%m-%d %H:%M:%f", DATETIME('now'))),
(3, 'José Souza', 'jose.aux@algafood.com', '123', strftime("%Y-%m-%d %H:%M:%f", DATETIME('now'))),
(4, 'Sebastião Martins', 'sebastiao.cad@algafood.com', '123', strftime("%Y-%m-%d %H:%M:%f", DATETIME('now')));

insert or ignore into permissao (id, nome, descricao) values (1, 'CONSULTAR_COZINHAS', 'Permite consultar cozinhas');
insert or ignore into permissao (id, nome, descricao) values (2, 'EDITAR_COZINHAS', 'Permite editar cozinhas');

insert or ignore into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3), (4, 1), (4, 2), (5, 1), (5, 2), (6, 3);

insert or ignore into produto (id, nome, descricao, preco, ativo, restaurante_id) values (1, 'Porco com molho agridoce', 'Deliciosa carne suína ao molho especial', 78.90, 1, 1);
insert or ignore into produto (id, nome, descricao, preco, ativo, restaurante_id) values (2, 'Camarão tailandês', '16 camarões grandes ao molho picante', 110, 1, 1);
insert or ignore into produto (id, nome, descricao, preco, ativo, restaurante_id) values (3, 'Salada picante com carne grelhada', 'Salada de folhas com cortes finos de carne bovina grelhada e nosso molho especial de pimenta vermelha', 87.20, 1, 2);
insert or ignore into produto (id, nome, descricao, preco, ativo, restaurante_id) values (4, 'Garlic Naan', 'Pão tradicional indiano com cobertura de alho', 21, 1, 3);
insert or ignore into produto (id, nome, descricao, preco, ativo, restaurante_id) values (5, 'Murg Curry', 'Cubos de frango preparados com molho curry e especiarias', 43, 1, 3);
insert or ignore into produto (id, nome, descricao, preco, ativo, restaurante_id) values (6, 'Bife Ancho', 'Corte macio e suculento, com dois dedos de espessura, retirado da parte dianteira do contrafilé', 79, 1, 4);
insert or ignore into produto (id, nome, descricao, preco, ativo, restaurante_id) values (7, 'T-Bone', 'Corte muito saboroso, com um osso em formato de T, sendo de um lado o contrafilé e do outro o filé mignon', 89, 1, 4);
insert or ignore into produto (id, nome, descricao, preco, ativo, restaurante_id) values (8, 'Sanduíche X-Tudo', 'Sandubão com muito queijo, hamburger bovino, bacon, ovo, salada e maionese', 19, 1, 5);
insert or ignore into produto (id, nome, descricao, preco, ativo, restaurante_id) values (9, 'Espetinho de Cupim', 'Acompanha farinha, mandioca e vinagrete', 8, 1, 6);

insert or ignore into grupo_permissao (grupo_id, permissao_id) values (1, 1), (1, 2), (2, 1), (2, 2), (3, 1);

insert or ignore into usuario_grupo (usuario_id, grupo_id) values (1, 1), (1, 2), (2, 2);

insert or ignore into restaurante_usuario_responsavel (restaurante_id, usuario_id) values (1, 4), (3, 4);
--
insert or ignore into pedido (id, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep,
    endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro,
    status, data_criacao, subtotal, taxa_frete, valor_total)
values (1, 1, 1, 1, 1, '38400-000', 'Rua Floriano Peixoto', '500', 'Apto 801', 'Brasil',
'CRIADO', strftime("%Y-%m-%d %H:%M:%f", DATETIME('now')), 298.90, 10, 308.90);

insert or ignore into item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
values (1, 1, 1, 1, 78.9, 78.9, null);
--
insert or ignore into item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
values (2, 1, 2, 2, 110, 220, 'Menos picante, por favor');

insert or ignore into pedido (id, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep,
        endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro,
        status, data_criacao, subtotal, taxa_frete, valor_total)
values (2, 4, 1, 2, 1, '38400-111', 'Rua Acre', '300', 'Casa 2', 'Centro',
'CRIADO', strftime("%Y-%m-%d %H:%M:%f", DATETIME('now')), 79, 0, 79);

insert or ignore into item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
values (3, 2, 6, 1, 79, 79, 'Ao ponto');