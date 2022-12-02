package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.input.UsuarioComSenhaInput;
import com.algaworks.algafood.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioInputDisassembler {

    @Autowired
    ModelMapper modelMapper;

    public Usuario toDomainObjet (UsuarioComSenhaInput usuarioComSenhaInput) {
        return modelMapper.map(usuarioComSenhaInput, Usuario.class);
    }

    public void copyToDomainObject (UsuarioComSenhaInput usuarioComSenhaInput, Usuario usuario) {
        modelMapper.map(usuarioComSenhaInput, usuario);
    }
}
