package org.example.dao;

import org.example.config.ConexaoSQlite;
import org.example.model.Categoria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoriaDAO {
    private Connection con;

    public CategoriaDAO() throws SQLException {
        this.con = new ConexaoSQlite().getConnection();
    }

    public ArrayList<Categoria> listar() throws SQLException {
        String sql = "SELECT * FROM categoria;";
        ArrayList<Categoria> lista = new ArrayList<>();

        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Integer id = rs.getInt("CATEGORIAID");
            String nome = rs.getString("NOME");
            String descricao = rs.getString("DESCRICAO");

            lista.add(new Categoria(id, nome, descricao));
        }

        return lista;
    }

    public Categoria buscarPorId(Integer id) throws SQLException {
        String sql = "SELECT * FROM categoria WHERE CATEGORIAID = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return new Categoria(
                    id,
                    rs.getString("NOME"),
                    rs.getString("DESCRICAO")
            );
        }
        return null;
    }

    public boolean inserir(Categoria categoria) throws SQLException {
        String sql = "INSERT INTO categoria (NOME, DESCRICAO) VALUES (?, ?)";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, categoria.getNOME());
        ps.setString(2, categoria.getDESCRICAO());

        return ps.executeUpdate() > 0;
    }

    public boolean atualizar(Categoria categoria) throws SQLException {
        String sql = "UPDATE categoria SET NOME=?, DESCRICAO=? WHERE CATEGORIAID=?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, categoria.getNOME());
        ps.setString(2, categoria.getDESCRICAO());
        ps.setInt(3, categoria.getCATEGORIAID());

        return ps.executeUpdate() > 0;
    }

    public boolean remover(Integer id) throws SQLException {
        String sql = "DELETE FROM categoria WHERE CATEGORIAID=?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);

        return ps.executeUpdate() > 0;
    }







}
