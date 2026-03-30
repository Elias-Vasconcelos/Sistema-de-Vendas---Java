package org.example.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import org.example.model.Categoria;

import java.net.URL;
import java.util.ResourceBundle;

public class TelaDeCadastroCategoriaController implements Initializable {

    @FXML
    private Button BtnApagar;

    @FXML
    private Button BtnAtualizar;

    @FXML
    private Button BtnCadastrar;

    @FXML
    private HBox BtnCategoria;

    @FXML
    private HBox BtnCidade;

    @FXML
    private HBox BtnCliente;

    @FXML
    private Button BtnDeletar;

    @FXML
    private HBox BtnEstado;

    @FXML
    private Label BtnHome;

    @FXML
    private HBox BtnLoja;

    @FXML
    private HBox BtnPeriodo;

    @FXML
    private HBox BtnProduto;

    @FXML
    private HBox BtnVenda;

    @FXML
    private HBox BtnVendaProduto;

    @FXML
    private HBox BtnVendedor;

    @FXML
    private TableColumn<Categoria, String> ColDescricao;

    @FXML
    private TableColumn<Categoria, String> ColNome;

    @FXML
    private TableColumn<Categoria, Integer> Colid;

    @FXML
    private TextField TfDescricao;

    @FXML
    private TextField TfIdDeletar;

    @FXML
    private TextField TfNome;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
