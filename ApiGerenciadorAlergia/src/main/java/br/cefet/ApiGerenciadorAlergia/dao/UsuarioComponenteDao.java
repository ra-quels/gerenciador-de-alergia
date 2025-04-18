package br.cefet.ApiGerenciadorAlergia.dao;

import java.util.List;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import br.cefet.ApiGerenciadorAlergia.model.Componente;

@RegisterBeanMapper(Componente.class)
public interface UsuarioComponenteDao {

    // Método para obter as alergias (componentes) de um usuário
    @SqlQuery("SELECT c.componente_id, c.nome " +
              "FROM usuario_componente uc " +
              "INNER JOIN componente c ON uc.componente_id = c.componente_id " +
              "WHERE uc.usuario_id = :usuarioId;")
    List<Componente> obterAlergiasDoUsuario(@Bind Long usuarioId);

    @SqlUpdate("DELETE FROM usuario_componente WHERE usuario_id = :usuarioId")
    void removerAlergiasDoUsuario(@Bind Long usuarioId);

    @SqlUpdate("INSERT INTO usuario_componente (usuario_id, componente_id) VALUES (:usuarioId, :componenteId)")
    void associarAlergiaAoUsuario(@Bind Long usuarioId, @Bind Long componenteId);


    // Novo método para verificar se o usuário existe
    @SqlQuery("SELECT count(*) > 0 FROM usuario WHERE usuario_id = :usuarioId")
    boolean usuarioExiste(@Bind Long usuarioId);


}
