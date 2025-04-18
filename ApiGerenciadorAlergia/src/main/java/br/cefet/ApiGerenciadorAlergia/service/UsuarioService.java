package br.cefet.ApiGerenciadorAlergia.service;

import java.util.List;
import org.jdbi.v3.core.Jdbi;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.cefet.ApiGerenciadorAlergia.dao.UsuarioDao;
import br.cefet.ApiGerenciadorAlergia.model.Usuario;

@Service
public class UsuarioService {
	private final UsuarioDao usuarioDao;
	

	private Usuario usuarioAutenticado;
    public UsuarioService(Jdbi jdbi) {
        this.usuarioDao = jdbi.onDemand(UsuarioDao.class);

    }
    
    
    public Usuario inserir(Usuario u) {
        if (u.getUsuario_id() != 0) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Id - informacao ilegal.");
        }
        
        Long usuario_id = usuarioDao.inserir(u);
        u.setUsuario_id(usuario_id);
        return u;   
    }
    
    public List<Usuario> consultar() {
        return usuarioDao.consultar();
    }
    public Usuario getUsuarioPorEmail(String email) {
        return usuarioDao.getUsuarioPorEmail(email);
    }
    public Usuario consultarPorId(Long usuario_id) {
        return usuarioDao.consultarPorId(usuario_id);
    }
    
    public Usuario alterar(Usuario u) {
        Long usuario_id = u.getUsuario_id();
        if (usuario_id == null || usuario_id <= 0) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Id é informação obrigatória.");
        }

        Usuario usuarioExistente = usuarioDao.consultarPorId(usuario_id);
        if (usuarioExistente == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado com o ID: " + usuario_id + ".");
        }

        int quantidadeAlterada = usuarioDao.alterar(u);
        if (quantidadeAlterada != 1) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Quantidade de entidades alteradas é " + quantidadeAlterada + ".");
        }

        return usuarioDao.consultarPorId(usuario_id);
    }

    public Usuario excluir(Long usuario_id) {
        Usuario usuarioAux = usuarioDao.consultarPorId(usuario_id);
        if (usuarioAux == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto nao encontrado com o id: " +usuario_id+ ".");
        }
        
        int qtd = usuarioDao.excluir(usuario_id);
        if (qtd != 1){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "A quantidade de entidades alteradas eh " +qtd+ ".");
        }
        
        return usuarioAux;
    }

    public Usuario autenticar(String email, String senha) {
        Usuario usuario = usuarioDao.consultarPorEmailESenha(email, senha);

        

        return usuario;
    }

    public Usuario verificarLogin(String email) {
        Usuario usuario = usuarioDao.consultarPorEmail(email);

        if (usuario == null) {
        	return usuario;
        }

        return usuario;
    }

    public void registrarAutenticacao(Usuario usuario) {
        if (usuario == null) {
        	this.usuarioAutenticado = usuario;
        }
        this.usuarioAutenticado = usuario;
    }

    public Usuario recuperarAutenticacao() {
        if (this.usuarioAutenticado == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Nenhum usuário autenticado.");
        }

        return this.usuarioAutenticado;
    }

    public void encerrarAutenticacao() {
        this.usuarioAutenticado = null;
    }


}
