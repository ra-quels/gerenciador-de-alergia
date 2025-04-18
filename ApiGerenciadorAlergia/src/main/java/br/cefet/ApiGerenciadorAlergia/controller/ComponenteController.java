package br.cefet.ApiGerenciadorAlergia.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import br.cefet.ApiGerenciadorAlergia.model.Componente;
import br.cefet.ApiGerenciadorAlergia.service.ComponenteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/v1/componente")
// @CrossOrigin(origins = "http://localhost:8100")  
@CrossOrigin(origins = "*")

public class ComponenteController {
	 
	private final ComponenteService componenteService;
	
	public ComponenteController(ComponenteService componenteService) {
		this.componenteService = componenteService;
	}


    @PostMapping({"/", ""})
    public ResponseEntity<Componente> inserir(@Valid @RequestBody Componente c) {
        return ResponseEntity.ok(componenteService.inserir(c));
    }

    @GetMapping({"/", ""})
    public ResponseEntity<List<Componente>> consultar() {
        return ResponseEntity.ok(componenteService.consultar());
    }

    @GetMapping({"/{componente_id}"})
    public ResponseEntity<Componente> consultarPorId(@PathVariable Long componente_id) {
        return ResponseEntity.ok(componenteService.consultarPorId(componente_id));
    }
    
    @GetMapping("/{nome}/nome/exists")
    public ResponseEntity<Boolean> verificarNome(@PathVariable String nome) {
        boolean exists = componenteService.nomeExiste(nome);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/alergias")
    public ResponseEntity<List<Componente>> listarAlergias() {
        List<Componente> alergias = componenteService.consultar(); // Obtém as alergias do serviço
        return ResponseEntity.ok(alergias);
    }

    // Novo endpoint para obter alergias de um usuário
    @GetMapping("/{usuarioId}/alergias")
    public ResponseEntity<List<Componente>> obterAlergiasDoUsuario(@PathVariable Long usuarioId) {
        List<Componente> alergias = componenteService.obterAlergiasDoUsuario(usuarioId);
        return ResponseEntity.ok(alergias);
    }
   

 // Método para adicionar alergias ao usuário no backend
    @PostMapping("/{usuarioId}/alergias")
    public ResponseEntity<Void> adicionarAlergiasAoUsuario(@PathVariable Long usuarioId, @RequestBody List<Long> alergiaIds) {
            componenteService.adicionarAlergiasAoUsuario(usuarioId, alergiaIds);
            return ResponseEntity.ok().build();
       
        
    }


    @PutMapping("/{componente_id}")
    public ResponseEntity<Componente> alterar(@PathVariable Long componente_id, @Valid @RequestBody Componente c) {
    	  c.setComponente_id(componente_id);
    	return ResponseEntity.ok(componenteService.alterar(c));
    }

    
    @DeleteMapping({"/{componente_id}"})
    public ResponseEntity<Componente> excluir(@PathVariable Long componente_id) {
        return ResponseEntity.ok(componenteService.excluir(componente_id));
    }

	
}
