package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.input.UsuarioInput;
import com.algaworks.algafood.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioSemSenhaInputDisassembler {

    @Autowired
    ModelMapper modelMapper;

    public void copyToDomainObject (UsuarioInput usuarioSemSenhaInput, Usuario usuario) {
        modelMapper.map(usuarioSemSenhaInput, usuario);
    }
}
