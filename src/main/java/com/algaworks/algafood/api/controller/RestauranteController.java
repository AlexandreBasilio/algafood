package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.RestauranteDTOAssembler;
import com.algaworks.algafood.api.assembler.RestauranteInputAssembler;
import com.algaworks.algafood.api.assembler.RestauranteInputDisassembler;
import com.algaworks.algafood.api.model.RestauranteDTO;
import com.algaworks.algafood.api.model.input.RestauranteInput;
import com.algaworks.algafood.core.validation.ValidacaoException;
import com.algaworks.algafood.domain.exception.*;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @Autowired
    private SmartValidator validator;

    @Autowired
    private RestauranteDTOAssembler restauranteDTOAssembler;

    @Autowired
    private RestauranteInputAssembler restauranteInputAssembler;

    @Autowired
    private RestauranteInputDisassembler restauranteInputDisassembler;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping
    public List<RestauranteDTO> list() {
        return restauranteDTOAssembler.toCollectionDTO(restauranteRepository.findAll());
    }

    @GetMapping("/{id}")
    public RestauranteDTO search(@PathVariable Long id) {
        Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(id);

        return restauranteDTOAssembler.toDTO(restaurante);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteDTO add (@RequestBody @Valid RestauranteInput restauranteInput) {
        try {
            Restaurante restaurante = restauranteInputDisassembler.toDomainObject(restauranteInput);
            return restauranteDTOAssembler.toDTO(cadastroRestauranteService.save(restaurante));
        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }


    @PutMapping("/{id}")
    public RestauranteDTO update (@PathVariable Long id, @RequestBody @Valid RestauranteInput restauranteInput) {

       // Restaurante restaurante = restauranteInputDisassembler.toDomainObject(restauranteInput);
        Restaurante restauranteAtual = cadastroRestauranteService.buscarOuFalhar(id);
        restauranteInputDisassembler.copyToDomainObject(restauranteInput, restauranteAtual);

        //BeanUtils.copyProperties(restaurante, restauranteAtual,
        //        "id", "formasPagamento", "endereco", "dataCadastro"); // copia todas as propriedades de cozinha para cozinhaAtual, exceto id

        try {
            return restauranteDTOAssembler.toDTO(cadastroRestauranteService.save(restauranteAtual));
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public RestauranteDTO updatePartial (@PathVariable Long id, @RequestBody Map<String, Object> campos, HttpServletRequest request) {
        Restaurante restauranteAtual = cadastroRestauranteService.buscarOuFalhar(id);

        merge(campos, restauranteAtual, request);
        // precisa fazer uma validacao com programa com a API do Spring. Validar o restauranteAtual
        validate(restauranteAtual, "restaurante");

        return update(id, restauranteInputAssembler.toDomainInput(restauranteAtual));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove (@PathVariable Long id) {
        cadastroRestauranteService.remove(id);
    }

    @PutMapping("/{id}/ativo")   // melhor que POST pois o PUT eh idemPotente
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativar (@PathVariable Long id) {
        cadastroRestauranteService.ativar(id);
    }

    @PutMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativarMultiplos (@RequestBody List<Long> restauranteIds) {
        try {
            cadastroRestauranteService.ativar(restauranteIds);
        } catch (RestauranteNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{id}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativar (@PathVariable Long id) {
        cadastroRestauranteService.inativar(id);
    }

    @PutMapping("/inativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativarMultiplos (@RequestBody List<Long> restauranteIds) {
        try {
            cadastroRestauranteService.inativar(restauranteIds);
        } catch (RestauranteNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("/{restauranteId}/abertura")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void abre (@PathVariable Long restauranteId) {
        cadastroRestauranteService.abre(restauranteId);
    }

    @PutMapping("/{restauranteId}/fechamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void fecha (@PathVariable Long restauranteId) {
        cadastroRestauranteService.fecha(restauranteId);
    }

    private void merge(@RequestBody Map<String, Object> campos, Restaurante restaurante,  HttpServletRequest request) {
        ServletServerHttpRequest servletServerHttpRequest = new ServletServerHttpRequest(request);

        try {
            System.out.println(" restaurante=" + restaurante);
            //converte java em json, json em java
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
            //Convertendo MAP em uma classe (instancia de restaurante)
            Restaurante restauranteOrigem = objectMapper.convertValue(campos, Restaurante.class);
            System.out.println(" restauranteOrigem=" + restauranteOrigem);

            // Map campos a mudar
            for (Map.Entry<String, Object> entry: campos.entrySet()) {
                System.out.println(entry.getKey() + " value=" + entry.getValue());
                // busca no objeto Restaurante, o campo.. e coloca em Field
                Field field = ReflectionUtils.findField(Restaurante.class, entry.getKey());
                System.out.println(" field=" + field);
                // se o campo eh private, torna ele acessivel
                field.setAccessible(true);

                // com p field em maos busca no objeto seu valor
                Object valor = ReflectionUtils.getField(field, restauranteOrigem);
                System.out.println(" valor=" + valor);

                // ccom o valor em maos,
                ReflectionUtils.setField(field, restaurante, valor);
            }
        } catch (IllegalArgumentException e) {
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            throw  new HttpMessageNotReadableException(e.getMessage(), rootCause, servletServerHttpRequest);
        }
    }


    private void validate(Restaurante restaurante, String objName) {
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurante, objName);
        validator.validate(restaurante, bindingResult);

        if (bindingResult.hasErrors()) {
            // throw new MethodArgumentNotValidException .. complicando pois tem alguns parametros encadeados que tem que ser enviados
            // entao vamos criar uma nova exception
            throw new ValidacaoException(bindingResult);

        }
    }
}
