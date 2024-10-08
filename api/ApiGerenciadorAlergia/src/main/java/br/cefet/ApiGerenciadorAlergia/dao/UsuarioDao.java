package br.cefet.ApiGerenciadorAlergia.dao;

import java.util.List;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.springframework.stereotype.Repository;

import br.cefet.ApiGerenciadorAlergia.model.Usuario;

@Repository
@RegisterBeanMapper(Usuario.class)

public interface UsuarioDao {

	@GetGeneratedKeys
	@SqlUpdate(" insert into usuario (nome, email, senha, tipo_usuario) "
			+ " values (:nome, :email, :senha, :tipoUsuario);")
	public Long inserir(@BindBean Usuario u);
	

	@SqlQuery(" select * from usuario;")
	public List<Usuario> consultar();
	
	@SqlQuery(" select * from usuario "
			+ " where usuario_id = :usuario_id;")
	public Usuario consultarPorId(@Bind Long usuario_id);
	
	@SqlQuery("select count(*) > 0 from usuario where email = :email")
    boolean emailExists(@Bind("email") String email);

	@SqlUpdate(" update usuario "
			+ "  set nome = :nome, "
			+ "      email = :email, "
			+ "      senha = :senha, "
			+ "      tipo_usuario = :tipoUsuario "
			+ " where usuario_id = :usuario_id;")
	public int alterar(@BindBean Usuario u);
	
	@SqlUpdate(" delete from usuario "
			+ " where usuario_id = :usuario_id;")
	public int excluir(@Bind Long usuario_id);


}