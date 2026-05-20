package com.degraoEmgrao.service;

import com.degraoEmgrao.repository.EstoqueRepository;
import com.degraoEmgrao.repository.MovimentacaoEstoqueRepository;
import com.degraoEmgrao.repository.PessoaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class DashboardService {

    private final EstoqueRepository estoqueRepository;
    private final PessoaRepository pessoaRepository;
    private final MovimentacaoEstoqueRepository movimentacaoRepository;

    public DashboardService(EstoqueRepository estoqueRepository,
                            PessoaRepository pessoaRepository,
                            MovimentacaoEstoqueRepository movimentacaoRepository) {
        this.estoqueRepository = estoqueRepository;
        this.pessoaRepository = pessoaRepository;
        this.movimentacaoRepository = movimentacaoRepository;
    }

    public Map<String, Object> obterIndicadoresGerais() {
        Map<String, Object> dashboard = new HashMap<>();

        dashboard.put("totalPessoas", pessoaRepository.count());
        dashboard.put("totalMovimentacoes", movimentacaoRepository.count());
        dashboard.put("totalEstoque", estoqueRepository.count());

        LocalDateTime trintaDiasAtras = LocalDateTime.now().minusDays(30);
        Double totalDistribuido = movimentacaoRepository
                .totalDistribuidoNoPeriodo(trintaDiasAtras, LocalDateTime.now());
        dashboard.put("totalDistribuidoUltimos30Dias",
                totalDistribuido != null ? totalDistribuido : 0.0);

        return dashboard;
    }
}