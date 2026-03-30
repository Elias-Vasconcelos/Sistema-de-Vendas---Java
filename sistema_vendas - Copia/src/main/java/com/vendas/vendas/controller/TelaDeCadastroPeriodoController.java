package com.vendas.vendas.controller;

import com.vendas.vendas.dao.PeriodoDAO;
import com.vendas.vendas.model.Periodo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class TelaDeCadastroPeriodoController implements Initializable {

    @FXML private Button BtnApagar, BtnAtualizar, BtnCadastrar, BtnDeletar;
    @FXML private HBox BtnCategoria, BtnCidade, BtnCliente, BtnEstado, BtnLoja, BtnPeriodo, BtnProduto, BtnVenda, BtnVendaProduto, BtnVendedor;
    @FXML private Label BtnHome;
    @FXML private ChoiceBox<String> CbAno, CbMes, CbSemana;
    @FXML private TableColumn<Periodo, Integer> Colid;
    @FXML private TableColumn<Periodo, LocalDate> ColData;
    @FXML private TableColumn<Periodo, String> ColAno, ColMes, ColSemana;
    @FXML private TableView<Periodo> tableView;
    @FXML private DatePicker DtData;
    @FXML private TextField TfIdDeletar, TfNome;

    private Integer idSelecionado = null;
    private List<String> diasSemana = Arrays.asList("Segunda-feira","Terça-feira","Quarta-feira","Quinta-feira","Sexta-feira","Sábado","Domingo");
    private List<String> meses = Arrays.asList("Janeiro","Fevereiro","Março","Abril","Maio","Junho","Julho","Agosto","Setembro","Outubro","Novembro","Dezembro");
    private List<String> datas = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        for (int i = 1990; i <= 2030; i++) datas.add(String.valueOf(i));
        CbAno.setItems(FXCollections.observableList(datas));
        CbMes.setItems(FXCollections.observableList(meses));
        CbSemana.setItems(FXCollections.observableList(diasSemana));

        Colid.setCellValueFactory(d -> new javafx.beans.property.SimpleObjectProperty<>(d.getValue().getPERIODOID()));
        ColData.setCellValueFactory(d -> new javafx.beans.property.SimpleObjectProperty<>(d.getValue().getDATA()));
        ColSemana.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getDIA_SEMANA()));
        ColMes.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getMES()));
        ColAno.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(String.valueOf(d.getValue().getANO())));

        carregarTabela();
        tableView.setOnMouseClicked(e -> selecionarLinha());
    }

    private void carregarTabela() {
        try { tableView.setItems(FXCollections.observableArrayList(new PeriodoDAO().listar())); }
        catch (SQLException e) { e.printStackTrace(); }
    }

    private void selecionarLinha() {
        Periodo p = tableView.getSelectionModel().getSelectedItem();
        if (p != null) {
            idSelecionado = p.getPERIODOID();
            DtData.setValue(p.getDATA());
            CbSemana.setValue(p.getDIA_SEMANA());
            CbMes.setValue(p.getMES());
            CbAno.setValue(String.valueOf(p.getANO()));
            TfIdDeletar.setText(String.valueOf(p.getPERIODOID()));
        }
    }

    @FXML
    private void cadastrar() {
        try {
            String sql = "INSERT INTO periodo (DATA, DIA_SEMANA, MES, ANO) VALUES (?,?,?,?)";
            java.sql.Connection con = new com.vendas.vendas.config.ConexaoSQlite().getConnection();
            java.sql.PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, DtData.getValue() != null ? DtData.getValue().toString() : "");
            ps.setString(2, CbSemana.getValue()); ps.setString(3, CbMes.getValue());
            ps.setString(4, CbAno.getValue());
            if (ps.executeUpdate() > 0) { mostrarAlerta("Sucesso", "Período cadastrado!", Alert.AlertType.INFORMATION); limparFormulario(); carregarTabela(); }
        } catch (Exception e) { mostrarAlerta("Erro", e.getMessage(), Alert.AlertType.ERROR); }
    }

    @FXML
    private void atualizar() {
        if (idSelecionado == null) { mostrarAlerta("Atenção", "Selecione uma linha da tabela.", Alert.AlertType.WARNING); return; }
        try {
            String sql = "UPDATE periodo SET DATA=?, DIA_SEMANA=?, MES=?, ANO=? WHERE PERIODOID=?";
            java.sql.Connection con = new com.vendas.vendas.config.ConexaoSQlite().getConnection();
            java.sql.PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, DtData.getValue() != null ? DtData.getValue().toString() : "");
            ps.setString(2, CbSemana.getValue()); ps.setString(3, CbMes.getValue());
            ps.setString(4, CbAno.getValue()); ps.setInt(5, idSelecionado);
            if (ps.executeUpdate() > 0) { mostrarAlerta("Sucesso", "Período atualizado!", Alert.AlertType.INFORMATION); limparFormulario(); carregarTabela(); }
        } catch (Exception e) { mostrarAlerta("Erro", e.getMessage(), Alert.AlertType.ERROR); }
    }

    @FXML
    private void deletar() {
        try {
            Integer id = Integer.parseInt(TfIdDeletar.getText());
            String sql = "DELETE FROM periodo WHERE PERIODOID=?";
            java.sql.Connection con = new com.vendas.vendas.config.ConexaoSQlite().getConnection();
            java.sql.PreparedStatement ps = con.prepareStatement(sql); ps.setInt(1, id);
            if (ps.executeUpdate() > 0) { mostrarAlerta("Sucesso", "Período removido!", Alert.AlertType.INFORMATION); limparFormulario(); carregarTabela(); }
        } catch (Exception e) { mostrarAlerta("Erro", "ID inválido: " + e.getMessage(), Alert.AlertType.ERROR); }
    }

    @FXML private void limparFormulario() { DtData.setValue(null); CbSemana.setValue(null); CbMes.setValue(null); CbAno.setValue(null); TfIdDeletar.clear(); idSelecionado = null; }
    private void mostrarAlerta(String t, String m, Alert.AlertType tipo) { Alert a = new Alert(tipo); a.setTitle(t); a.setContentText(m); a.showAndWait(); }

    private Stage getStage(MouseEvent event) { return (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow(); }
    @FXML private void abrirHome(MouseEvent event)         { NavegacaoHelper.navegar(getStage(event), "/fxml/TelaHome.fxml"); }
    @FXML private void abrirCliente(MouseEvent event)      { NavegacaoHelper.navegar(getStage(event), "/fxml/TelaDeCadastroCliente.fxml"); }
    @FXML private void abrirCategoria(MouseEvent event)    { NavegacaoHelper.navegar(getStage(event), "/fxml/TelaDeCadastroCategoria.fxml"); }
    @FXML private void abrirCidade(MouseEvent event)       { NavegacaoHelper.navegar(getStage(event), "/fxml/TelaDeCadastroCidade.fxml"); }
    @FXML private void abrirEstado(MouseEvent event)       { NavegacaoHelper.navegar(getStage(event), "/fxml/TelaDeCadastroEstado.fxml"); }
    @FXML private void abrirLoja(MouseEvent event)         { NavegacaoHelper.navegar(getStage(event), "/fxml/TelaDeCadastroLoja.fxml"); }
    @FXML private void abrirPeriodo(MouseEvent event)      { NavegacaoHelper.navegar(getStage(event), "/fxml/TelaDeCadastroPeriodo.fxml"); }
    @FXML private void abrirProduto(MouseEvent event)      { NavegacaoHelper.navegar(getStage(event), "/fxml/TelaDeCadastroProduto.fxml"); }
    @FXML private void abrirVenda(MouseEvent event)        { NavegacaoHelper.navegar(getStage(event), "/fxml/TelaDeCadastroVenda.fxml"); }
    @FXML private void abrirVendaProduto(MouseEvent event) { NavegacaoHelper.navegar(getStage(event), "/fxml/TelaDeCadastroVendaProduto.fxml"); }
    @FXML private void abrirVendedor(MouseEvent event)     { NavegacaoHelper.navegar(getStage(event), "/fxml/TelaDeCadastroVendedor.fxml"); }
}
