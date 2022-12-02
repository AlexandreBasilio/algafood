package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.SenhaAtualInvalidaException;
import com.algaworks.algafood.domain.exception.UsuarioNaoEncontradoException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CadastroUsuarioService {

    public static final String MSG_USUARIO_USADO = "Usuario %s usado";

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    CadastroGrupoService cadastroGrupoService;

    public Usuario searcrOrFail (Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(()-> new UsuarioNaoEncontradoException(id));
    }

    @Transactional
    public Usuario save (Usuario usuario) {
        usuarioRepository.detach(usuario);

        Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());

        if (usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)) {
            throw new NegocioException(String.format("Ja existe usuario cadastrado com o email %s ",usuario.getEmail()));
        }

        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void remove (Long id) {
        searcrOrFail(id);
        try {
            usuarioRepository.deleteById(id);
            usuarioRepository.flush();

        } catch (JpaSystemException e) {
            throw new EntidadeEmUsoException(String.format(MSG_USUARIO_USADO, id));
        } catch (DataIntegrityViolationException e1) {
            throw new EntidadeEmUsoException(String.format(MSG_USUARIO_USADO, id));
        }
    }

    @Transactional
    public void updateSenha(Long id, String senhaAtual, String novaSenha) {
        Usuario usuario = searcrOrFail(id);

        if (usuario.senhaNaoCoincideCom(senhaAtual)) {
            throw new SenhaAtualInvalidaException("Senha atual informada invalida.");
        }

        usuario.setSenha(novaSenha);
    }

    @Transactional
    public void associarGrupo(Long usuarioId, Long grupoId) {
        Usuario usuario = searcrOrFail(usuarioId);
        Grupo grupo = cadastroGrupoService.searchOrFail(grupoId);

        usuario.adicionaGrupo(grupo);
    }

    @Transactional
    public void deassociarGrupo(Long usuarioId, Long grupoId) {
        Usuario usuario = searcrOrFail(usuarioId);
        Grupo grupo = cadastroGrupoService.searchOrFail(grupoId);

        usuario.removeGrupo(grupo);
    }
}
