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
import org.example.model.Cliente;

import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class TelaDeCadastroPessoaController implements Initializable {

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
    private ChoiceBox<String> CbCredito;

    @FXML
    private ChoiceBox<String> CbSexo;

    @FXML
    private TableColumn<Cliente, String> ColCredito;

    @FXML
    private TableColumn<Cliente, LocalDate> ColDataCadas;

    @FXML
    private TableColumn<Cliente, LocalDate>  ColDataNasc;

    @FXML
    private TableColumn<Cliente, String> ColEmail;

    @FXML
    private TableColumn<Cliente, String> ColNome;

    @FXML
    private TableColumn<Cliente, String> ColSexo;

    @FXML
    private TableColumn<Cliente, Integer> Colid;

    @FXML
    private DatePicker DtData;

    @FXML
    private TextField TfEmail;

    @FXML
    private TextField TfIdDeletar;

    @FXML
    private TextField TfNome;

    private List<String> sexos = Arrays.asList("Masculino", "Feminino");
    private List<String> credito = Arrays.asList("Positivo", "Negativado");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> genero = FXCollections.observableList(sexos);
        ObservableList<String> sCredito = FXCollections.observableList(credito);

        CbSexo.setItems(genero);
        CbCredito.setItems(sCredito);

    }


}
