package com.vendas.vendas.controller;

import com.vendas.vendas.dao.VendaProdutoDAO;
import com.vendas.vendas.model.Produto;
import com.vendas.vendas.model.Venda;
import com.vendas.vendas.model.VendaProduto;
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

public class TelaDeCadastroVendaProdutoController implements Initializable {

    @FXML private Button BtnApagar, BtnAtualizar, BtnCadastrar;
    @FXML private HBox BtnCategoria, BtnCidade, BtnCliente, BtnEstado, BtnLoja, BtnPeriodo, BtnProduto, BtnVenda, BtnVendaProduto, BtnVendedor;
    @FXML private Label BtnHome;
    @FXML private TableColumn<VendaProduto, Integer> Colid;
    @FXML private TableColumn<VendaProduto, String> ColVenda, ColProduto, ColQuantidade, ColPreco, ColDesconto;
    @FXML private TableView<VendaProduto> tableView;
    @FXML private TextField TfVendaID, TfProdutoID, TfQuantidade, TfPreco, TfDesconto;

    private Integer idSelecionado = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Colid.setCellValueFactory(d -> new javafx.beans.property.SimpleObjectProperty<>(d.getValue().getVENDAPRODUTOID()));
        ColVenda.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(
            d.getValue().getVENDA() != null ? String.valueOf(d.getValue().getVENDA().getVENDAID()) : ""));
        ColProduto.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(
            d.getValue().getPRODUTO() != null ? String.valueOf(d.getValue().getPRODUTO().getPRODUTOID()) : ""));
        ColQuantidade.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(String.valueOf(d.getValue().getQUANTIDADE())));
        ColPreco.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(String.valueOf(d.getValue().getPRECO_UNIDADE())));
        ColDesconto.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(String.valueOf(d.getValue().getDESCONTO())));

        carregarTabela();
        tableView.setOnMouseClicked(e -> selecionarLinha());
    }

    private void carregarTabela() {
        try { tableView.setItems(FXCollections.observableArrayList(new VendaProdutoDAO().listar())); }
        catch (SQLException e) { e.printStackTrace(); }
    }

    private void selecionarLinha() {
        VendaProduto vp = tableView.getSelectionModel().getSelectedItem();
        if (vp != null) {
            idSelecionado = vp.getVENDAPRODUTOID();
            TfVendaID.setText(vp.getVENDA() != null ? String.valueOf(vp.getVENDA().getVENDAID()) : "");
            TfProdutoID.setText(vp.getPRODUTO() != null ? String.valueOf(vp.getPRODUTO().getPRODUTOID()) : "");
            TfQuantidade.setText(String.valueOf(vp.getQUANTIDADE()));
            TfPreco.setText(String.valueOf(vp.getPRECO_UNIDADE()));
            TfDesconto.setText(String.valueOf(vp.getDESCONTO()));
        }
    }

    @FXML
    private void cadastrar() {
        try {
            String sql = "INSERT INTO vendaproduto (VENDAID, PRODUTOID, QUANTIDADE, PRECO_UNIDADE, DESCONTO) VALUES (?,?,?,?,?)";
            java.sql.Connection con = new com.vendas.vendas.config.ConexaoSQlite().getConnection();
            java.sql.PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(TfVendaID.getText()));
            ps.setInt(2, Integer.parseInt(TfProdutoID.getText()));
            ps.setInt(3, Integer.parseInt(TfQuantidade.getText()));
            ps.setDouble(4, Double.parseDouble(TfPreco.getText()));
            ps.setDouble(5, Double.parseDouble(TfDesconto.getText()));
            if (ps.executeUpdate() > 0) { mostrarAlerta("Sucesso", "Venda Produto cadastrado!", Alert.AlertType.INFORMATION); limparFormulario(); carregarTabela(); }
        } catch (Exception e) { mostrarAlerta("Erro", e.getMessage(), Alert.AlertType.ERROR); }
    }

    @FXML
    private void atualizar() {
        if (idSelecionado == null) { mostrarAlerta("Atenção", "Selecione uma linha da tabela.", Alert.AlertType.WARNING); return; }
        try {
            String sql = "UPDATE vendaproduto SET VENDAID=?, PRODUTOID=?, QUANTIDADE=?, PRECO_UNIDADE=?, DESCONTO=? WHERE VENDAPRODUTOID=?";
            java.sql.Connection con = new com.vendas.vendas.config.ConexaoSQlite().getConnection();
            java.sql.PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(TfVendaID.getText()));
            ps.setInt(2, Integer.parseInt(TfProdutoID.getText()));
            ps.setInt(3, Integer.parseInt(TfQuantidade.getText()));
            ps.setDouble(4, Double.parseDouble(TfPreco.getText()));
            ps.setDouble(5, Double.parseDouble(TfDesconto.getText()));
            ps.setInt(6, idSelecionado);
            if (ps.executeUpdate() > 0) { mostrarAlerta("Sucesso", "Venda Produto atualizado!", Alert.AlertType.INFORMATION); limparFormulario(); carregarTabela(); }
        } catch (Exception e) { mostrarAlerta("Erro", e.getMessage(), Alert.AlertType.ERROR); }
    }

    @FXML private void limparFormulario() { TfVendaID.clear(); TfProdutoID.clear(); TfQuantidade.clear(); TfPreco.clear(); TfDesconto.clear(); idSelecionado = null; }
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
