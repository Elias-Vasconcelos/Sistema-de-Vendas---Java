package com.vendas.vendas.dao;

import com.vendas.vendas.config.ConexaoSQlite;
import com.vendas.vendas.model.Periodo;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class PeriodoDAO {

    static Connection con;

    static {
        try {
            con = new ConexaoSQlite().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

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