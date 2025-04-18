package br.cefet.ApiGerenciadorAlergia.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ProdutoComponente {
	private long produto_id;
	private long componente_id;
}
