package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.PermissaoDTOAssembler;
import com.algaworks.algafood.api.model.PermissaoDTO;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.service.CadastroGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/grupos/{grupoId}/permissoes")
public class GrupoPermissaoController {


    @Autowired
    CadastroGrupoService cadastroGrupoService;

    @Autowired
    PermissaoDTOAssembler permissaoDTOAssembler;

    @GetMapping
    public List<PermissaoDTO> listAll (@PathVariable Long grupoId) {
        Grupo grupo = cadastroGrupoService.searchOrFail(grupoId);
        Set<Permissao> permissoes = grupo.getPermissoes();

        return permissaoDTOAssembler.toCollectDTO(permissoes);
    }

    @PutMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associarPermissao (@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        cadastroGrupoService.associarPermissao(grupoId, permissaoId);
    }

    @DeleteMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociarPermissao (@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        cadastroGrupoService.desassociarPermissao(grupoId, permissaoId);
    }
}
