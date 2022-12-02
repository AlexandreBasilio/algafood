package com.algaworks.algafood.core.modelMapper;

import com.algaworks.algafood.api.model.EnderecoDTO;
import com.algaworks.algafood.api.model.RestauranteDTO;
import com.algaworks.algafood.api.model.input.ItemPedidoInput;
import com.algaworks.algafood.domain.model.Endereco;
import com.algaworks.algafood.domain.model.ItemPedido;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        TypeMap <Endereco, EnderecoDTO> enderecoToEnderecoDTOTypeMap = modelMapper.createTypeMap(
                Endereco.class, EnderecoDTO.class);

        enderecoToEnderecoDTOTypeMap.<String>addMapping(src -> src.getCidade().getEstado().getNome(),
                (dest, value) -> dest.getCidade().setEstado(value));

        // Customiza ModelMapper para atributo taxaFrete ir para PrecoFrete
        modelMapper.createTypeMap(Restaurante.class, RestauranteDTO.class)
                .addMapping(Restaurante::getTaxaFrete, RestauranteDTO::setPrecoFrete);

        modelMapper.createTypeMap(ItemPedidoInput.class, ItemPedido.class)
                .addMappings(mapper -> mapper.skip(ItemPedido::setId));

        return modelMapper;
    }
}
