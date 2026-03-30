package org.example.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Categoria {
    private Integer  CATEGORIAID;
    private String  NOME;
    private String  DESCRICAO;
}
