package org.example.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Periodo {
    private  Integer PERIODOID;
    private LocalDate DATA;
    private String DIA_SEMANA;
    private String MES;
    private Integer ANO;
}
