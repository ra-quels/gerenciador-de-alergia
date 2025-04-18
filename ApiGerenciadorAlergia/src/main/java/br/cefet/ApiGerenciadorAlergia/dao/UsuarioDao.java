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
	
	@SqlUpdate("INSERT INTO usuariocomponente (usuario_id, componente_id) VALUES (:usuarioId, :componenteId)")
	void associarAlergia(@BindBean("usuarioId") Long usuarioId, @BindBean("componenteId") Long componenteId);

	@SqlQuery(" select * from usuario;")
	public List<Usuario> consultar();
	
	@SqlQuery(" select * from usuario "
			+ " where usuario_id = :usuario_id;")
	public Usuario consultarPorId(@Bind Long usuario_id);
	
	@SqlQuery("select count(*) > 0 from usuario where email = :email")
    boolean emailExists(@Bind("email") String email);

	 @SqlQuery("select * "
             + "from usuario "
             + " where email like :email;")
     Usuario getUsuarioPorEmail(@Bind("email") String email);
	
	
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

    @SqlQuery("SELECT * FROM usuario "
            + "WHERE email = :email AND senha = :senha")
    Usuario consultarPorEmailESenha(@Bind("email") String email, @Bind("senha") String senha);
    
    @SqlQuery("SELECT * FROM usuario WHERE email = :email")
    Usuario consultarPorEmail(@Bind("email") String email);
    
    @SqlUpdate("UPDATE usuario SET autenticado = true WHERE id = :id")
    public void registrarAutenticacao(@Bind int id);

    @SqlQuery("SELECT * FROM usuario WHERE autenticado = true LIMIT 1")
    public Usuario recuperarAutenticacao();

    @SqlUpdate("UPDATE usuario SET autenticado = false WHERE id = :id")
    public void encerrarAutenticacao(@Bind int id);

}