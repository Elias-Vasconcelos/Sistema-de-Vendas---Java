package org.example.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class VendaProduto {
    private Integer VENDAPRODUTOID;
    private Venda VENDA;
    private Produto PRODUTO;
    private Integer QUANTIDADE;
    private Double PRECO_UNIDADE;
    private Double DESCONTO;
}
