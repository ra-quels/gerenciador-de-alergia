package br.cefet.ApiGerenciadorAlergia.dao;

import java.util.List;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.springframework.stereotype.Repository;

import br.cefet.ApiGerenciadorAlergia.model.Produto;

@Repository
@RegisterBeanMapper(Produto.class)

public interface ProdutoDao {
	@GetGeneratedKeys
	@SqlUpdate(" insert into produto (nome, descricao) "
			+ " values (:nome, :descricao);")
	public Long inserir(@BindBean Produto p);
	
	@SqlQuery(" select * from produto;")
	public List<Produto> consultar();
	
	@SqlQuery(" select * from produto "
			+ " where produto _id = :produto_id;")
	public Produto consultarPorId(@Bind Long produto_id);
	
	@SqlQuery("select count(*) > 0 from produto where nome = :nome")
    boolean nomeExiste(@Bind("nome") String nome);
	
	@SqlUpdate(" update produto "
			+ "  set nome = :nome, "
			+ "  set descricao = :descricao, "
			+ " where produto_id = :produto_id;")
	public int alterar(@BindBean Produto p);
	
	@SqlUpdate(" delete from produto "
			+ " where produto_id = :produto_id;")
	public int excluir(@Bind Long produto_id);
}
