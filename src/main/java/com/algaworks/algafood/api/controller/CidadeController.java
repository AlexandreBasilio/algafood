package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.CidadeInputDisassembler;
import com.algaworks.algafood.api.model.CidadeDTO;
import com.algaworks.algafood.api.assembler.CidadeDTOAssemvbler;
import com.algaworks.algafood.api.model.input.CidadeInput;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    CidadeRepository cidadeRepository;

    @Autowired
    CadastroCidadeServico cadastroCidadeServico;

    @Autowired
    CidadeDTOAssemvbler cidadeDTOAssemvbler;

    @Autowired
    CidadeInputDisassembler cidadeInputDisassembler;

    @GetMapping
    public List<CidadeDTO> list() {
        List<Cidade> cidades = cidadeRepository.findAll();

        return cidadeDTOAssemvbler.toCollectDTO(cidades);
    }

    @GetMapping("{id}")
    public CidadeDTO search (@PathVariable Long id) {
        Cidade cidade = cadastroCidadeServico.buscarOuFalhar(id);

        return cidadeDTOAssemvbler.toDTO(cidade);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeDTO add (@RequestBody @Valid CidadeInput cidadeInput) {
       try {
           Cidade cidade = cidadeInputDisassembler.toDomainobject(cidadeInput);

           return cidadeDTOAssemvbler.toDTO(cadastroCidadeServico.save(cidade));
       } catch (EstadoNaoEncontradoException e) {
           throw new NegocioException(e.getMessage());
       }
    }

    @PutMapping("/{id}")
    public CidadeDTO update (@PathVariable Long id, @RequestBody @Valid CidadeInput cidadeInput) {
         try {
             Cidade cidadeAtual = cadastroCidadeServico.buscarOuFalhar(id);
             //BeanUtils.copyProperties(cidade, cidadeAtual, "id");
             cidadeInputDisassembler.copyToDomainObject(cidadeInput, cidadeAtual);

             return cidadeDTOAssemvbler.toDTO(cadastroCidadeServico.save(cidadeAtual));
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e); // quando vc passa a causa..
        }

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove (@PathVariable Long id) {
        cadastroCidadeServico.remove(id);
    }

}
