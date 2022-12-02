package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.input.CidadeInput;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CidadeInputDisassembler {

    @Autowired
    ModelMapper modelMapper;

    public Cidade toDomainobject(CidadeInput cidadeInput) {
       return modelMapper.map(cidadeInput, Cidade.class);
    }

    public void copyToDomainObject(CidadeInput cidadeInput, Cidade cidadeAtual) {
        cidadeAtual.setEstado((new Estado()));

        modelMapper.map(cidadeInput, cidadeAtual);
    }
}
