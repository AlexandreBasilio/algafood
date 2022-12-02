create table pedido (
                        id  integer,
                        codigo varchar(36),
                        data_cancelemento datetime,
                        data_confirmacao datetime,
                        data_criacao datetime not null,
                        data_entrega datetime,
                        endereco_bairro varchar(60),
                        endereco_cep varchar(9),
                        endereco_complemento varchar(60),
                        endereco_logradouro varchar(100),
                        endereco_numero varchar(20),
                        status varchar(10),
                        subtotal numeric(10,2) not null,
                        taxa_frete numeric(10,2) not null,
                        valor_total numeric(10,2) not null,
                        usuario_cliente_id bigint not null,
                        endereco_cidade_id bigint,
                        forma_pagamento_id bigint not null,
                        restaurante_id bigint not null,
                        foreign key (usuario_cliente_id) references usuario (id),
                        foreign key (endereco_cidade_id) references cidade (id),
                        foreign key (forma_pagamento_id) references forma_pagamento (id),
                        CONSTRAINT uk_pedido_codigo UNIQUE (codigo),
                        primary key (id));

create table item_pedido (
    id  integer,
    observacao varchar(255),
    preco_total numeric(10,2) not null,
    preco_unitario numeric(10,2) not null,
    quantidade integer not null,
    pedido_id bigint not null,
    produto_id bigint not null,
    foreign key (pedido_id) references pedido (id),
    foreign key (produto_id) references produto (id),
    primary key (id));


CREATE TABLE UUID_TABLE(
   id varchar(500),
   name varchar(500) NOT NULL,
   CONSTRAINT name_unique UNIQUE (name),
   CONSTRAINT rid_pkey PRIMARY KEY (id)
);
