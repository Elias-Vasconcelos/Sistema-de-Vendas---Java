package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {
    private Integer CLIENTEID;
    private String NOME;
    private String EMAIL;
    private LocalDate DATA_DE_NACIMENTO;
    private String SEXO;
    private String STATUS_DE_CREDITO;
    private LocalDate DATA_DE_CADASTRO;

}
