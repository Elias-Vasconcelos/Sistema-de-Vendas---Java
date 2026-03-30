package com.vendas.vendas.dao;

import com.vendas.vendas.config.ConexaoSQlite;
import com.vendas.vendas.model.Vendedor;

import java.sql.*;
import java.util.ArrayList;

public class VendedorDAO {

    static Connection con;

    static {
        try {
            con = new ConexaoSQlite().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public VendedorDAO() throws SQLException {
        this.con = new ConexaoSQlite().getConnection();
    }

    public ArrayList<Vendedor> listar() throws SQLException {
        String sql = "SELECT * FROM vendedor;";
        ArrayList<Vendedor> lista = new ArrayList<>();

        ResultSet rs = con.prepareStatement(sql).executeQuery();

        while(rs.next()){
            lista.add(new Vendedor(
                    rs.getInt("VENDEDORID"),
                    rs.getString("NOME"),
                    rs.getString("SEXO"),
                    rs.getString("DATA_NASCIMENTO"),
                    rs.getString("DATA_CONTRATACAO")
            ));
        }
        return lista;
    }
}