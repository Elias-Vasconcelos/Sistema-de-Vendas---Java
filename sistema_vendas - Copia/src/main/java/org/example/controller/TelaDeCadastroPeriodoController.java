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
import org.example.model.Periodo;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class TelaDeCadastroPeriodoController implements Initializable {

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
    private ChoiceBox<String> CbAno;

    @FXML
    private ChoiceBox<String> CbMes;

    @FXML
    private ChoiceBox<String> CbSemana;

    @FXML
    private TableColumn<Periodo, String> ColAno;

    @FXML
    private TableColumn<Periodo, LocalDate> ColData;

    @FXML
    private TableColumn<Periodo, String> ColMes;

    @FXML
    private TableColumn<Periodo, String>ColSemana;

    @FXML
    private TableColumn<Periodo, Integer>Colid;

    @FXML
    private DatePicker DtData;

    @FXML
    private TextField TfIdDeletar;

    @FXML
    private TextField TfNome;


    private List<String> diasSemana = Arrays.asList(
            "Segunda-feira", "Terça-feira", "Quarta-feira",
            "Quinta-feira", "Sexta-feira", "Sábado", "Domingo"
    );

    private List<String> meses = Arrays.asList(
            "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
            "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"
    );

    private List<String> datas = new ArrayList<>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (int i = 1990, j = 0; i <= 2030; i++, j++) {
            datas.add(String.valueOf(i));
        }

        ObservableList<String> sSemana = FXCollections.observableList(diasSemana);
        ObservableList<String> sMes = FXCollections.observableList(meses);
        ObservableList<String> sAno = FXCollections.observableList(datas);

        CbAno.setItems(sAno);
        CbMes.setItems(sMes);
        CbSemana.setItems(sSemana);



    }
}
