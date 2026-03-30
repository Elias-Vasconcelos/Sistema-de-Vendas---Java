package com.vendas.vendas.dao;

import com.vendas.vendas.config.ConexaoSQlite;
import com.vendas.vendas.model.Produto;
import com.vendas.vendas.model.Venda;
import com.vendas.vendas.model.VendaProduto;

import java.sql.*;
import java.util.ArrayList;

public class VendaProdutoDAO {

    static Connection con;

    static {
        try {
            con = new ConexaoSQlite().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public VendaProdutoDAO() throws SQLException {
        this.con = new ConexaoSQlite().getConnection();
    }

    public ArrayList<VendaProduto> listar() throws SQLException {
        String sql = "SELECT * FROM vendaproduto;";
        ArrayList<VendaProduto> lista = new ArrayList<>();

        ResultSet rs = con.prepareStatement(sql).executeQuery();

        while(rs.next()){

            Venda venda = new Venda();
            venda.setVENDAID(rs.getInt("VENDAID"));

            Produto produto = new Produto();
            produto.setPRODUTOID(rs.getInt("PRODUTOID"));

            lista.add(new VendaProduto(
                    rs.getInt("VENDAPRODUTOID"),
                    venda,
                    produto,
                    rs.getInt("QUANTIDADE"),
                    rs.getDouble("PRECO_UNIDADE"),
                    rs.getDouble("DESCONTO")
            ));
        }
        return lista;
    }
}