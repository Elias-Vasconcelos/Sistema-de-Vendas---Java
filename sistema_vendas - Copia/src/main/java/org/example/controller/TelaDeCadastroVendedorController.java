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
import org.example.model.Vendedor;

import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class TelaDeCadastroVendedorController implements Initializable {

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
    private ChoiceBox<String> CbSexo;

    @FXML
    private TableColumn<Vendedor, LocalDate> ColDataContrato;

    @FXML
    private TableColumn<Vendedor, LocalDate>ColDataNasc;

    @FXML
    private TableColumn<Vendedor, String> ColNome;

    @FXML
    private TableColumn<Vendedor, String> ColSexo;

    @FXML
    private TableColumn<Vendedor, Integer> Colid;

    @FXML
    private DatePicker DtData;

    @FXML
    private DatePicker DtDataContrato;

    @FXML
    private TextField TfIdDeletar;

    @FXML
    private TextField TfNome;


    private List<String> sexos = Arrays.asList("Masculino", "Feminino");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<String> genero = FXCollections.observableList(sexos);

        CbSexo.setItems(genero);

    }
}
