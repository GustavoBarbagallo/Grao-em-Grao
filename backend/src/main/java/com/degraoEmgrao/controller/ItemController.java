package com.degraoEmgrao.controller;

import com.degraoEmgrao.model.Item;
import com.degraoEmgrao.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/itens")
@CrossOrigin(origins = "*")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public ResponseEntity<List<Item>> listarTodos() {
        return ResponseEntity.ok(itemService.listarTodos());
    }

    @GetMapping("/disponiveis")
    public ResponseEntity<List<Item>> listarDisponiveis() {
        return ResponseEntity.ok(itemService.listarDisponiveis());
    }

    @GetMapping("/fefo")
    public ResponseEntity<List<Item>> listarPorFEFO() {
        return ResponseEntity.ok(itemService.listarPorFEFO());
    }

    @GetMapping("/alertas-vencimento")
    public ResponseEntity<List<Item>> alertasVencimento() {
        return ResponseEntity.ok(itemService.alertasVencimento());
    }

    @GetMapping("/resumo-estoque")
    public ResponseEntity<Map<String, Object>> resumoEstoque() {
        return ResponseEntity.ok(itemService.obterResumoEstoque());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> buscarPorId(@PathVariable Long id) {
        return itemService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Item>> buscarPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(itemService.buscarPorNome(nome));
    }

    @PostMapping
    public ResponseEntity<Item> criar(@Valid @RequestBody Item item) {
        return ResponseEntity.status(HttpStatus.CREATED).body(itemService.salvar(item));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Item> atualizar(@PathVariable Long id, @Valid @RequestBody Item item) {
        return ResponseEntity.ok(itemService.atualizar(id, item));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        itemService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/processar-vencidos")
    public ResponseEntity<Map<String, Integer>> processarVencidos() {
        int total = itemService.processarItensVencidos();
        return ResponseEntity.ok(Map.of("itensProcessados", total));
    }
}
