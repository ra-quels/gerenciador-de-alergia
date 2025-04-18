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
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/v1/usuario")
@CrossOrigin(origins = "http://localhost:8100")  

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
    @GetMapping("/autenticar/{email}/{senha}")
    public ResponseEntity<Usuario> autenticar(@PathVariable("email") String email, @PathVariable("senha") String senha) {
        Usuario usuario = usuarioService.autenticar(email, senha);
        
        if (usuario == null) {
            // Retorna um status 200 OK com um corpo vazio, sem parar o sistema
            return ResponseEntity.ok(null);
        }
        
        return ResponseEntity.ok(usuario);
    }
    
    
    @GetMapping("/verificarLogin")
    public Usuario verificarLogin(@RequestParam("email") String email) {
        Usuario usuario = usuarioService.verificarLogin(email);
        if (usuario == null) {
        	 return usuario;
        }
        return usuario;
    }
    
    @GetMapping("/recuperarAutenticacao")
    public ResponseEntity<Usuario> recuperarAutenticacao() {
        Usuario usuario = usuarioService.recuperarAutenticacao();
        return ResponseEntity.ok(usuario);
    }

    @PostMapping("/desautenticar")
    public ResponseEntity<Void> encerrarAutenticacao() {
        usuarioService.encerrarAutenticacao();
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/registrarAutenticacao")
    public ResponseEntity<Void> registrarAutenticacao(@RequestBody Usuario usuario) {
        usuarioService.registrarAutenticacao(usuario);
        return ResponseEntity.noContent().build();
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
