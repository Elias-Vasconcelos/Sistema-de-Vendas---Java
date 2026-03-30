package org.example.dao;

import org.example.config.ConexaoSQlite;
import org.example.model.Periodo;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class PeriodoDAO {

    private Connection con;

    public PeriodoDAO() throws SQLException {
        this.con = new ConexaoSQlite().getConnection();
    }

    public ArrayList<Periodo> listar() throws SQLException {
        String sql = "SELECT * FROM periodo;";
        ArrayList<Periodo> lista = new ArrayList<>();

        ResultSet rs = con.prepareStatement(sql).executeQuery();

        while(rs.next()){
            lista.add(new Periodo(
                    rs.getInt("PERIODOID"),
                    rs.getObject("DATA", LocalDate.class),
                    rs.getString("DIA_SEMANA"),
                    rs.getString("MES"),
                    rs.getInt("ANO")
            ));
        }
        return lista;
    }
}