package com.vendas.vendas.dao;

import com.vendas.vendas.config.ConexaoSQlite;
import com.vendas.vendas.model.Venda;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class VendaDAO {

    static Connection con;

    static {
        try {
            con = new ConexaoSQlite().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public VendaDAO() throws SQLException {
        this.con = new ConexaoSQlite().getConnection();
    }

    public ArrayList<Venda> listar() throws SQLException {
        String sql = "SELECT * FROM venda;";
        ArrayList<Venda> lista = new ArrayList<>();

        ResultSet rs = con.prepareStatement(sql).executeQuery();

        while(rs.next()){

            Integer cliente = (rs.getInt("CLIENTEID"));

            Integer cidade = (rs.getInt("CIDADEID"));

            Integer periodo = (rs.getInt("PERIODOID"));

            Integer vendedor = (rs.getInt("VENDEDORID"));

            Integer loja = (rs.getInt("LOJAID"));

            lista.add(new Venda(
                    rs.getInt("VENDAID"),
                    cliente,
                    cidade,
                    periodo,
                    vendedor,
                    loja,
                    rs.getObject("DATA_VENDA", LocalDate.class),
                    rs.getDouble("HORA_VENDA"),
                    rs.getString("TOTAL_VENDA"),
                    rs.getString("METODO_PAGAMENTO")
            ));
        }
        return lista;
    }
}