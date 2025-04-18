package br.cefet.ApiGerenciadorAlergia.model;

import java.util.List;

import jakarta.validation.constraints.Size;
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
public class Produto {
	private long produto_id;
	    @Size(min = 2)
	 private String nome;
	private String descricao;
    private String foto;
    private List<Long> componentes;
}