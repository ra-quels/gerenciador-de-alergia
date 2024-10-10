package br.cefet.ApiGerenciadorAlergia.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import br.cefet.ApiGerenciadorAlergia.model.Produto;
import br.cefet.ApiGerenciadorAlergia.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/v1/produto")
public class ProdutoController {
	
	private final ProdutoService produtoService;
	
	public ProdutoController(ProdutoService produtoService) {
		this.produtoService = produtoService;
	}
	

    @PostMapping({"/", ""})
    public ResponseEntity<Produto> inserir(@Valid @RequestBody Produto p) {
        return ResponseEntity.ok(produtoService.inserir(p));
    }

    @GetMapping({"/", ""})
    public ResponseEntity<List<Produto>> consultar() {
        return ResponseEntity.ok(produtoService.consultar());
    }

    @GetMapping({"/{produto_id}"})
    public ResponseEntity<Produto> consultarPorId(@PathVariable Long produto_id) {
        return ResponseEntity.ok(produtoService.consultarPorId(produto_id));
    }
    
    @GetMapping("/{nome}/nome/exists")
    public ResponseEntity<Boolean> verificarNome(@PathVariable String nome) {
        boolean exists = produtoService.nomeExiste(nome);
        return ResponseEntity.ok(exists);
    }

    
    @PutMapping("/{produto_id}")
    public ResponseEntity<Produto> alterar(@PathVariable Long produto_id, @Valid @RequestBody Produto p) {
    	  p.setProduto_id(produto_id);
    	return ResponseEntity.ok(produtoService.alterar(p));
    }

    
    @DeleteMapping({"/{produto_id}"})
    public ResponseEntity<Produto> excluir(@PathVariable Long produto_id) {
        return ResponseEntity.ok(produtoService.excluir(produto_id));
    }

	
}
