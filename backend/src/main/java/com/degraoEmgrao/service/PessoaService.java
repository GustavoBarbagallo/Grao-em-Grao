package com.degraoEmgrao.service;

import com.degraoEmgrao.model.Pessoa;
import com.degraoEmgrao.repository.PessoaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;

    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    public List<Pessoa> listarTodas() {
        return pessoaRepository.findAll();
    }

    public Optional<Pessoa> buscarPorId(Integer id) {
        return pessoaRepository.findById(id);
    }

    public List<Pessoa> buscarPorPapel(String papel) {
        return pessoaRepository.findByPapel(papel);
    }

    public Pessoa salvar(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }

    public Pessoa atualizar(Integer id, Pessoa dadosNovos) {
        return pessoaRepository.findById(id).map(pessoa -> {
            pessoa.setNome(dadosNovos.getNome());
            pessoa.setEmail(dadosNovos.getEmail());
            pessoa.setTelefone(dadosNovos.getTelefone());
            pessoa.setPapel(dadosNovos.getPapel());
            pessoa.setTipoPessoa(dadosNovos.getTipoPessoa());
            pessoa.setRecorrencia(dadosNovos.getRecorrencia());
            pessoa.setLimiteMensalKg(dadosNovos.getLimiteMensalKg());
            pessoa.setPrazoAtendimento(dadosNovos.getPrazoAtendimento());
            pessoa.setUltimaEntrega(dadosNovos.getUltimaEntrega());
            return pessoaRepository.save(pessoa);
        }).orElseThrow(() -> new RuntimeException("Pessoa não encontrada: " + id));
    }

    public void deletar(Integer id) {
        pessoaRepository.deleteById(id);
    }

    // Login simples por documento
    public Optional<Pessoa> login(String documentoLogin, String senha) {
        return pessoaRepository.findByDocumentoLogin(documentoLogin)
                .filter(p -> p.getSenha().equals(senha));
    }
}