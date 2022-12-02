CREATE TABLE cozinha (id  integer, nome varchar(60) not null, primary key (id));

CREATE TABLE estado (id  integer, nome varchar(80) not null, primary key (id));

CREATE TABLE cidade (id  integer, nome varchar(80) not null, estado_id integer not null, primary key (id),
                     FOREIGN KEY(estado_id) REFERENCES estado(id));

-- forma_pagamento definition

create table forma_pagamento (id integer not null, descricao varchar(60) not null,  primary key (id));

create table grupo (id integer not null, nome varchar(60) not null, primary key (id));

create table permissao (id integer not null, descricao varchar(60) not null, nome varchar(100) not null, primary key (id));

create table grupo_permissao (grupo_id integer not null, permissao_id integer not null, primary key (grupo_id, permissao_id),
                              foreign key (permissao_id) references permissao (id),
                              foreign key (grupo_id) references grupo (id));

create table produto (   id integer not null  ,
                         restaurante_id integer not null,
                         nome varchar(80) not null,
                         descricao text not null,
                         preco  numeric(10,2) not null,
                         ativo boolean(1) not null,
                         foreign key (restaurante_id) references restaurante (id),
                         primary key (id));

create table restaurante (   id integer not null  ,
                             cozinha_id integer not null,
                             nome varchar(80) not null,
                             aberto boolean not null,
                             ativo boolean not null,
                             taxa_frete  numeric(10,2) not null,
                             data_atualizacao datetime not null,
                             data_cadastro datetime not null,
                             endereco_cidade_id integer,
                             endereco_cep varchar(9),
                             endereco_logradouro varchar(100),
                             endereco_numero varchar(20),
                             endereco_complemento varchar(60),
                             endereco_bairro varchar(60),
                             foreign key (cozinha_id) references cozinha (id),
                             foreign key (endereco_cidade_id) references cidade (id),
                             primary key (id));

create table restaurante_forma_pagamento (   restaurante_id integer not null,
                                             forma_pagamento_id integer not null,
                                             foreign key (forma_pagamento_id) references forma_pagamento (id),
                                             foreign key (restaurante_id) references restaurante (id),
                                             primary key (restaurante_id, forma_pagamento_id));

-- CREATE TABLE restaurante_forma_pagamento (restaurante_id bigint not null, forma_pagamento_id bigint not null);

create table usuario (   id integer not null  ,
                         nome varchar(80) not null,
                         email varchar(255) not null,
                         senha varchar(255) not null,
                         data_cadastro datetime not null,
                         primary key (id));

create table usuario_grupo (   usuario_id integer not null,
                               grupo_id integer not null,
                               foreign key (grupo_id) references grupo (id),
                               foreign key (usuario_id) references usuario (id),
                               primary key (usuario_id, grupo_id));
-- CREATE TABLE usuario_grupo (usuario_id bigint not null, grupo_id bigint not null);