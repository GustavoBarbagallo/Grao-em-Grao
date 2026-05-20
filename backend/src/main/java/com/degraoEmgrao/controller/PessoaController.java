package com.degraoEmgrao.controller;

import com.degraoEmgrao.model.Pessoa;
import com.degraoEmgrao.service.PessoaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pessoas")
@CrossOrigin(origins = "*")
public class PessoaController {

    private final PessoaService pessoaService;

    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    // GET /api/pessoas
    @GetMapping
    public ResponseEntity<List<Pessoa>> listarTodas() {
        return ResponseEntity.ok(pessoaService.listarTodas());
    }

    // GET /api/pessoas/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> buscarPorId(@PathVariable Integer id) {
        return pessoaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/pessoas/papel?papel=DOADOR
    @GetMapping("/papel")
    public ResponseEntity<List<Pessoa>> buscarPorPapel(@RequestParam String papel) {
        return ResponseEntity.ok(pessoaService.buscarPorPapel(papel));
    }

    // POST /api/pessoas
    @PostMapping
    public ResponseEntity<Pessoa> cadastrar(@Valid @RequestBody Pessoa pessoa) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(pessoaService.salvar(pessoa));
    }

    // PUT /api/pessoas/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Pessoa> atualizar(
            @PathVariable Integer id,
            @Valid @RequestBody Pessoa pessoa) {
        return ResponseEntity.ok(pessoaService.atualizar(id, pessoa));
    }

    // DELETE /api/pessoas/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        pessoaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // POST /api/pessoas/login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credenciais) {
        String documento = credenciais.get("documentoLogin");
        String senha = credenciais.get("senha");
        return pessoaService.login(documento, senha)
                .map(p -> ResponseEntity.ok((Object) p))
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Documento ou senha inválidos"));
    }
}