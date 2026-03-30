package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Produto {
    private Integer PRODUTOID;
    private String NOME;
    private String DESCRICAO;
    private Categoria CATEGORIA;
    private Double VALOR_DE_CUSTO;
    private Double VALOR_DE_VENDA;
}
