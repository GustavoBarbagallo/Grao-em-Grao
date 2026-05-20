package com.degraoEmgrao.controller;

import com.degraoEmgrao.model.MovimentacaoEstoque;
import com.degraoEmgrao.service.MovimentacaoEstoqueService;
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

    // GET /api/movimentacoes
    @GetMapping
    public ResponseEntity<List<MovimentacaoEstoque>> listarTodas() {
        return ResponseEntity.ok(movimentacaoService.listarTodas());
    }

    // GET /api/movimentacoes/{id}
    @GetMapping("/{id}")
    public ResponseEntity<MovimentacaoEstoque> buscarPorId(@PathVariable Integer id) {
        return movimentacaoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/movimentacoes/pessoa/{idPessoa}
    @GetMapping("/pessoa/{idPessoa}")
    public ResponseEntity<List<MovimentacaoEstoque>> buscarPorPessoa(
            @PathVariable Integer idPessoa) {
        return ResponseEntity.ok(movimentacaoService.buscarPorPessoa(idPessoa));
    }

    // GET /api/movimentacoes/tipo?tipo=ENTRADA
    @GetMapping("/tipo")
    public ResponseEntity<List<MovimentacaoEstoque>> buscarPorTipo(
            @RequestParam MovimentacaoEstoque.TipoMovimentacao tipo) {
        return ResponseEntity.ok(movimentacaoService.buscarPorTipo(tipo));
    }

    // GET /api/movimentacoes/periodo?inicio=...&fim=...
    @GetMapping("/periodo")
    public ResponseEntity<List<MovimentacaoEstoque>> buscarPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        return ResponseEntity.ok(movimentacaoService.buscarPorPeriodo(inicio, fim));
    }

    // GET /api/movimentacoes/total-distribuido?inicio=...&fim=...
    @GetMapping("/total-distribuido")
    public ResponseEntity<Map<String, Double>> totalDistribuido(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        Double total = movimentacaoService.totalDistribuidoNoPeriodo(inicio, fim);
        return ResponseEntity.ok(Map.of("totalDistribuido", total));
    }

    // POST /api/movimentacoes
    @PostMapping
    public ResponseEntity<MovimentacaoEstoque> registrar(
            @RequestBody MovimentacaoEstoque movimentacao) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(movimentacaoService.registrar(movimentacao));
    }
}