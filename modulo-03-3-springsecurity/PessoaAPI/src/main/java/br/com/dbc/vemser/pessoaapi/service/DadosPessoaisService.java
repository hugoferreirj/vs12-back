package br.com.dbc.vemser.pessoaapi.service;

import br.com.dbc.vemser.pessoaapi.client.DadosPessoaisClient;
import br.com.dbc.vemser.pessoaapi.dto.DadosPessoaisDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DadosPessoaisService {

    private final DadosPessoaisClient client;

    public List<DadosPessoaisDTO> listDadosPessoais() {
        return client.getAll();
    }

    public DadosPessoaisDTO create(
            DadosPessoaisDTO dadosPessoaisDTO) throws Exception {
        return client.post(dadosPessoaisDTO);
    }

    public DadosPessoaisDTO update(String cpf,
                                   DadosPessoaisDTO dto) throws Exception {
        return client.put(cpf, dto);
    }

    public DadosPessoaisDTO getByCpf(String cpf) {
        return client.get(cpf);
    }

    public void delete(String cpf) {
        client.delete(cpf);
    }
}
