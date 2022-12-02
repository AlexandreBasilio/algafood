package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.EstadoDTOAssembler;
import com.algaworks.algafood.api.assembler.EstadoInputDisassemble;
import com.algaworks.algafood.api.model.EstadoDTO;
import com.algaworks.algafood.api.model.input.EstadoInput;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.CadastroEstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/estados")
public class EstadoController {

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private CadastroEstadoService cadastroEstadoService;

    @Autowired
    EstadoDTOAssembler estadoDTOAssembler;

    @Autowired
    EstadoInputDisassemble estadoInputDisassemble;

    @GetMapping
    public List<EstadoDTO> list() {
        return estadoDTOAssembler.toCollectDTO(estadoRepository.findAll());
    }

    @GetMapping("/{id}")
    public EstadoDTO search (@PathVariable Long id) {
        Estado estado = cadastroEstadoService.buscarOuFalhar(id);

        return estadoDTOAssembler.toDTO(estado);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstadoDTO add (@RequestBody @Valid EstadoInput estadoInput) {
        Estado estado = estadoInputDisassemble.toDomainObject(estadoInput);
        return estadoDTOAssembler.toDTO(cadastroEstadoService.save(estado));
    }

    @PutMapping("/{id}")
    public EstadoDTO update (@PathVariable Long id, @RequestBody @Valid EstadoInput estadoInput) {
        Estado estadoAtual = cadastroEstadoService.buscarOuFalhar(id);
        estadoInputDisassemble.copyToDomainObject(estadoInput, estadoAtual);
        //BeanUtils.copyProperties(estado, estadoAtual, "id");

        return estadoDTOAssembler.toDTO(cadastroEstadoService.save(estadoAtual));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove (@PathVariable Long id) {
       cadastroEstadoService.remove(id);
    }

}
