package com.degraoEmgrao.controller;

import com.degraoEmgrao.model.Estoque;
import com.degraoEmgrao.model.Item;
import com.degraoEmgrao.service.ItemService;
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

    // GET /api/itens
    @GetMapping
    public ResponseEntity<List<Item>> listarTodos() {
        return ResponseEntity.ok(itemService.listarTodos());
    }

    // GET /api/itens/{gtin}
    @GetMapping("/{gtin}")
    public ResponseEntity<Item> buscarPorGtin(@PathVariable String gtin) {
        return itemService.buscarPorGtin(gtin)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/itens/fefo
    @GetMapping("/fefo")
    public ResponseEntity<List<Estoque>> listarPorFEFO() {
        return ResponseEntity.ok(itemService.listarPorFEFO());
    }

    // GET /api/itens/alertas-vencimento
    @GetMapping("/alertas-vencimento")
    public ResponseEntity<List<Estoque>> alertasVencimento() {
        return ResponseEntity.ok(itemService.alertasVencimento());
    }

    // POST /api/itens
    @PostMapping
    public ResponseEntity<Item> criar(@RequestBody Item item) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(itemService.salvar(item));
    }

    // PUT /api/itens/{gtin}
    @PutMapping("/{gtin}")
    public ResponseEntity<Item> atualizar(@PathVariable String gtin,
                                          @RequestBody Item item) {
        return ResponseEntity.ok(itemService.atualizar(gtin, item));
    }

    // DELETE /api/itens/{gtin}
    @DeleteMapping("/{gtin}")
    public ResponseEntity<Void> deletar(@PathVariable String gtin) {
        itemService.deletar(gtin);
        return ResponseEntity.noContent().build();
    }

    // POST /api/itens/processar-vencidos
    @PostMapping("/processar-vencidos")
    public ResponseEntity<Map<String, Integer>> processarVencidos() {
        int total = itemService.processarItensVencidos();
        return ResponseEntity.ok(Map.of("itensProcessados", total));
    }
}