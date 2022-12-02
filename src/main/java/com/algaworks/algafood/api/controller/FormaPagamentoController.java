package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.FormaPagamentoDTOAssembler;
import com.algaworks.algafood.api.assembler.FormaPagamentoInputDisassembler;
import com.algaworks.algafood.api.model.FormaPagamentoDTO;
import com.algaworks.algafood.api.model.input.FormaPagamentoInput;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import com.algaworks.algafood.domain.service.FormaPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/formasPagamento")
public class FormaPagamentoController {

    @Autowired
    FormaPagamentoRepository formaPagamentoRepository;

    @Autowired
    FormaPagamentoService formaPagamentoService;

    @Autowired
    FormaPagamentoDTOAssembler formaPagamentoDTOAssembler;

    @Autowired
    FormaPagamentoInputDisassembler formaPagamentoInputDisassembler;

    @GetMapping
    public List<FormaPagamentoDTO> findAll ()  {
        List<FormaPagamento> formasPagamento = formaPagamentoRepository.findAll();

        return formaPagamentoDTOAssembler.toCollectDTO(formasPagamento);
    }

    @GetMapping("/{id}")
    public FormaPagamentoDTO find(@PathVariable Long id) {
        FormaPagamento formaPagamento = formaPagamentoService.searchOuFail(id);

        return formaPagamentoDTOAssembler.toDTO(formaPagamento);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamentoDTO save (@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
        FormaPagamento formaPagamento = formaPagamentoInputDisassembler.toDomainObject(formaPagamentoInput);

        return formaPagamentoDTOAssembler.toDTO(formaPagamentoService.save(formaPagamento));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove (@PathVariable Long id) {
        formaPagamentoService.remove(id);
    }

    @PutMapping("/{id}")
    public FormaPagamentoDTO update (@PathVariable Long id, @RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
        FormaPagamento formaPagamento = formaPagamentoService.searchOuFail(id);

        formaPagamentoInputDisassembler.copyToDomainObject(formaPagamentoInput, formaPagamento);

        return formaPagamentoDTOAssembler.toDTO(formaPagamentoService.save(formaPagamento));
    }
}
