package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.UsuarioDTOAssembler;
import com.algaworks.algafood.api.assembler.UsuarioInputDisassembler;
import com.algaworks.algafood.api.assembler.UsuarioSemSenhaInputDisassembler;
import com.algaworks.algafood.api.assembler.UsuarioSenhaInputDisassembler;
import com.algaworks.algafood.api.model.UsuarioDTO;
import com.algaworks.algafood.api.model.input.UsuarioComSenhaInput;
import com.algaworks.algafood.api.model.input.UsuarioInput;
import com.algaworks.algafood.api.model.input.SenhaInput;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    CadastroUsuarioService cadastroUsuarioService;

    @Autowired
    UsuarioDTOAssembler usuarioDTOAssembler;

    @Autowired
    UsuarioInputDisassembler usuarioInputDisassembler;

    @Autowired
    UsuarioSemSenhaInputDisassembler usuarioSemSenhaInputDisassembler;

    @Autowired
    UsuarioSenhaInputDisassembler usuarioSenhaInputDisassembler;

    @GetMapping
    public List<UsuarioDTO> listAll () {
        return  usuarioDTOAssembler.toCollectDTO(usuarioRepository.findAll());
    }

    @GetMapping("/{id}")
    public UsuarioDTO search (@PathVariable Long id) {
        Usuario usuario = cadastroUsuarioService.searcrOrFail(id);

        return usuarioDTOAssembler.toDTO(usuario);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioDTO add (@RequestBody @Valid UsuarioComSenhaInput usuarioComSenhaInput) {
        Usuario usuario = usuarioInputDisassembler.toDomainObjet(usuarioComSenhaInput);

        return usuarioDTOAssembler.toDTO(cadastroUsuarioService.save(usuario));
    }

    @PutMapping("/{id}")
    public UsuarioDTO update (@PathVariable Long id, @RequestBody @Valid UsuarioInput usuarioInput) {
        Usuario usuario = cadastroUsuarioService.searcrOrFail(id); // usuario  ja estah sendo gerenciado pelo metodo de persistencai.
        // quando a gente busca o usuario, esse usaurio fica no contexto de persitencia do JPA. Area de memmoria onde os objetos que a gente trabalha (find, persitinnto)
        // ele gerencia esss objetos e qualquer mudanca neles eh sincronizado com o banco de dados
        usuarioSemSenhaInputDisassembler.copyToDomainObject(usuarioInput, usuario); // aqui o objeto eh alterado e deve ser sincronizado no transational do service

        return usuarioDTOAssembler.toDTO(cadastroUsuarioService.save(usuario));
    }


    @PutMapping("/{id}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateSenha (@PathVariable Long id, @RequestBody @Valid SenhaInput senhaInput) {
        cadastroUsuarioService.updateSenha(id, senhaInput.getSenhaAtual(), senhaInput.getNovaSenha());
    }

}
