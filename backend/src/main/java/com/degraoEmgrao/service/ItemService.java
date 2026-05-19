package com.degraoEmgrao.service;

import com.degraoEmgrao.model.Item;
import com.degraoEmgrao.model.MovimentacaoEstoque;
import com.degraoEmgrao.repository.ItemRepository;
import com.degraoEmgrao.repository.MovimentacaoEstoqueRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final MovimentacaoEstoqueRepository movimentacaoRepository;

    @Value("${app.fefo.alert-days:7}")
    private int alertDays;

    public ItemService(ItemRepository itemRepository,
                       MovimentacaoEstoqueRepository movimentacaoRepository) {
        this.itemRepository = itemRepository;
        this.movimentacaoRepository = movimentacaoRepository;
    }

    // -------------------------------------------------------
    // CRUD básico
    // -------------------------------------------------------
    public List<Item> listarTodos() {
        return itemRepository.findAll();
    }

    public List<Item> listarDisponiveis() {
        return itemRepository.findByStatus(Item.StatusItem.DISPONIVEL);
    }

    public Optional<Item> buscarPorId(Long id) {
        return itemRepository.findById(id);
    }

    public List<Item> buscarPorNome(String nome) {
        return itemRepository.findByNomeProdutoContainingIgnoreCaseAndStatus(nome, Item.StatusItem.DISPONIVEL);
    }

    @Transactional
    public Item salvar(Item item) {
        Item salvo = itemRepository.save(item);
        // Registra movimentação de entrada
        MovimentacaoEstoque mov = new MovimentacaoEstoque();
        mov.setItemId(salvo.getId());
        mov.setTipo(MovimentacaoEstoque.TipoMovimentacao.ENTRADA_DOACAO);
        mov.setQuantidade(salvo.getQuantidade());
        mov.setDoadorId(salvo.getDoadorId());
        mov.setObservacoes("Entrada automática ao cadastrar item");
        movimentacaoRepository.save(mov);
        return salvo;
    }

    @Transactional
    public Item atualizar(Long id, Item itemAtualizado) {
        return itemRepository.findById(id).map(item -> {
            item.setNomeProduto(itemAtualizado.getNomeProduto());
            item.setCategoria(itemAtualizado.getCategoria());
            item.setQuantidade(itemAtualizado.getQuantidade());
            item.setUnidadeMedida(itemAtualizado.getUnidadeMedida());
            item.setDataValidade(itemAtualizado.getDataValidade());
            item.setLote(itemAtualizado.getLote());
            item.setStatus(itemAtualizado.getStatus());
            return itemRepository.save(item);
        }).orElseThrow(() -> new RuntimeException("Item não encontrado: " + id));
    }

    @Transactional
    public void deletar(Long id) {
        itemRepository.deleteById(id);
    }

    // -------------------------------------------------------
    // FEFO — First Expired, First Out
    // -------------------------------------------------------

    /**
     * Retorna itens disponíveis ordenados por validade (mais próximos primeiro).
     * Deve ser chamado antes de qualquer distribuição para garantir o FEFO.
     */
    public List<Item> listarPorFEFO() {
        return itemRepository.findByStatusOrderByDataValidadeAsc(Item.StatusItem.DISPONIVEL);
    }

    /**
     * Retorna itens que vencem nos próximos {@code alertDays} dias.
     */
    public List<Item> alertasVencimento() {
        LocalDate dataLimite = LocalDate.now().plusDays(alertDays);
        return itemRepository.findItensProximosDoVencimento(dataLimite);
    }

    /**
     * Verifica e marca itens vencidos, registrando movimentação de descarte.
     */
    @Transactional
    public int processarItensVencidos() {
        List<Item> vencidos = itemRepository.findItensVencidos(LocalDate.now());
        for (Item item : vencidos) {
            item.setStatus(Item.StatusItem.VENCIDO);
            itemRepository.save(item);

            MovimentacaoEstoque mov = new MovimentacaoEstoque();
            mov.setItemId(item.getId());
            mov.setTipo(MovimentacaoEstoque.TipoMovimentacao.SAIDA_VENCIMENTO);
            mov.setQuantidade(item.getQuantidade());
            mov.setObservacoes("Item marcado como vencido automaticamente");
            movimentacaoRepository.save(mov);
        }
        return vencidos.size();
    }

    // -------------------------------------------------------
    // Dashboard
    // -------------------------------------------------------
    public Map<String, Object> obterResumoEstoque() {
        Map<String, Object> resumo = new HashMap<>();
        resumo.put("totalItensDisponiveis", itemRepository.contarItensDisponiveis());
        resumo.put("alertasVencimento", alertasVencimento().size());

        List<Object[]> porCategoria = itemRepository.contarQuantidadePorCategoria();
        Map<String, Double> categorias = new HashMap<>();
        for (Object[] row : porCategoria) {
            categorias.put(row[0].toString(), ((Number) row[1]).doubleValue());
        }
        resumo.put("quantidadePorCategoria", categorias);
        return resumo;
    }
}
