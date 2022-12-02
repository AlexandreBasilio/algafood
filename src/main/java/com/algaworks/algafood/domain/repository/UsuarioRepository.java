package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends CustomJpaRespository<Usuario, Long> {

    Optional<Usuario> findByEmail (String email);

}
