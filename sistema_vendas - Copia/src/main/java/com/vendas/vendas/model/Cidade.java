package com.vendas.vendas.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cidade {
    private Integer CIDADEID;
    private String NOME;
    private Estado ESTADO;
}
