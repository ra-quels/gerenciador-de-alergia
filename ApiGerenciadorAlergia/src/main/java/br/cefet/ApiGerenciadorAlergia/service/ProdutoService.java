package br.cefet.ApiGerenciadorAlergia.service;

import java.util.List;

import org.jdbi.v3.core.Jdbi;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.cefet.ApiGerenciadorAlergia.dao.ProdutoDao;
import br.cefet.ApiGerenciadorAlergia.model.Componente;
import br.cefet.ApiGerenciadorAlergia.model.Produto;

@Service
public class ProdutoService {
	private final ProdutoDao produtoDao;
	
	public ProdutoService(Jdbi jdbi) {
        this.produtoDao = jdbi.onDemand(ProdutoDao.class);
	}
	
        public Produto inserir(Produto p) {
            if (p.getProduto_id() != 0) {
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Id - informacao ilegal.");
            }
            
            Long produto_id = produtoDao.inserir(p);
            p.setProduto_id(produto_id);
            
            // Associar os componentes ao produto
            if (p.getComponentes() != null) {
                for (Long componente_id : p.getComponentes()) {
                    produtoDao.associarComponente(produto_id, componente_id);
                }
            }
            
            return p;   
        }
        
        public List<Produto> consultar() {
            return produtoDao.consultar();
        }
        
        public Produto consultarPorId(Long produto_id) {
            return produtoDao.consultarPorId(produto_id);
        }
        
        public Produto alterar(Produto p) {
            Long produto_id = p.getProduto_id();
            if (produto_id == null || produto_id <= 0) {
                throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Id é informação obrigatória.");
            }

            Produto produtoExistente = produtoDao.consultarPorId(produto_id);
            if (produtoExistente == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado com o ID: " + produto_id + ".");
            }

            int quantidadeAlterada = produtoDao.alterar(p);
            if (quantidadeAlterada != 1) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Quantidade de entidades alteradas é " + quantidadeAlterada + ".");
            }

            return produtoDao.consultarPorId(produto_id);
        }        
        
        public Produto excluir(Long produto_id) {
        	Produto produtoAux = produtoDao.consultarPorId(produto_id);
            if (produtoAux == null){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto nao encontrado com o id: " + produto_id+ ".");
            }
            
            int qtd = produtoDao.excluir(produto_id);
            if (qtd != 1){
                throw new ResponseStatusException(HttpStatus.CONFLICT, "A quantidade de entidades alteradas eh " +qtd+ ".");
            }
            
            return produtoAux;
        }
            
        public boolean nomeExiste(String nome) {
            return produtoDao.nomeExiste(nome);
        }
        
        // componente e produto alteracoes
        
        public List<Componente> consultarComponentesPorProduto(Long produto_id) {
            return produtoDao.consultarComponentesPorProduto(produto_id);
        }

        public void associarComponentes(Long produtoId, List<Long> componentes) {
            for (Long componenteId : componentes) {
                if (!existeAssociacao(produtoId, componenteId)) {
                    produtoDao.associarComponente(produtoId, componenteId);
                }
            }
        }

        private boolean existeAssociacao(Long produtoId, Long componenteId) {
            return produtoDao.verificarAssociacao(produtoId, componenteId);
        }

        
    

}
