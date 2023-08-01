package br.com.dbc.vemser.pessoaapi.service;

import br.com.dbc.vemser.pessoaapi.entity.Contato;
import br.com.dbc.vemser.pessoaapi.entity.Pessoa;
import br.com.dbc.vemser.pessoaapi.repository.ContatoRepository;
import br.com.dbc.vemser.pessoaapi.repository.PessoaRepository;

import java.util.List;

public class ContatoService {

    private ContatoRepository contatoRepository;

    public ContatoService() {
        contatoRepository = new ContatoRepository();
    }

    public Contato create(Contato contato) {
        return contatoRepository.create(contato);
    }

    public List<Contato> list() {
        return contatoRepository.list();
    }

    public Contato update(Integer id,
                          Contato contatoAtualizar) throws Exception {
        Contato contatoRecuperado = getContato(id);

        contatoRecuperado.setTipoContato(contatoAtualizar.getTipoContato());
        contatoRecuperado.setIdPessoa(contatoAtualizar.getIdPessoa());
        contatoRecuperado.setDescricao(contatoAtualizar.getDescricao());
        contatoRecuperado.setNumero(contatoAtualizar.getNumero());

        return contatoRecuperado;
    }

    public void delete(Integer id) throws Exception {
        Contato contatoRecuperado = getContato(id);
        contatoRepository.delete(contatoRecuperado);
    }

    public List<Contato> listByPessoa(Integer idPessoa) {
        return contatoRepository.listByPessoa(idPessoa);
    }

    private Contato getContato(Integer id) throws Exception {
        Contato contatoRecuperado = contatoRepository.list().stream()
                .filter(contato -> contato.getIdContato().equals(id))
                .findFirst()
                .orElseThrow(() -> new Exception("Contato não encontrado!"));
        return contatoRecuperado;
    }
}
