package com.degraoEmgrao.controller;

import com.degraoEmgrao.model.MovimentacaoEstoque;
import com.degraoEmgrao.service.MovimentacaoEstoqueService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/movimentacoes")
@CrossOrigin(origins = "*")
public class MovimentacaoEstoqueController {

    private final MovimentacaoEstoqueService movimentacaoService;

    public MovimentacaoEstoqueController(MovimentacaoEstoqueService movimentacaoService) {
        this.movimentacaoService = movimentacaoService;
    }

    @GetMapping
    public ResponseEntity<List<MovimentacaoEstoque>> listarTodas() {
        return ResponseEntity.ok(movimentacaoService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovimentacaoEstoque> buscarPorId(@PathVariable Long id) {
        return movimentacaoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/item/{itemId}")
    public ResponseEntity<List<MovimentacaoEstoque>> buscarPorItem(@PathVariable Long itemId) {
        return ResponseEntity.ok(movimentacaoService.buscarPorItem(itemId));
    }

    @GetMapping("/doador/{doadorId}")
    public ResponseEntity<List<MovimentacaoEstoque>> buscarPorDoador(@PathVariable Long doadorId) {
        return ResponseEntity.ok(movimentacaoService.buscarPorDoador(doadorId));
    }

    @GetMapping("/familia/{familiaId}")
    public ResponseEntity<List<MovimentacaoEstoque>> buscarPorFamilia(@PathVariable Long familiaId) {
        return ResponseEntity.ok(movimentacaoService.buscarPorFamilia(familiaId));
    }

    @GetMapping("/tipo")
    public ResponseEntity<List<MovimentacaoEstoque>> buscarPorTipo(
            @RequestParam MovimentacaoEstoque.TipoMovimentacao tipo) {
        return ResponseEntity.ok(movimentacaoService.buscarPorTipo(tipo));
    }

    @GetMapping("/periodo")
    public ResponseEntity<List<MovimentacaoEstoque>> buscarPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        return ResponseEntity.ok(movimentacaoService.buscarPorPeriodo(inicio, fim));
    }

    @GetMapping("/total-distribuido")
    public ResponseEntity<Map<String, Double>> totalDistribuido(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        Double total = movimentacaoService.totalDistribuidoNoPeriodo(inicio, fim);
        return ResponseEntity.ok(Map.of("totalDistribuido", total));
    }

    @PostMapping
    public ResponseEntity<MovimentacaoEstoque> registrar(
            @Valid @RequestBody MovimentacaoEstoque movimentacao) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(movimentacaoService.registrar(movimentacao));
    }
}