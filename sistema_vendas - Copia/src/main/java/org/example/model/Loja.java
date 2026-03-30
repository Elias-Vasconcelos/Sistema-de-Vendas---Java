package org.example.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Loja {
    private Integer LOJAID;
    private String  NOME;
    private String  TELEFONE;
}
