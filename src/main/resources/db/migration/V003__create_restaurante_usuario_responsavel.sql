create table restaurante_usuario_responsavel ( restaurante_id integer not null,
                                             usuario_id integer not null,
                                             foreign key (usuario_id) references usuario (id),
                                             foreign key (restaurante_id) references restaurante (id),
                                             primary key (restaurante_id, usuario_id));
