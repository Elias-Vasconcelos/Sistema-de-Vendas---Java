package org.example.dao;

import org.example.config.ConexaoSQlite;
import org.example.model.*;

import java.sql.*;
import java.util.ArrayList;

public class VendaProdutoDAO {

    private Connection con;

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