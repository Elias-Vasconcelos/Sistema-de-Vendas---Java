package org.example.dao;

import org.example.config.ConexaoSQlite;
import org.example.model.Loja;

import java.sql.*;
import java.util.ArrayList;

public class LojaDAO {

    private Connection con;

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