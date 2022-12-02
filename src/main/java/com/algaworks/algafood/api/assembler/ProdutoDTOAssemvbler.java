package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.ProdutoDTO;
import com.algaworks.algafood.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProdutoDTOAssemvbler {

    @Autowired
    ModelMapper modelMapper;

    public ProdutoDTO toDTO(Produto produto) {
        return modelMapper.map(produto, ProdutoDTO.class);
    }

    public List<ProdutoDTO> toCollectDTO(List<Produto> produtos) {
        return produtos.stream()
                .map(produto -> toDTO(produto))
                .collect(Collectors.toList());
    }
}
