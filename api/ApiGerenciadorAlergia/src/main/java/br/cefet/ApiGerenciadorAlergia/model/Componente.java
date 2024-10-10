package br.cefet.ApiGerenciadorAlergia.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Componente {
	private Long componente_id;
	 @NotBlank(message = "Nome e infomacao obrigatoria")
	 private String nome;
}
