package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.CozinhaDTOAssembler;
import com.algaworks.algafood.api.assembler.CozinhaInputDisassembler;
import com.algaworks.algafood.api.model.CozinhaDTO;
import com.algaworks.algafood.api.model.input.CozinhaInput;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController   // substitui Controller e ResponseBody
@RequestMapping(value = "/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private CadastroCozinhaService cadastroCozinhaService;

    @Autowired
    CozinhaDTOAssembler cozinhaDTOAssembler;

    @Autowired
    CozinhaInputDisassembler cozinhaInputDisassembler;

    @GetMapping
    public List<CozinhaDTO> list() {
        List<Cozinha> cozinhas = cozinhaRepository.findAll();

        return cozinhaDTOAssembler.toCollectDTO(cozinhas);
    }

    @GetMapping ("/{id}")
    public CozinhaDTO  search(@PathVariable(name = "id") Long id) {
        Cozinha cozinha = cadastroCozinhaService.buscarOuFalhar(id);

        return cozinhaDTOAssembler.toDTO(cozinha);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaDTO add(@RequestBody @Valid CozinhaInput cozinhaInput) {
        Cozinha cozinha = cozinhaInputDisassembler.toDomainObject(cozinhaInput);

        return cozinhaDTOAssembler.toDTO(cadastroCozinhaService.save(cozinha));
    }

    @PutMapping("/{id}")
    //@ResponseStatus(HttpStatus.)
    public CozinhaDTO update(@PathVariable Long id, @RequestBody @Valid CozinhaInput cozinhaInput) {
        Cozinha cozinhaAtual = cadastroCozinhaService.buscarOuFalhar(id);

        cozinhaInputDisassembler.copyToDomainObject(cozinhaInput, cozinhaAtual);

        //BeanUtils.copyProperties(cozinha, cozinhaAtual, "id"); // copia todas as propriedades de cozinha para cozinhaAtual, exceto id

        return cozinhaDTOAssembler.toDTO(cadastroCozinhaService.save(cozinhaAtual));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)  // isso eh em caso de sucesso!!
    public void remove(@PathVariable Long id) {
        cadastroCozinhaService.remove(id);
    }

}
