package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import org.example.model.Produto;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class TelaDeCadastroProdutoController implements Initializable {

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
    private ChoiceBox<String> CbCategoria;

    @FXML
    private TableColumn<Produto, String> ColCategoria;

    @FXML
    private TableColumn<Produto, Double>  ColCusto;

    @FXML
    private TableColumn<Produto, String> ColDescricao;

    @FXML
    private TableColumn<Produto, String> ColNome;

    @FXML
    private TableColumn<Produto, Double> ColVenda;

    @FXML
    private TableColumn<Produto, Integer> Colid;

    @FXML
    private TextField TfCusto;

    @FXML
    private TextField TfDescricao;

    @FXML
    private TextField TfIdDeletar;

    @FXML
    private TextField TfNome;

    @FXML
    private TextField TfVenda;



    private List<String> categorias = Arrays.asList(
            "Alimentos e Bebidas",
            "Eletrônicos",
            "Vestuário",
            "Casa e Decoração",
            "Saúde e Beleza",
            "Brinquedos e Jogos",
            "Esporte e Lazer",
            "Automotivo",
            "Papelaria",
            "Pet Shop"
    );


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> sCategoria = FXCollections.observableList(categorias);
        CbCategoria.setItems(sCategoria);
    }
}
