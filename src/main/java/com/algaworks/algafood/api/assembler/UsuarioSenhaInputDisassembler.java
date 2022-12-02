package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.input.SenhaInput;
import com.algaworks.algafood.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioSenhaInputDisassembler {

    @Autowired
    ModelMapper modelMapper;

    public void copyToDomainObject (SenhaInput usuarioSenhaInput, Usuario usuario) {
        modelMapper.map(usuarioSenhaInput, usuario);
    }
}
