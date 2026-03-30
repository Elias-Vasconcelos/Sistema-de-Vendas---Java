package com.vendas.vendas.controller;

import com.vendas.vendas.dao.ClienteDAO;
import com.vendas.vendas.model.Cliente;
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
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class TelaDeCadastroPessoaController implements Initializable {

    @FXML private Button BtnApagar, BtnAtualizar, BtnCadastrar, BtnDeletar;
    @FXML private HBox BtnCategoria, BtnCidade, BtnCliente, BtnEstado, BtnLoja, BtnPeriodo, BtnProduto, BtnVenda, BtnVendaProduto, BtnVendedor;
    @FXML private Label BtnHome;
    @FXML private ChoiceBox<String> CbCredito, CbSexo;
    @FXML private TableColumn<Cliente, Integer> Colid;
    @FXML private TableColumn<Cliente, LocalDate> ColDataCadas, ColDataNasc;
    @FXML private TableColumn<Cliente, String> ColCredito, ColEmail, ColNome, ColSexo;
    @FXML private TableView<Cliente> tableView;
    @FXML private DatePicker DtData;
    @FXML private TextField TfEmail, TfIdDeletar, TfNome;

    private Integer idSelecionado = null;
    private List<String> sexos = Arrays.asList("Masculino", "Feminino");
    private List<String> credito = Arrays.asList("Positivo", "Negativado");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CbSexo.setItems(FXCollections.observableList(sexos));
        CbCredito.setItems(FXCollections.observableList(credito));

        Colid.setCellValueFactory(d -> new javafx.beans.property.SimpleObjectProperty<>(d.getValue().getCLIENTEID()));
        ColNome.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getNOME()));
        ColEmail.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getEMAIL()));
        ColDataNasc.setCellValueFactory(d -> new javafx.beans.property.SimpleObjectProperty<>(d.getValue().getDATA_DE_NACIMENTO()));
        ColSexo.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getSEXO()));
        ColCredito.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getSTATUS_DE_CREDITO()));
        ColDataCadas.setCellValueFactory(d -> new javafx.beans.property.SimpleObjectProperty<>(d.getValue().getDATA_DE_CADASTRO()));

        carregarTabela();
        tableView.setOnMouseClicked(e -> selecionarLinha());
    }

    private void carregarTabela() {
        try { tableView.setItems(FXCollections.observableArrayList(ClienteDAO.listar())); }
        catch (SQLException e) { e.printStackTrace(); }
    }

    private void selecionarLinha() {
        Cliente c = tableView.getSelectionModel().getSelectedItem();
        if (c != null) {
            idSelecionado = c.getCLIENTEID();
            TfNome.setText(c.getNOME());
            TfEmail.setText(c.getEMAIL());
            DtData.setValue(c.getDATA_DE_NACIMENTO());
            CbSexo.setValue(c.getSEXO());
            CbCredito.setValue(c.getSTATUS_DE_CREDITO());
            TfIdDeletar.setText(String.valueOf(c.getCLIENTEID()));
        }
    }

    @FXML
    private void cadastrar() {
        try {
            String sql = "INSERT INTO cliente (NOME, EMAIL, DATA_DE_NACIMENTO, SEXO, STATUS_DE_CREDITO, DATA_DE_CADASTRO) VALUES (?,?,?,?,?,?)";
            java.sql.Connection con = new com.vendas.vendas.config.ConexaoSQlite().getConnection();
            java.sql.PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, TfNome.getText());
            ps.setString(2, TfEmail.getText());
            ps.setString(3, DtData.getValue() != null ? DtData.getValue().toString() : "");
            ps.setString(4, CbSexo.getValue());
            ps.setString(5, CbCredito.getValue());
            ps.setString(6, LocalDate.now().toString());
            if (ps.executeUpdate() > 0) { mostrarAlerta("Sucesso", "Cliente cadastrado!", Alert.AlertType.INFORMATION); limparFormulario(); carregarTabela(); }
        } catch (Exception e) { mostrarAlerta("Erro", e.getMessage(), Alert.AlertType.ERROR); }
    }

    @FXML
    private void atualizar() {
        if (idSelecionado == null) { mostrarAlerta("Atenção", "Selecione uma linha da tabela.", Alert.AlertType.WARNING); return; }
        try {
            Cliente c = new Cliente(idSelecionado, TfNome.getText(), TfEmail.getText(),
                DtData.getValue(), CbSexo.getValue(), CbCredito.getValue(), LocalDate.now());
            if (new ClienteDAO().atualizar(c)) { mostrarAlerta("Sucesso", "Cliente atualizado!", Alert.AlertType.INFORMATION); limparFormulario(); carregarTabela(); }
        } catch (Exception e) { mostrarAlerta("Erro", e.getMessage(), Alert.AlertType.ERROR); }
    }

    @FXML
    private void deletar() {
        try {
            Integer id = Integer.parseInt(TfIdDeletar.getText());
            if (new ClienteDAO().remover(id)) { mostrarAlerta("Sucesso", "Cliente removido!", Alert.AlertType.INFORMATION); limparFormulario(); carregarTabela(); }
        } catch (Exception e) { mostrarAlerta("Erro", "ID inválido: " + e.getMessage(), Alert.AlertType.ERROR); }
    }

    @FXML private void limparFormulario() { TfNome.clear(); TfEmail.clear(); TfIdDeletar.clear(); DtData.setValue(null); CbSexo.setValue(null); CbCredito.setValue(null); idSelecionado = null; }
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
