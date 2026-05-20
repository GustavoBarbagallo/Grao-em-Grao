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

    public Optional<MovimentacaoEstoque> buscarPorId(Integer id) {
        return movimentacaoRepository.findById(id);
    }

    public List<MovimentacaoEstoque> buscarPorPessoa(Integer pessoaId) {
        return movimentacaoRepository.findByPessoaIdPessoa(pessoaId);
    }

    public List<MovimentacaoEstoque> buscarPorTipo(MovimentacaoEstoque.TipoMovimentacao tipo) {
        return movimentacaoRepository.findByTipoMovimentacao(tipo);
    }

    public List<MovimentacaoEstoque> buscarPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return movimentacaoRepository.findByPeriodo(inicio, fim);
    }

    public MovimentacaoEstoque registrar(MovimentacaoEstoque movimentacao) {
        if (movimentacao.getDataHoraMovimentacao() == null) {
            movimentacao.setDataHoraMovimentacao(LocalDateTime.now());
        }
        return movimentacaoRepository.save(movimentacao);
    }

    public Double totalDistribuidoNoPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return movimentacaoRepository.totalDistribuidoNoPeriodo(inicio, fim);
    }
}