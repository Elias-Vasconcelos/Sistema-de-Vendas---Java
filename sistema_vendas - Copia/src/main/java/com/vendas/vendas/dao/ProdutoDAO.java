package com.vendas.vendas.dao;

import com.vendas.vendas.config.ConexaoSQlite;
import com.vendas.vendas.model.Categoria;
import com.vendas.vendas.model.Produto;

import java.sql.*;
import java.util.ArrayList;

public class ProdutoDAO {

    static Connection con;

    static {
        try {
            con = new ConexaoSQlite().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ProdutoDAO() throws SQLException {
        this.con = new ConexaoSQlite().getConnection();
    }

    public ArrayList<Produto> listar() throws SQLException {
        String sql = "SELECT * FROM produto;";
        ArrayList<Produto> lista = new ArrayList<>();

        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            Categoria categoria = new Categoria();
            categoria.setCATEGORIAID(rs.getInt("CATEGORIA"));

            Produto p = new Produto(
                    rs.getInt("PRODUTOID"),
                    rs.getString("NOME"),
                    rs.getString("DESCRICAO"),
                    categoria,
                    rs.getDouble("VALOR_DE_CUSTO"),
                    rs.getDouble("VALOR_DE_VENDA")
            );

            lista.add(p);
        }

        return lista;
    }

    public boolean inserir(Produto produto) throws SQLException {
        String sql = "INSERT INTO produto (NOME, DESCRICAO, CATEGORIA, VALOR_DE_CUSTO, VALOR_DE_VENDA) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, produto.getNOME());
        ps.setString(2, produto.getDESCRICAO());
        ps.setInt(3, produto.getCATEGORIA().getCATEGORIAID());
        ps.setDouble(4, produto.getVALOR_DE_CUSTO());
        ps.setDouble(5, produto.getVALOR_DE_VENDA());

        return ps.executeUpdate() > 0;
    }
}