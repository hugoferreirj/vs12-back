package br.com.dbc.vemser.pessoaapi.entity.pk;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class ProfessorPK implements Serializable {
//    @Column(name="id_professor")
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PESSOA_SEQ")
//    @SequenceGenerator(name = "PESSOA_SEQ", sequenceName = "seq_pessoa2", allocationSize = 1)
//    private Integer id;

    @Column(name="id_professor")
    private Integer idProfessor;

    @Column(name="id_universidade")
    private Integer idUniversidade;
}
