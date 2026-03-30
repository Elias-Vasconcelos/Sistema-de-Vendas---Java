package org.example.dao;

import org.example.config.ConexaoSQlite;
import org.example.model.*;

import java.sql.*;
import java.util.ArrayList;

public class CidadeDAO {

    private Connection con;

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