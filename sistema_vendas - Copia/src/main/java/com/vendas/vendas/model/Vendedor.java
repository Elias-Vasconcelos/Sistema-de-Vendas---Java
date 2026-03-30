package com.vendas.vendas.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vendedor {
    private Integer VENDEDORID;
    private String NOME;
    private String SEXO;
    private String DATA_NASCIMENTO;
    private String DATA_CONTRATACAO;
}
