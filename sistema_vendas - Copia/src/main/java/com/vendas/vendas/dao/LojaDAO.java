package com.vendas.vendas.dao;

import com.vendas.vendas.config.ConexaoSQlite;
import com.vendas.vendas.model.Loja;

import java.sql.*;
import java.util.ArrayList;

public class LojaDAO {

    static Connection con;

    static {
        try {
            con = new ConexaoSQlite().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public LojaDAO() throws SQLException {
        this.con = new ConexaoSQlite().getConnection();
    }

    public ArrayList<Loja> listar() throws SQLException {
        String sql = "SELECT * FROM loja;";
        ArrayList<Loja> lista = new ArrayList<>();

        ResultSet rs = con.prepareStatement(sql).executeQuery();

        while(rs.next()){
            lista.add(new Loja(
                    rs.getInt("LOJAID"),
                    rs.getString("NOME"),
                    rs.getString("TELEFONE")
            ));
        }
        return lista;
    }
}