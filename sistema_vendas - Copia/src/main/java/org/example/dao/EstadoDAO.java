package org.example.dao;

import org.example.config.ConexaoSQlite;
import org.example.model.Estado;

import java.sql.*;
import java.util.ArrayList;

public class EstadoDAO {

    private Connection con;

    public EstadoDAO() throws SQLException {
        this.con = new ConexaoSQlite().getConnection();
    }

    public ArrayList<Estado> listar() throws SQLException {
        String sql = "SELECT * FROM estado;";
        ArrayList<Estado> lista = new ArrayList<>();

        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            lista.add(new Estado(
                    rs.getInt("ESTADOID"),
                    rs.getString("NOME")
            ));
        }
        return lista;
    }

    public boolean inserir(Estado estado) throws SQLException {
        String sql = "INSERT INTO estado (NOME) VALUES (?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, estado.getNOME());
        return ps.executeUpdate() > 0;
    }
}