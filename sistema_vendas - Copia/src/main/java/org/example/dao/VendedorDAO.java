package org.example.dao;

import org.example.config.ConexaoSQlite;
import org.example.model.Vendedor;

import java.sql.*;
import java.util.ArrayList;

public class VendedorDAO {

    private Connection con;

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