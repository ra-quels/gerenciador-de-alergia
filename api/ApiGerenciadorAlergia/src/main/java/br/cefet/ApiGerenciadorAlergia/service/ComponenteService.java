package br.cefet.ApiGerenciadorAlergia.service;

import java.util.List;

import org.jdbi.v3.core.Jdbi;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import br.cefet.ApiGerenciadorAlergia.dao.ComponenteDao;
import br.cefet.ApiGerenciadorAlergia.model.Componente;

public class ComponenteService {
	private final ComponenteDao componenteDao;
	
	public ComponenteService(Jdbi jdbi) {
        this.componenteDao = jdbi.onDemand(ComponenteDao.class);
	}
	
        public Componente inserir(Componente c) {
            if (c.getComponente_id() != 0) {
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Id - informacao ilegal.");
            }
            
            Long componente_id = componenteDao.inserir(c);
            c.setComponente_id(componente_id);
            return c;   
        }
        
        public List<Componente> consultar() {
            return componenteDao.consultar();
        }
        
        public Componente consultarPorId(Long componente_id) {
            return componenteDao.consultarPorId(componente_id);
        }
        
        public Componente alterar(Componente c) {
            Long componente_id = c.getComponente_id();
            if (componente_id == null || componente_id <= 0) {
                throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Id é informação obrigatória.");
            }

            Componente componenteExistente = componenteDao.consultarPorId(componente_id);
            if (componenteExistente == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado com o ID: " + componente_id + ".");
            }

            int quantidadeAlterada = componenteDao.alterar(c);
            if (quantidadeAlterada != 1) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Quantidade de entidades alteradas é " + quantidadeAlterada + ".");
            }

            return componenteDao.consultarPorId(componente_id);
        }        
        
        public Componente excluir(Long componente_id) {
        	Componente componenteAux = componenteDao.consultarPorId(componente_id);
            if (componenteAux == null){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto nao encontrado com o id: " + componente_id+ ".");
            }
            
            int qtd = componenteDao.excluir(componente_id);
            if (qtd != 1){
                throw new ResponseStatusException(HttpStatus.CONFLICT, "A quantidade de entidades alteradas eh " +qtd+ ".");
            }
            
            return componenteAux;
        }
            
        public boolean nomeExiste(String nome) {
            return componenteDao.nomeExiste(nome);
        }
        

    

}
