package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.ItemPedidoDTO;
import com.algaworks.algafood.domain.model.ItemPedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItemPedidoDTOAssemblber {

    @Autowired
    ModelMapper modelMapper;

    public ItemPedidoDTO toDTO (ItemPedido itemPedido) {
        return modelMapper.map(itemPedido, ItemPedidoDTO.class);
    }

    public List<ItemPedidoDTO> toCollectDTO (Collection<ItemPedido> itensPedido) {
        return itensPedido.stream()
                .map(itemPedido -> toDTO(itemPedido))
                .collect(Collectors.toList());
    }
}
