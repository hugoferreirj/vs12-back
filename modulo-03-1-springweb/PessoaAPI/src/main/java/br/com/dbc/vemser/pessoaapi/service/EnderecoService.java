package br.com.dbc.vemser.pessoaapi.service;

import br.com.dbc.vemser.pessoaapi.dto.EnderecoCreateDTO;
import br.com.dbc.vemser.pessoaapi.dto.EnderecoDTO;
import br.com.dbc.vemser.pessoaapi.entity.Endereco;
import br.com.dbc.vemser.pessoaapi.entity.Pessoa;
import br.com.dbc.vemser.pessoaapi.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.pessoaapi.repository.EnderecoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Data
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final PessoaService pessoaService;
    private final ObjectMapper objectMapper;
    private final EmailService emailService;


    public EnderecoDTO create(Integer idPessoa, EnderecoCreateDTO endereco) throws RegraDeNegocioException {
        Pessoa pessoa = pessoaService.getPessoa(idPessoa);
        if (pessoa != null) {
            Endereco entity = objectMapper.convertValue(endereco, Endereco.class);
            entity.setIdPessoa(idPessoa);


            try {
                emailService.enviarEmailSobreEnderecoUtilizandoTemplate(pessoa, "Um endereço foi criado e associado a sua conta!");
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }

            Endereco enderecoCriado = enderecoRepository.create(entity);
            EnderecoDTO enderecoDTO = objectMapper.convertValue(enderecoCriado, EnderecoDTO.class);
//            EnderecoDTO enderecoDTO = new EnderecoDTO();
//            enderecoDTO.setIdEndereco(enderecoCriado.getIdEndereco());
//            enderecoDTO.setCep(enderecoCriado.getCep());
//            enderecoDTO.setCidade(enderecoCriado.getCidade());
//            enderecoDTO.setEstado(enderecoCriado.getEstado());
//            enderecoDTO.setComplemento(enderecoCriado.getComplemento());
//            enderecoDTO.setNumero(enderecoCriado.getNumero());
//            enderecoDTO.setPais(enderecoCriado.getPais());
//            enderecoDTO.setLogradouro(enderecoCriado.getLogradouro());
//            enderecoDTO.setTipo(enderecoCriado.getTipo());
//            enderecoDTO.setIdPessoa(enderecoCriado.getIdPessoa());

            return enderecoDTO;
        } else {
            return null;
        }
    }

    public List<EnderecoDTO> list() {
        return enderecoRepository.list().stream()
                .map(endereco -> objectMapper.convertValue(endereco, EnderecoDTO.class))
                .collect(Collectors.toList());
    }

    public EnderecoDTO update(Integer idEndereco, EnderecoDTO enderecoAtualizar) throws RegraDeNegocioException {
        enderecoAtualizar.setIdEndereco(idEndereco);
        Endereco entity = getEndereco(enderecoAtualizar.getIdEndereco());
        if (entity != null) {
            entity.setTipo(enderecoAtualizar.getTipo());
            entity.setIdPessoa(enderecoAtualizar.getIdPessoa());
            entity.setLogradouro(enderecoAtualizar.getLogradouro());
            entity.setNumero(enderecoAtualizar.getNumero());
            entity.setComplemento(enderecoAtualizar.getComplemento());
            entity.setCep(enderecoAtualizar.getCep());
            entity.setCidade(enderecoAtualizar.getCidade());
            entity.setEstado(enderecoAtualizar.getEstado());
            entity.setPais(enderecoAtualizar.getPais());

            Pessoa pessoa = pessoaService.getPessoa(entity.getIdPessoa());

            try {
                emailService.enviarEmailSobreEnderecoUtilizandoTemplate(pessoa, "O endereço " + idEndereco + " associado à sua conta foi alterado!");
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }
        return enderecoAtualizar;
    }

    public void delete(Integer id) throws RegraDeNegocioException {
        Endereco enderecoRecuperado = getEndereco(id);
        if (enderecoRecuperado != null) {
            enderecoRepository.delete(enderecoRecuperado);
            Pessoa pessoa = pessoaService.getPessoa(enderecoRecuperado.getIdPessoa());
            try {
                emailService.enviarEmailSobreEnderecoUtilizandoTemplate(pessoa, "O endereço " + id + " associado à sua conta foi excluido!");
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public List<EnderecoDTO> listByPessoa(Integer idPessoa) {
        return enderecoRepository.listByPessoa(idPessoa).stream()
                .map(endereco -> objectMapper.convertValue(endereco, EnderecoDTO.class))
                .collect(Collectors.toList());
    }

    public List<EnderecoDTO> listByEndereco(Integer idPessoa) {
        return enderecoRepository.listByEndereco(idPessoa).stream()
                .map(endereco -> objectMapper.convertValue(endereco, EnderecoDTO.class))
                .collect(Collectors.toList());
    }

    private Endereco getEndereco(Integer id) throws RegraDeNegocioException {
        Endereco enderecoRecuperado = enderecoRepository.list().stream()
                .filter(endereco -> endereco.getIdEndereco().equals(id))
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Contato não encontrado!"));
        return enderecoRecuperado;
    }
}
