package com.degraoEmgrao.service;

import com.degraoEmgrao.repository.DoadorRepository;
import com.degraoEmgrao.repository.FamiliaRepository;
import com.degraoEmgrao.repository.ItemRepository;
import com.degraoEmgrao.repository.MovimentacaoEstoqueRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class DashboardService {

    private final ItemRepository itemRepository;
    private final FamiliaRepository familiaRepository;
    private final DoadorRepository doadorRepository;
    private final MovimentacaoEstoqueRepository movimentacaoRepository;
    private final ItemService itemService;
    private final FamiliaService familiaService;

    public DashboardService(ItemRepository itemRepository,
                            FamiliaRepository familiaRepository,
                            DoadorRepository doadorRepository,
                            MovimentacaoEstoqueRepository movimentacaoRepository,
                            ItemService itemService,
                            FamiliaService familiaService) {
        this.itemRepository = itemRepository;
        this.familiaRepository = familiaRepository;
        this.doadorRepository = doadorRepository;
        this.movimentacaoRepository = movimentacaoRepository;
        this.itemService = itemService;
        this.familiaService = familiaService;
    }

    /**
     * Retorna todos os indicadores consolidados para o painel principal.
     */
    public Map<String, Object> obterIndicadoresGerais() {
        Map<String, Object> dashboard = new HashMap<>();

        // Estoque
        dashboard.put("estoque", itemService.obterResumoEstoque());

        // Famílias
        dashboard.put("familias", familiaService.obterIndicadores());

        // Doadores
        dashboard.put("totalDoadores", doadorRepository.count());

        // Distribuição últimos 30 dias
        LocalDateTime trintaDiasAtras = LocalDateTime.now().minusDays(30);
        Double totalDistribuido = movimentacaoRepository.totalDistribuidoNoPeriodo(trintaDiasAtras, LocalDateTime.now());
        dashboard.put("totalDistribuidoUltimos30Dias", totalDistribuido != null ? totalDistribuido : 0.0);

        // Total de movimentações
        dashboard.put("totalMovimentacoes", movimentacaoRepository.count());

        return dashboard;
    }
}
