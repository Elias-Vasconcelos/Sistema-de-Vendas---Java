package com.vendas.vendas.controller;

import com.vendas.vendas.dao.CategoriaDAO;
import com.vendas.vendas.model.Categoria;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TelaDeCadastroCategoriaController implements Initializable {

    @FXML private Button BtnApagar;
    @FXML private Button BtnAtualizar;
    @FXML private Button BtnCadastrar;
    @FXML private HBox BtnCategoria;
    @FXML private HBox BtnCidade;
    @FXML private HBox BtnCliente;
    @FXML private Button BtnDeletar;
    @FXML private HBox BtnEstado;
    @FXML private Label BtnHome;
    @FXML private HBox BtnLoja;
    @FXML private HBox BtnPeriodo;
    @FXML private HBox BtnProduto;
    @FXML private HBox BtnVenda;
    @FXML private HBox BtnVendaProduto;
    @FXML private HBox BtnVendedor;
    @FXML private TableColumn<Categoria, Integer> Colid;
    @FXML private TableColumn<Categoria, String> ColNome;
    @FXML private TableColumn<Categoria, String> ColDescricao;
    @FXML private TableView<Categoria> tableView;
    @FXML private TextField TfNome;
    @FXML private TextField TfDescricao;
    @FXML private TextField TfIdDeletar;

    private Integer idSelecionado = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Colid.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getCATEGORIAID()));
        ColNome.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getNOME()));
        ColDescricao.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getDESCRICAO()));
        carregarTabela();
        tableView.setOnMouseClicked(e -> selecionarLinha());
    }

    private void carregarTabela() {
        try {
            ArrayList<Categoria> lista = new CategoriaDAO().listar();
            tableView.setItems(FXCollections.observableArrayList(lista));
        } catch (SQLException e) { e.printStackTrace(); }
    }

    private void selecionarLinha() {
        Categoria c = tableView.getSelectionModel().getSelectedItem();
        if (c != null) {
            idSelecionado = c.getCATEGORIAID();
            TfNome.setText(c.getNOME());
            TfDescricao.setText(c.getDESCRICAO());
            TfIdDeletar.setText(String.valueOf(c.getCATEGORIAID()));
        }
    }

    @FXML
    private void cadastrar() {
        try {
            Categoria c = new Categoria(null, TfNome.getText(), TfDescricao.getText());
            if (new CategoriaDAO().inserir(c)) {
                mostrarAlerta("Sucesso", "Categoria cadastrada!", Alert.AlertType.INFORMATION);
                limparFormulario();
                carregarTabela();
            }
        } catch (SQLException e) { mostrarAlerta("Erro", e.getMessage(), Alert.AlertType.ERROR); }
    }

    @FXML
    private void atualizar() {
        if (idSelecionado == null) { mostrarAlerta("Atenção", "Selecione uma linha da tabela.", Alert.AlertType.WARNING); return; }
        try {
            Categoria c = new Categoria(idSelecionado, TfNome.getText(), TfDescricao.getText());
            if (new CategoriaDAO().atualizar(c)) {
                mostrarAlerta("Sucesso", "Categoria atualizada!", Alert.AlertType.INFORMATION);
                limparFormulario();
                carregarTabela();
            }
        } catch (SQLException e) { mostrarAlerta("Erro", e.getMessage(), Alert.AlertType.ERROR); }
    }

    @FXML
    private void deletar() {
        try {
            Integer id = Integer.parseInt(TfIdDeletar.getText());
            if (new CategoriaDAO().remover(id)) {
                mostrarAlerta("Sucesso", "Categoria removida!", Alert.AlertType.INFORMATION);
                limparFormulario();
                carregarTabela();
            }
        } catch (Exception e) { mostrarAlerta("Erro", "ID inválido ou erro: " + e.getMessage(), Alert.AlertType.ERROR); }
    }

    @FXML private void limparFormulario() { TfNome.clear(); TfDescricao.clear(); TfIdDeletar.clear(); idSelecionado = null; }

    private void mostrarAlerta(String titulo, String msg, Alert.AlertType tipo) {
        Alert a = new Alert(tipo); a.setTitle(titulo); a.setContentText(msg); a.showAndWait();
    }

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
