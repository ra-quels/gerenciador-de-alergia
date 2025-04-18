package br.cefet.ApiGerenciadorAlergia.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class UsuarioComponente {
	private long usuario_id;
	private long componente_id;
}
