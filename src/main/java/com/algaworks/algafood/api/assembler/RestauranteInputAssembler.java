package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.input.RestauranteInput;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestauranteInputAssembler {

    @Autowired
    ModelMapper modelMapper;

    public RestauranteInput toDomainInput (Restaurante restaurante) {
        return modelMapper.map(restaurante, RestauranteInput.class);
    }
}
