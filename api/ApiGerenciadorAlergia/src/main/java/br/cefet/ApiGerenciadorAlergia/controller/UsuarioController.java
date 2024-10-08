package br.cefet.ApiGerenciadorAlergia.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import br.cefet.ApiGerenciadorAlergia.model.Usuario;
import br.cefet.ApiGerenciadorAlergia.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/v1/usuario")
public class UsuarioController {
	
	private final UsuarioService usuarioService;
	
	public UsuarioController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}
	

    @PostMapping({"/", ""})
    public ResponseEntity<Usuario> inserir(@Valid @RequestBody Usuario u) {
        return ResponseEntity.ok(usuarioService.inserir(u));
    }

    @GetMapping({"/", ""})
    public ResponseEntity<List<Usuario>> consultar() {
        return ResponseEntity.ok(usuarioService.consultar());
    }

    @GetMapping({"/{usuario_id}"})
    public ResponseEntity<Usuario> consultarPorId(@PathVariable Long usuario_id) {
        return ResponseEntity.ok(usuarioService.consultarPorId(usuario_id));
    }
    
    @GetMapping("/{email}/email/exists")
    public ResponseEntity<Boolean> verificarEmail(@PathVariable String email) {
        boolean exists = usuarioService.emailExists(email);
        return ResponseEntity.ok(exists);
    }

    
    @PutMapping("/{usuario_id}")
    public ResponseEntity<Usuario> alterar(@PathVariable Long usuario_id, @Valid @RequestBody Usuario u) {
    	  u.setUsuario_id(usuario_id);
    	return ResponseEntity.ok(usuarioService.alterar(u));
    }

    
    @DeleteMapping({"/{usuario_id}"})
    public ResponseEntity<Usuario> excluir(@PathVariable Long usuario_id) {
        return ResponseEntity.ok(usuarioService.excluir(usuario_id));
    }

	
}
