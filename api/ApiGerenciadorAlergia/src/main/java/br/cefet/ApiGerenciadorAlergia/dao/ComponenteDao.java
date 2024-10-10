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

@Repository
@RegisterBeanMapper(Componente.class)

public interface ComponenteDao {
	@GetGeneratedKeys
	@SqlUpdate(" insert into componente (nome) "
			+ " values (:nome);")
	public Long inserir(@BindBean Componente a);
	
	@SqlQuery(" select * from componente;")
	public List<Componente> consultar();
	
	@SqlQuery(" select * from componente "
			+ " where componente_id = :componente_id;")
	public Componente consultarPorId(@Bind Long componente_id);
	
	@SqlQuery("select count(*) > 0 from componente where nome = :nome")
    boolean nomeExiste(@Bind("nome") String nome);
	
	@SqlUpdate(" update componente "
			+ "  set nome = :nome, "
			+ " where componente_id = :componente_id;")
	public int alterar(@BindBean Componente a);
	
	@SqlUpdate(" delete from componente "
			+ " where componente_id = :componente_id;")
	public int excluir(@Bind Long componente_id);
}
