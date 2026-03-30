package org.example.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import org.example.model.VendaProduto;

import java.net.URL;
import java.util.ResourceBundle;

public class TelaDeCadastroVendaProdutoController implements Initializable {

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
    private TableColumn<VendaProduto, String> ColDesconto;

    @FXML
    private TableColumn<VendaProduto, String> ColPreco;

    @FXML
    private TableColumn<VendaProduto, String> ColProduto;

    @FXML
    private TableColumn<VendaProduto, String> ColQuantidade;

    @FXML
    private TableColumn<VendaProduto, String> ColVenda;

    @FXML
    private TableColumn<VendaProduto, Integer> Colid;

    @FXML
    private TextField TfDesconto;

    @FXML
    private TextField TfPreco;

    @FXML
    private TextField TfProdutoID;

    @FXML
    private TextField TfQuantidade;

    @FXML
    private TextField TfVendaID;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
