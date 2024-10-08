package br.cefet.ApiGerenciadorAlergia.service;

import br.cefet.ApiGerenciadorAlergia.dao.UsuarioDao;
import br.cefet.ApiGerenciadorAlergia.model.Usuario;

import java.util.List;
import org.jdbi.v3.core.Jdbi;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UsuarioService {
	private final UsuarioDao usuarioDao;
	

    public UsuarioService(Jdbi jdbi) {
        this.usuarioDao = jdbi.onDemand(UsuarioDao.class);
    }
    
    public Usuario inserir(Usuario u) {
        if (u.getUsuario_id() != 0) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Id - informacao ilegal.");
        }
        
        Long id = usuarioDao.inserir(u);
        u.setUsuario_id(id);
        return u;   
    }
    
    public List<Usuario> consultar() {
        return usuarioDao.consultar();
    }
    
    public Usuario consultarPorId(Long id) {
        return usuarioDao.consultarPorId(id);
    }
    
    public Usuario alterar(Usuario u) {
        Long id = u.getUsuario_id();
        if (id == null || id <= 0) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Id é informação obrigatória.");
        }

        Usuario usuarioExistente = usuarioDao.consultarPorId(id);
        if (usuarioExistente == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado com o ID: " + id + ".");
        }

        int quantidadeAlterada = usuarioDao.alterar(u);
        if (quantidadeAlterada != 1) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Quantidade de entidades alteradas é " + quantidadeAlterada + ".");
        }

        return usuarioDao.consultarPorId(id);
    }

    public Usuario excluir(Long id) {
        Usuario usuarioAux = usuarioDao.consultarPorId(id);
        if (usuarioAux == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto nao encontrado com o id: " +id+ ".");
        }
        
        int qtd = usuarioDao.excluir(id);
        if (qtd != 1){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "A quantidade de entidades alteradas eh " +qtd+ ".");
        }
        
        return usuarioAux;
    }
        
    public boolean emailExists(String email) {
        return usuarioDao.emailExists(email);
    }
    


}
