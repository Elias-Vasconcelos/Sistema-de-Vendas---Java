package org.example.config;

import java.sql.*;

public class ConexaoSQlite {
    private Connection connection;


    public ConexaoSQlite() throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:sqlite:C:\\sistema_vendas\\src\\main\\resources\\sistemaDeVendas.db");;
    }


    public  Connection getConnection(){
        return connection;
    }



    }

