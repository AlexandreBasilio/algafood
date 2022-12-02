package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.GrupoDTOAssembler;
import com.algaworks.algafood.api.assembler.GrupoInputDisassembler;
import com.algaworks.algafood.api.model.GrupoDTO;
import com.algaworks.algafood.api.model.input.GrupoInput;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.repository.GrupoRepository;
import com.algaworks.algafood.domain.service.CadastroGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/grupos")
public class GrupoController {

    @Autowired
    GrupoRepository grupoRepository;

    @Autowired
    CadastroGrupoService cadastroGrupoService;

    @Autowired
    GrupoDTOAssembler grupoDTOAssembler;

    @Autowired
    GrupoInputDisassembler grupoInputDisassembler;

    @GetMapping
    public List<GrupoDTO> findAll () {
        return grupoDTOAssembler.toCollectDTO(grupoRepository.findAll());
    }

    @GetMapping("/{id}")
    public GrupoDTO find (@PathVariable Long id) {
        Grupo grupo = cadastroGrupoService.searchOrFail(id);
        return grupoDTOAssembler.toDto(grupo);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoDTO add (@RequestBody @Valid GrupoInput grupoInput) {
        Grupo grupo = grupoInputDisassembler.toDomainModel(grupoInput);

        return grupoDTOAssembler.toDto(cadastroGrupoService.save(grupo));
    }

    @PutMapping("/{id}")
    public GrupoDTO update (@PathVariable Long id, @RequestBody @Valid GrupoInput grupoInput) {
        Grupo grupo = cadastroGrupoService.searchOrFail(id);
        grupoInputDisassembler.copyToDomainModel(grupoInput, grupo);

        return grupoDTOAssembler.toDto(cadastroGrupoService.save(grupo));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove (@PathVariable Long id) {
        cadastroGrupoService.remove(id);
    }

}
