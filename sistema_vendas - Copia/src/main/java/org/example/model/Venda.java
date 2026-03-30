package org.example.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Venda {
    private Integer VENDAID;
    private Integer CLIENTE;
    private Integer CIDADE;
    private Integer PERIODO;
    private Integer VENDEDOR;
    private Integer LOJA;
    private LocalDate DATA_VENDA;
    private Double TOTAL_VENDA;
    private String HORA_VENDA;
    private String METODO_PAGAMENTO;
}
