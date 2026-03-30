package org.example.dao;

import org.example.config.ConexaoSQlite;
import org.example.model.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ClienteDAO {

    private static Connection con;


    public ClienteDAO() throws SQLException {
        this.con = new ConexaoSQlite().getConnection();
    }


    public static ArrayList<Cliente> listar()throws SQLException {
        String sql = "SELECT * FROM cliente;";
        ArrayList<Cliente> clientes = new ArrayList<>();

        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            Integer id = rs.getInt("CLIENTEID");
            String nome_completo = rs.getString("NOME");
            String email = rs.getString("EMAIL");
            String data = rs.getString("DATA_DE_NACIMENTO");
            LocalDate dataNascimento = LocalDate.parse(data);
            String sexo = rs.getString("SEXO");
            String statusDeCredito = rs.getString("STATUS_DE_CREDITO");
            LocalDate dataDeCadastro = rs.getObject("DATA_DE_CADASTRO", LocalDate.class);
            clientes.add(new Cliente(id,nome_completo, email, dataNascimento, sexo, statusDeCredito, dataDeCadastro ));

        }
    return clientes;

    }

    public Cliente buscarPorId (Integer id) throws SQLException {
        String sql = "SELECT * FROM cliente WHERE CLIENTEID =  ?;";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            String nome_completo = rs.getString("NOME");
            String email = rs.getString("EMAIL");
            String data = rs.getString("DATA_DE_NACIMENTO");
            LocalDate dataNascimento = LocalDate.parse(data);
            String sexo = rs.getString("SEXO");
            String statusDeCredito = rs.getString("STATUS_DE_CREDITO");
            LocalDate dataDeCadastro = rs.getObject("DATA_DE_CADASTRO", LocalDate.class);


            System.out.println("O nome da/do Clinte é: " + nome_completo );
           return new Cliente(id,nome_completo, email, dataNascimento, sexo, statusDeCredito, dataDeCadastro);
        }else {
            System.out.println("Clinte nao encontrado!");
            return null;
        }



    }

    public ArrayList<Cliente> buscarPorNome(String nome) throws SQLException {
        String sql = "SELECT * FROM cliente WHERE NOME LIKE  ?;";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, "%" + nome + "%");
        ResultSet rs = ps.executeQuery();
        ArrayList<Cliente> clientes = new ArrayList<>();

        while(rs.next()){
            Integer id = rs.getInt("CLIENTEID");
            String nome_completo = rs.getString("NOME");
            String email = rs.getString("EMAIL");
            String data = rs.getString("DATA_DE_NACIMENTO");
            LocalDate dataNascimento = LocalDate.parse(data);
            String sexo = rs.getString("SEXO");
            String statusDeCredito = rs.getString("STATUS_DE_CREDITO");
            LocalDate dataDeCadastro = rs.getObject("DATA_DE_CADASTRO", LocalDate.class);


            System.out.println("O nome da/do Clinte é: " + nome_completo );
            clientes.add(new Cliente(id,nome_completo, email, dataNascimento, sexo, statusDeCredito, dataDeCadastro ));
        }
        return clientes;
    }

    public Boolean atualizar(Cliente cliente) throws SQLException{
        String sql = "UPDATE cliente SET NOME = ?,EMAIL = ?, DATA_DE_NACIMENTO = ?, SEXO = ?, STATUS_DE_CREDITO = ?, DATA_DE_CADASTRO = ? WHERE CLIENTEID = ?  ;";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, cliente.getNOME());
        ps.setString(2 , cliente.getEMAIL());
        ps.setString( 3, cliente.getDATA_DE_NACIMENTO().toString());
        ps.setString(4, cliente.getSEXO() );
        ps.setString(5, cliente.getSTATUS_DE_CREDITO());
        ps.setString(6, String.valueOf(cliente.getDATA_DE_CADASTRO()));
        ps.setInt(7, cliente.getCLIENTEID());


        int foiAtualizado = ps.executeUpdate();

        if (foiAtualizado >= 1){
            return true;
        }else {
            return false;

        }

    }

    public Boolean remover(Integer id) throws SQLException {
        String sql = "DELETE FROM cliente  WHERE CLIENTEID = ?  ;";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);


        int foiExcluido = ps.executeUpdate();

        if (foiExcluido >= 1){
            return true;
        }else {
            return false;

        }

    }


}
