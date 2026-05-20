package com.degraoEmgrao.service;

import com.degraoEmgrao.model.Estoque;
import com.degraoEmgrao.model.MovimentacaoEstoque;
import com.degraoEmgrao.repository.EstoqueRepository;
import com.degraoEmgrao.repository.ItemRepository;
import com.degraoEmgrao.repository.MovimentacaoEstoqueRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.degraoEmgrao.model.Item;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final EstoqueRepository estoqueRepository;
    private final MovimentacaoEstoqueRepository movimentacaoRepository;

    public ItemService(ItemRepository itemRepository,
                       EstoqueRepository estoqueRepository,
                       MovimentacaoEstoqueRepository movimentacaoRepository) {
        this.itemRepository = itemRepository;
        this.estoqueRepository = estoqueRepository;
        this.movimentacaoRepository = movimentacaoRepository;
    }

    public List<Item> listarTodos() {
        return itemRepository.findAll();
    }

    public Optional<Item> buscarPorGtin(String gtin) {
        return itemRepository.findById(gtin);
    }

    @Transactional
    public Item salvar(Item item) {
        return itemRepository.save(item);
    }

    @Transactional
    public Item atualizar(String gtin, Item itemAtualizado) {
        return itemRepository.findById(gtin).map(item -> {
            item.setNomeProduto(itemAtualizado.getNomeProduto());
            item.setCategoria(itemAtualizado.getCategoria());
            item.setUnidadeMedida(itemAtualizado.getUnidadeMedida());
            return itemRepository.save(item);
        }).orElseThrow(() -> new RuntimeException("Item não encontrado: " + gtin));
    }

    @Transactional
    public void deletar(String gtin) {
        itemRepository.deleteById(gtin);
    }

    // FEFO — itens ordenados por validade mais próxima
    public List<Estoque> listarPorFEFO() {
        return estoqueRepository.findByStatusOrderByDataValidadeAsc(
                Estoque.Status.DISPONIVEL);
    }

    // Alertas de vencimento nos próximos 7 dias
    public List<Estoque> alertasVencimento() {
        LocalDate dataLimite = LocalDate.now().plusDays(7);
        return estoqueRepository.findByDataValidadeBeforeAndStatus(
                dataLimite, Estoque.Status.DISPONIVEL);
    }

    // Marca itens vencidos e registra movimentação de saída
    @Transactional
    public int processarItensVencidos() {
        List<Estoque> vencidos = estoqueRepository.findByDataValidadeBeforeAndStatus(
                LocalDate.now(), Estoque.Status.DISPONIVEL);

        for (Estoque estoque : vencidos) {
            estoque.setStatus(Estoque.Status.VENCIDO);
            estoqueRepository.save(estoque);

            MovimentacaoEstoque mov = new MovimentacaoEstoque();
            mov.setEstoque(estoque);
            mov.setTipoMovimentacao(MovimentacaoEstoque.TipoMovimentacao.SAIDA);
            mov.setQuantidadeMovimentada(estoque.getQuantidadeAtual());
            mov.setDataHoraMovimentacao(LocalDateTime.now());
            mov.setObservacao("Item marcado como vencido automaticamente");
            movimentacaoRepository.save(mov);
        }
        return vencidos.size();
    }
}