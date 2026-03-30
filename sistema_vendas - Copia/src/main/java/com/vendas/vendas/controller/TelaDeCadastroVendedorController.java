package com.vendas.vendas.controller;

import com.vendas.vendas.dao.VendedorDAO;
import com.vendas.vendas.model.Vendedor;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class TelaDeCadastroVendedorController implements Initializable {

    @FXML private Button BtnApagar, BtnAtualizar, BtnCadastrar, BtnDeletar;
    @FXML private HBox BtnCategoria, BtnCidade, BtnCliente, BtnEstado, BtnLoja, BtnPeriodo, BtnProduto, BtnVenda, BtnVendaProduto, BtnVendedor;
    @FXML private Label BtnHome;
    @FXML private ChoiceBox<String> CbSexo;
    @FXML private TableColumn<Vendedor, Integer> Colid;
    @FXML private TableColumn<Vendedor, String> ColNome, ColSexo;
    @FXML private TableColumn<Vendedor, LocalDate> ColDataNasc, ColDataContrato;
    @FXML private TableView<Vendedor> tableView;
    @FXML private DatePicker DtData, DtDataContrato;
    @FXML private TextField TfNome, TfIdDeletar;

    private Integer idSelecionado = null;
    private List<String> sexos = Arrays.asList("Masculino", "Feminino");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CbSexo.setItems(FXCollections.observableList(sexos));

        Colid.setCellValueFactory(d -> new javafx.beans.property.SimpleObjectProperty<>(d.getValue().getVENDEDORID()));
        ColNome.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getNOME()));
        ColSexo.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getSEXO()));
        ColDataNasc.setCellValueFactory(d -> new javafx.beans.property.SimpleObjectProperty<>(
            d.getValue().getDATA_NASCIMENTO() != null ? LocalDate.parse(d.getValue().getDATA_NASCIMENTO()) : null));
        ColDataContrato.setCellValueFactory(d -> new javafx.beans.property.SimpleObjectProperty<>(
            d.getValue().getDATA_CONTRATACAO() != null ? LocalDate.parse(d.getValue().getDATA_CONTRATACAO()) : null));

        carregarTabela();
        tableView.setOnMouseClicked(e -> selecionarLinha());
    }

    private void carregarTabela() {
        try { tableView.setItems(FXCollections.observableArrayList(new VendedorDAO().listar())); }
        catch (SQLException e) { e.printStackTrace(); }
    }

    private void selecionarLinha() {
        Vendedor v = tableView.getSelectionModel().getSelectedItem();
        if (v != null) {
            idSelecionado = v.getVENDEDORID();
            TfNome.setText(v.getNOME()); CbSexo.setValue(v.getSEXO());
            if (v.getDATA_NASCIMENTO() != null) DtData.setValue(LocalDate.parse(v.getDATA_NASCIMENTO()));
            if (v.getDATA_CONTRATACAO() != null) DtDataContrato.setValue(LocalDate.parse(v.getDATA_CONTRATACAO()));
            TfIdDeletar.setText(String.valueOf(v.getVENDEDORID()));
        }
    }

    @FXML
    private void cadastrar() {
        try {
            String sql = "INSERT INTO vendedor (NOME, SEXO, DATA_NASCIMENTO, DATA_CONTRATACAO) VALUES (?,?,?,?)";
            java.sql.Connection con = new com.vendas.vendas.config.ConexaoSQlite().getConnection();
            java.sql.PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, TfNome.getText()); ps.setString(2, CbSexo.getValue());
            ps.setString(3, DtData.getValue() != null ? DtData.getValue().toString() : "");
            ps.setString(4, DtDataContrato.getValue() != null ? DtDataContrato.getValue().toString() : "");
            if (ps.executeUpdate() > 0) { mostrarAlerta("Sucesso", "Vendedor cadastrado!", Alert.AlertType.INFORMATION); limparFormulario(); carregarTabela(); }
        } catch (Exception e) { mostrarAlerta("Erro", e.getMessage(), Alert.AlertType.ERROR); }
    }

    @FXML
    private void atualizar() {
        if (idSelecionado == null) { mostrarAlerta("Atenção", "Selecione uma linha da tabela.", Alert.AlertType.WARNING); return; }
        try {
            String sql = "UPDATE vendedor SET NOME=?, SEXO=?, DATA_NASCIMENTO=?, DATA_CONTRATACAO=? WHERE VENDEDORID=?";
            java.sql.Connection con = new com.vendas.vendas.config.ConexaoSQlite().getConnection();
            java.sql.PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, TfNome.getText()); ps.setString(2, CbSexo.getValue());
            ps.setString(3, DtData.getValue() != null ? DtData.getValue().toString() : "");
            ps.setString(4, DtDataContrato.getValue() != null ? DtDataContrato.getValue().toString() : "");
            ps.setInt(5, idSelecionado);
            if (ps.executeUpdate() > 0) { mostrarAlerta("Sucesso", "Vendedor atualizado!", Alert.AlertType.INFORMATION); limparFormulario(); carregarTabela(); }
        } catch (Exception e) { mostrarAlerta("Erro", e.getMessage(), Alert.AlertType.ERROR); }
    }

    @FXML
    private void deletar() {
        try {
            Integer id = Integer.parseInt(TfIdDeletar.getText());
            String sql = "DELETE FROM vendedor WHERE VENDEDORID=?";
            java.sql.Connection con = new com.vendas.vendas.config.ConexaoSQlite().getConnection();
            java.sql.PreparedStatement ps = con.prepareStatement(sql); ps.setInt(1, id);
            if (ps.executeUpdate() > 0) { mostrarAlerta("Sucesso", "Vendedor removido!", Alert.AlertType.INFORMATION); limparFormulario(); carregarTabela(); }
        } catch (Exception e) { mostrarAlerta("Erro", "ID inválido: " + e.getMessage(), Alert.AlertType.ERROR); }
    }

    @FXML private void limparFormulario() { TfNome.clear(); TfIdDeletar.clear(); CbSexo.setValue(null); DtData.setValue(null); DtDataContrato.setValue(null); idSelecionado = null; }
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
