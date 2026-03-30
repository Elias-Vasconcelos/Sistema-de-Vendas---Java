package com.vendas.vendas.controller;

import com.vendas.vendas.dao.EstadoDAO;
import com.vendas.vendas.model.Estado;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TelaDeCadastroEstadoController implements Initializable {

    @FXML private Button BtnApagar, BtnAtualizar, BtnCadastrar, BtnDeletar;
    @FXML private HBox BtnCategoria, BtnCidade, BtnCliente, BtnEstado, BtnLoja, BtnPeriodo, BtnProduto, BtnVenda, BtnVendaProduto, BtnVendedor;
    @FXML private Label BtnHome;
    @FXML private TableColumn<Estado, Integer> Colid;
    @FXML private TableColumn<Estado, String> ColNome;
    @FXML private TableView<Estado> tableView;
    @FXML private TextField TfNome, TfIdDeletar;

    private Integer idSelecionado = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Colid.setCellValueFactory(d -> new javafx.beans.property.SimpleObjectProperty<>(d.getValue().getESTADOID()));
        ColNome.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getNOME()));
        carregarTabela();
        tableView.setOnMouseClicked(e -> selecionarLinha());
    }

    private void carregarTabela() {
        try { tableView.setItems(FXCollections.observableArrayList(new EstadoDAO().listar())); }
        catch (SQLException e) { e.printStackTrace(); }
    }

    private void selecionarLinha() {
        Estado s = tableView.getSelectionModel().getSelectedItem();
        if (s != null) { idSelecionado = s.getESTADOID(); TfNome.setText(s.getNOME()); TfIdDeletar.setText(String.valueOf(s.getESTADOID())); }
    }

    @FXML
    private void cadastrar() {
        try {
            if (new EstadoDAO().inserir(new Estado(null, TfNome.getText()))) {
                mostrarAlerta("Sucesso", "Estado cadastrado!", Alert.AlertType.INFORMATION);
                limparFormulario(); carregarTabela();
            }
        } catch (SQLException e) { mostrarAlerta("Erro", e.getMessage(), Alert.AlertType.ERROR); }
    }

    @FXML
    private void atualizar() {
        if (idSelecionado == null) { mostrarAlerta("Atenção", "Selecione uma linha da tabela.", Alert.AlertType.WARNING); return; }
        try {
            String sql = "UPDATE estado SET NOME=? WHERE ESTADOID=?";
            java.sql.Connection con = new com.vendas.vendas.config.ConexaoSQlite().getConnection();
            java.sql.PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, TfNome.getText()); ps.setInt(2, idSelecionado);
            if (ps.executeUpdate() > 0) { mostrarAlerta("Sucesso", "Estado atualizado!", Alert.AlertType.INFORMATION); limparFormulario(); carregarTabela(); }
        } catch (SQLException e) { mostrarAlerta("Erro", e.getMessage(), Alert.AlertType.ERROR); }
    }

    @FXML
    private void deletar() {
        try {
            Integer id = Integer.parseInt(TfIdDeletar.getText());
            String sql = "DELETE FROM estado WHERE ESTADOID=?";
            java.sql.Connection con = new com.vendas.vendas.config.ConexaoSQlite().getConnection();
            java.sql.PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            if (ps.executeUpdate() > 0) { mostrarAlerta("Sucesso", "Estado removido!", Alert.AlertType.INFORMATION); limparFormulario(); carregarTabela(); }
        } catch (Exception e) { mostrarAlerta("Erro", "ID inválido: " + e.getMessage(), Alert.AlertType.ERROR); }
    }

    @FXML private void limparFormulario() { TfNome.clear(); TfIdDeletar.clear(); idSelecionado = null; }
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
