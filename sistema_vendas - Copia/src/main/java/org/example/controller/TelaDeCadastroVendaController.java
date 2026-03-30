package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import org.example.model.Venda;

import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class TelaDeCadastroVendaController implements Initializable {

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
    private ChoiceBox<String> CbMetPagamento;

    @FXML
    private TableColumn<Venda, String> ColCidade;

    @FXML
    private TableColumn<Venda, String> ColCliente;

    @FXML
    private TableColumn<Venda, LocalDate> ColData;

    @FXML
    private TableColumn<Venda, String> ColHora;

    @FXML
    private TableColumn<Venda, String> ColLoja;

    @FXML
    private TableColumn<Venda, String> ColPagamento;

    @FXML
    private TableColumn<Venda, String> ColPeriodo;

    @FXML
    private TableColumn<Venda, String> ColTotal;

    @FXML
    private TableColumn<Venda, String> ColVendedor;

    @FXML
    private TableColumn<Venda, Integer> Colid;

    @FXML
    private DatePicker DataVenda;

    @FXML
    private TextField TfCidadeID;

    @FXML
    private TextField TfClienteID;

    @FXML
    private TextField TfHora;

    @FXML
    private TextField TfLojaID;

    @FXML
    private TextField TfPeriodoID;

    @FXML
    private TextField TfTotal;

    @FXML
    private TextField TfVendedorID;

    private List<String> metodosPagamento = Arrays.asList(
            "Dinheiro",
            "Cartão de Crédito",
            "Cartão de Débito",
            "PIX",
            "Boleto Bancário",
            "Transferência Bancária",
            "Vale Refeição",
            "Crediário"
    );


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> sPagamento = FXCollections.observableList(metodosPagamento);
        CbMetPagamento.setItems(sPagamento);

    }
}
