package com.degraoEmgrao.controller;

import com.degraoEmgrao.model.Familia;
import com.degraoEmgrao.service.FamiliaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/familias")
@CrossOrigin(origins = "*")
public class FamiliaController {

    private final FamiliaService familiaService;

    public FamiliaController(FamiliaService familiaService) {
        this.familiaService = familiaService;
    }

    @GetMapping
    public ResponseEntity<List<Familia>> listarTodas() {
        return ResponseEntity.ok(familiaService.listarTodas());
    }

    @GetMapping("/ativas")
    public ResponseEntity<List<Familia>> listarAtivas() {
        return ResponseEntity.ok(familiaService.listarAtivas());
    }

    @GetMapping("/indicadores")
    public ResponseEntity<Map<String, Object>> indicadores() {
        return ResponseEntity.ok(familiaService.obterIndicadores());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Familia> buscarPorId(@PathVariable Long id) {
        return familiaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Familia> criar(@Valid @RequestBody Familia familia) {
        return ResponseEntity.status(HttpStatus.CREATED).body(familiaService.salvar(familia));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Familia> atualizar(@PathVariable Long id, @Valid @RequestBody Familia familia) {
        return ResponseEntity.ok(familiaService.atualizar(id, familia));
    }

    @PatchMapping("/{id}/atendimento")
    public ResponseEntity<Void> registrarAtendimento(@PathVariable Long id) {
        familiaService.registrarAtendimento(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        familiaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
