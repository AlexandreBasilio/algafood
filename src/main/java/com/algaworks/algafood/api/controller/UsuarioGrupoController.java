package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.GrupoDTOAssembler;
import com.algaworks.algafood.api.model.GrupoDTO;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios/{usuarioId}/grupos")
public class UsuarioGrupoController {

    @Autowired
    CadastroUsuarioService cadastroUsuarioService;

    @Autowired
    GrupoDTOAssembler grupoDTOAssembler;

    @GetMapping
    public List<GrupoDTO> listAll (@PathVariable Long usuarioId) {
        Usuario usuario = cadastroUsuarioService.searcrOrFail(usuarioId);

        return  grupoDTOAssembler.toCollectDTO(usuario.getGrupos());
    }

    @PutMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associarGrupo (@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        cadastroUsuarioService.associarGrupo(usuarioId, grupoId);
    }

    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociarGrupo (@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        cadastroUsuarioService.deassociarGrupo(usuarioId, grupoId);
    }
}
