package com.vendas.vendas.dao;

import com.vendas.vendas.config.ConexaoSQlite;
import com.vendas.vendas.model.Cidade;
import com.vendas.vendas.model.Estado;

import java.sql.*;
import java.util.ArrayList;

public class CidadeDAO {

    static Connection con;

    static {
        try {
            con = new ConexaoSQlite().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public CidadeDAO() throws SQLException {
        this.con = new ConexaoSQlite().getConnection();
    }

    public ArrayList<Cidade> listar() throws SQLException {
        String sql = "SELECT * FROM cidade;";
        ArrayList<Cidade> lista = new ArrayList<>();

        ResultSet rs = con.prepareStatement(sql).executeQuery();

        while(rs.next()){
            Estado estado = new Estado();
            estado.setESTADOID(rs.getInt("ESTADO_ID"));

            lista.add(new Cidade(
                    rs.getInt("CIDADEID"),
                    rs.getString("NOME"),
                    estado
            ));
        }
        return lista;
    }
}