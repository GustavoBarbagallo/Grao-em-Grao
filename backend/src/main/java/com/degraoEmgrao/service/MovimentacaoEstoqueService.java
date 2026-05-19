package com.degraoEmgrao.service;

import com.degraoEmgrao.model.MovimentacaoEstoque;
import com.degraoEmgrao.repository.MovimentacaoEstoqueRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MovimentacaoEstoqueService {

    private final MovimentacaoEstoqueRepository movimentacaoRepository;

    public MovimentacaoEstoqueService(MovimentacaoEstoqueRepository movimentacaoRepository) {
        this.movimentacaoRepository = movimentacaoRepository;
    }

    public List<MovimentacaoEstoque> listarTodas() {
        return movimentacaoRepository.findAll();
    }

    public Optional<MovimentacaoEstoque> buscarPorId(Long id) {
        return movimentacaoRepository.findById(id);
    }

    public List<MovimentacaoEstoque> buscarPorItem(Long itemId) {
        return movimentacaoRepository.findByItemIdOrderByDataHoraDesc(itemId);
    }

    public List<MovimentacaoEstoque> buscarPorDoador(Long doadorId) {
        return movimentacaoRepository.findByDoadorIdOrderByDataHoraDesc(doadorId);
    }

    public List<MovimentacaoEstoque> buscarPorFamilia(Long familiaId) {
        return movimentacaoRepository.findByFamiliaIdOrderByDataHoraDesc(familiaId);
    }

    public List<MovimentacaoEstoque> buscarPorTipo(MovimentacaoEstoque.TipoMovimentacao tipo) {
        return movimentacaoRepository.findByTipoOrderByDataHoraDesc(tipo);
    }

    public List<MovimentacaoEstoque> buscarPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return movimentacaoRepository.findByPeriodo(inicio, fim);
    }

    public MovimentacaoEstoque registrar(MovimentacaoEstoque movimentacao) {
        if (movimentacao.getDataHora() == null) {
            movimentacao.setDataHora(LocalDateTime.now());
        }
        return movimentacaoRepository.save(movimentacao);
    }

    public Double totalDistribuidoNoPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return movimentacaoRepository.totalDistribuidoNoPeriodo(inicio, fim);
    }
}