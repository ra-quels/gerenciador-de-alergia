package br.cefet.ApiGerenciadorAlergia.model;

import lombok.Getter;
import lombok.Setter;
import br.cefet.ApiGerenciadorAlergia.enums.TipoUsuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import jakarta.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

@Entity

public class Usuario {

	private long usuario_id;
	
	@NotBlank(message = "Nome e infomacao obrigatoria")
	@Size(min = 4, max = 45)
	private String nome;
	
	private String email;
	
	private String senha;
	
	private TipoUsuario tipoUsuario;

}
