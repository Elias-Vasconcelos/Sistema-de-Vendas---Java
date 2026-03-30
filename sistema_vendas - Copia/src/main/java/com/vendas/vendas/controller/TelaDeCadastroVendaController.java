package com.vendas.vendas.controller;

import com.vendas.vendas.dao.VendaDAO;
import com.vendas.vendas.model.Venda;
import javafx.beans.property.SimpleStringProperty;
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

public class TelaDeCadastroVendaController implements Initializable {

    @FXML private Button BtnApagar, BtnAtualizar, BtnCadastrar;
    @FXML private HBox BtnCategoria, BtnCidade, BtnCliente, BtnEstado, BtnLoja, BtnPeriodo, BtnProduto, BtnVenda, BtnVendaProduto, BtnVendedor;
    @FXML private Label BtnHome;
    @FXML private ChoiceBox<String> CbMetPagamento;
    @FXML private TableColumn<Venda, Integer> Colid;
    @FXML private TableColumn<Venda, String> ColCliente, ColVendedor, ColLoja, ColTotal, ColPeriodo, ColCidade, ColHora, ColPagamento;
    @FXML private TableColumn<Venda, LocalDate> ColData;
    @FXML private TableView<Venda> tableView;
    @FXML private DatePicker DataVenda;
    @FXML private TextField TfClienteID, TfVendedorID, TfLojaID, TfTotal, TfPeriodoID, TfCidadeID, TfHora;

    private Integer idSelecionado = null;
    private List<String> metodosPagamento = Arrays.asList("Dinheiro","Cartão de Crédito","Cartão de Débito","PIX","Boleto Bancário","Transferência Bancária","Vale Refeição","Crediário");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CbMetPagamento.setItems(FXCollections.observableList(metodosPagamento));

        Colid.setCellValueFactory(d -> new javafx.beans.property.SimpleObjectProperty<>(d.getValue().getVENDAID()));
        ColCliente.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(String.valueOf(d.getValue().getCLIENTE())));
        ColVendedor.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(String.valueOf(d.getValue().getVENDEDOR())));
        ColLoja.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(String.valueOf(d.getValue().getLOJA())));
        ColCidade.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(String.valueOf(d.getValue().getCIDADE())));
        ColPeriodo.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(String.valueOf(d.getValue().getPERIODO())));
        ColData.setCellValueFactory(d -> new javafx.beans.property.SimpleObjectProperty<>(d.getValue().getDATA_VENDA()));
        ColHora.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(String.valueOf(d.getValue().getHORA_VENDA())));
        ColTotal.setCellValueFactory(d ->new SimpleStringProperty(String.valueOf(d.getValue().getTOTAL_VENDA())));
        ColPagamento.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getMETODO_PAGAMENTO()));

        carregarTabela();
        tableView.setOnMouseClicked(e -> selecionarLinha());
    }

    private void carregarTabela() {
        try { tableView.setItems(FXCollections.observableArrayList(new VendaDAO().listar())); }
        catch (SQLException e) { e.printStackTrace(); }
    }

    private void selecionarLinha() {
        Venda v = tableView.getSelectionModel().getSelectedItem();
        if (v != null) {
            idSelecionado = v.getVENDAID();
            TfClienteID.setText(String.valueOf(v.getCLIENTE()));
            TfVendedorID.setText(String.valueOf(v.getVENDEDOR()));
            TfLojaID.setText(String.valueOf(v.getLOJA()));
            TfCidadeID.setText(String.valueOf(v.getCIDADE()));
            TfPeriodoID.setText(String.valueOf(v.getPERIODO()));
            TfTotal.setText(String.valueOf(v.getTOTAL_VENDA()));
            TfHora.setText(String.valueOf(v.getHORA_VENDA()));
            DataVenda.setValue(v.getDATA_VENDA());
            CbMetPagamento.setValue(v.getMETODO_PAGAMENTO());
        }
    }

    @FXML
    private void cadastrar() {
        try {
            String sql = "INSERT INTO venda (CLIENTEID, CIDADEID, PERIODOID, VENDEDORID, LOJAID, DATA_VENDA, HORA_VENDA, TOTAL_VENDA, METODO_PAGAMENTO) VALUES (?,?,?,?,?,?,?,?,?)";
            java.sql.Connection con = new com.vendas.vendas.config.ConexaoSQlite().getConnection();
            java.sql.PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(TfClienteID.getText()));
            ps.setInt(2, Integer.parseInt(TfCidadeID.getText()));
            ps.setInt(3, Integer.parseInt(TfPeriodoID.getText()));
            ps.setInt(4, Integer.parseInt(TfVendedorID.getText()));
            ps.setInt(5, Integer.parseInt(TfLojaID.getText()));
            ps.setString(6, DataVenda.getValue() != null ? DataVenda.getValue().toString() : "");
            ps.setString(7, TfHora.getText());
            ps.setString(8, TfTotal.getText());
            ps.setString(9, CbMetPagamento.getValue());
            if (ps.executeUpdate() > 0) { mostrarAlerta("Sucesso", "Venda cadastrada!", Alert.AlertType.INFORMATION); limparFormulario(); carregarTabela(); }
        } catch (Exception e) { mostrarAlerta("Erro", e.getMessage(), Alert.AlertType.ERROR); }
    }

    @FXML
    private void atualizar() {
        if (idSelecionado == null) { mostrarAlerta("Atenção", "Selecione uma linha da tabela.", Alert.AlertType.WARNING); return; }
        try {
            String sql = "UPDATE venda SET CLIENTEID=?, CIDADEID=?, PERIODOID=?, VENDEDORID=?, LOJAID=?, DATA_VENDA=?, HORA_VENDA=?, TOTAL_VENDA=?, METODO_PAGAMENTO=? WHERE VENDAID=?";
            java.sql.Connection con = new com.vendas.vendas.config.ConexaoSQlite().getConnection();
            java.sql.PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(TfClienteID.getText()));
            ps.setInt(2, Integer.parseInt(TfCidadeID.getText()));
            ps.setInt(3, Integer.parseInt(TfPeriodoID.getText()));
            ps.setInt(4, Integer.parseInt(TfVendedorID.getText()));
            ps.setInt(5, Integer.parseInt(TfLojaID.getText()));
            ps.setString(6, DataVenda.getValue() != null ? DataVenda.getValue().toString() : "");
            ps.setString(7, TfHora.getText()); ps.setString(8, TfTotal.getText());
            ps.setString(9, CbMetPagamento.getValue()); ps.setInt(10, idSelecionado);
            if (ps.executeUpdate() > 0) { mostrarAlerta("Sucesso", "Venda atualizada!", Alert.AlertType.INFORMATION); limparFormulario(); carregarTabela(); }
        } catch (Exception e) { mostrarAlerta("Erro", e.getMessage(), Alert.AlertType.ERROR); }
    }

    @FXML private void limparFormulario() {
        TfClienteID.clear(); TfVendedorID.clear(); TfLojaID.clear(); TfTotal.clear();
        TfPeriodoID.clear(); TfCidadeID.clear(); TfHora.clear();
        DataVenda.setValue(null); CbMetPagamento.setValue(null); idSelecionado = null;
    }
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
