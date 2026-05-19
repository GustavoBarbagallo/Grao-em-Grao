package com.degraoEmgrao.controller;

import com.degraoEmgrao.model.Doador;
import com.degraoEmgrao.service.DoadorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/doadores")
@CrossOrigin(origins = "*")
public class DoadorController {

    private final DoadorService doadorService;

    public DoadorController(DoadorService doadorService) {
        this.doadorService = doadorService;
    }

    @GetMapping
    public ResponseEntity<List<Doador>> listarTodos() {
        return ResponseEntity.ok(doadorService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doador> buscarPorId(@PathVariable Long id) {
        return doadorService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/relatorio")
    public ResponseEntity<Map<String, Object>> relatorioDoador(@PathVariable Long id) {
        return ResponseEntity.ok(doadorService.gerarRelatorioDoador(id));
    }

    @PostMapping
    public ResponseEntity<Doador> criar(@Valid @RequestBody Doador doador) {
        return ResponseEntity.status(HttpStatus.CREATED).body(doadorService.salvar(doador));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Doador> atualizar(@PathVariable Long id, @Valid @RequestBody Doador doador) {
        return ResponseEntity.ok(doadorService.atualizar(id, doador));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        doadorService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
