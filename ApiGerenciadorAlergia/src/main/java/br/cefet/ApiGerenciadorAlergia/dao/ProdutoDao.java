package br.cefet.ApiGerenciadorAlergia.dao;

import java.util.List;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.springframework.stereotype.Repository;

import br.cefet.ApiGerenciadorAlergia.model.Componente;
import br.cefet.ApiGerenciadorAlergia.model.Produto;

@Repository
@RegisterBeanMapper(Produto.class)

public interface ProdutoDao {
	@GetGeneratedKeys
	@SqlUpdate(" insert into produto (nome, descricao, foto) "
			+ " values (:nome, :descricao, :foto);")
	public Long inserir(@BindBean Produto p);
	
	@SqlQuery(" select * from produto;")
	public List<Produto> consultar();
	
	
	@SqlQuery("select count(*) > 0 from produto where nome = :nome")
    boolean nomeExiste(@Bind("nome") String nome);
	
	@SqlUpdate("update produto set nome = :nome, descricao = :descricao, foto = :foto where produto_id = :produto_id;")
	public int alterar(@BindBean Produto p);

	@SqlUpdate(" delete from produto "
			+ " where produto_id = :produto_id;")
	public int excluir(@Bind Long produto_id);
	
	// metodo associar componente ao produto
	
	 @SqlUpdate("insert into produto_componente (produto_id, componente_id) values (:produto_id, :componente_id);")
	    public void associarComponente(@Bind("produto_id") long produto_id, @Bind("componente_id") long componente_id);

	 @SqlQuery("select * from produto where produto_id = :produto_id")
	 @RegisterBeanMapper(Produto.class)
	 public Produto consultarPorId(@Bind("produto_id") Long produto_id);

	 @SqlQuery("select c.* from componente c join produto_componente pc on c.componente_id = pc.componente_id where pc.produto_id = :produto_id")
	 @RegisterBeanMapper(Componente.class)
	 public List<Componente> consultarComponentesPorProduto(@Bind("produto_id") Long produto_id);

	 @SqlQuery("SELECT COUNT(*) > 0 FROM produto_componente WHERE produto_id = :produtoId AND componente_id = :componenteId")
	 boolean verificarAssociacao(@Bind("produtoId") Long produtoId, @Bind("componenteId") Long componenteId);

}
