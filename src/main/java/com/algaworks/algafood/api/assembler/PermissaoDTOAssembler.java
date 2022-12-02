package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.PermissaoDTO;
import com.algaworks.algafood.domain.model.Permissao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PermissaoDTOAssembler {

    @Autowired
    ModelMapper modelMapper;

    public PermissaoDTO toDTO (Permissao permissao) {
        return modelMapper.map(permissao, PermissaoDTO.class);
    }

    public List<PermissaoDTO> toCollectDTO (Collection<Permissao> permissoes) {
        return permissoes.stream()
                .map(permissao -> toDTO(permissao))
                .collect(Collectors.toList());
    }
}
