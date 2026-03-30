package com.vendas.vendas.controller;

import com.vendas.vendas.dao.ProdutoDAO;
import com.vendas.vendas.model.Categoria;
import com.vendas.vendas.model.Produto;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class TelaDeCadastroProdutoController implements Initializable {

    @FXML private Button BtnApagar, BtnAtualizar, BtnCadastrar, BtnDeletar;
    @FXML private HBox BtnCategoria, BtnCidade, BtnCliente, BtnEstado, BtnLoja, BtnPeriodo, BtnProduto, BtnVenda, BtnVendaProduto, BtnVendedor;
    @FXML private Label BtnHome;
    @FXML private ChoiceBox<String> CbCategoria;
    @FXML private TableColumn<Produto, Integer> Colid;
    @FXML private TableColumn<Produto, String> ColNome, ColDescricao, ColCategoria;
    @FXML private TableColumn<Produto, Double> ColCusto, ColVenda;
    @FXML private TableView<Produto> tableView;
    @FXML private TextField TfNome, TfDescricao, TfCusto, TfVenda, TfIdDeletar;

    private Integer idSelecionado = null;
    private List<String> categorias = Arrays.asList("Alimentos e Bebidas","Eletrônicos","Vestuário","Casa e Decoração","Saúde e Beleza","Brinquedos e Jogos","Esporte e Lazer","Automotivo","Papelaria","Pet Shop");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CbCategoria.setItems(FXCollections.observableList(categorias));

        Colid.setCellValueFactory(d -> new javafx.beans.property.SimpleObjectProperty<>(d.getValue().getPRODUTOID()));
        ColNome.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getNOME()));
        ColDescricao.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getDESCRICAO()));
        ColCusto.setCellValueFactory(d -> new javafx.beans.property.SimpleObjectProperty<>(d.getValue().getVALOR_DE_CUSTO()));
        ColVenda.setCellValueFactory(d -> new javafx.beans.property.SimpleObjectProperty<>(d.getValue().getVALOR_DE_VENDA()));
        ColCategoria.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(
            d.getValue().getCATEGORIA() != null ? String.valueOf(d.getValue().getCATEGORIA().getCATEGORIAID()) : ""));

        carregarTabela();
        tableView.setOnMouseClicked(e -> selecionarLinha());
    }

    private void carregarTabela() {
        try { tableView.setItems(FXCollections.observableArrayList(new ProdutoDAO().listar())); }
        catch (SQLException e) { e.printStackTrace(); }
    }

    private void selecionarLinha() {
        Produto p = tableView.getSelectionModel().getSelectedItem();
        if (p != null) {
            idSelecionado = p.getPRODUTOID();
            TfNome.setText(p.getNOME()); TfDescricao.setText(p.getDESCRICAO());
            TfCusto.setText(String.valueOf(p.getVALOR_DE_CUSTO()));
            TfVenda.setText(String.valueOf(p.getVALOR_DE_VENDA()));
            TfIdDeletar.setText(String.valueOf(p.getPRODUTOID()));
        }
    }

    @FXML
    private void cadastrar() {
        try {
            Categoria cat = new Categoria(); cat.setCATEGORIAID(categorias.indexOf(CbCategoria.getValue()) + 1);
            Produto p = new Produto(null, TfNome.getText(), TfDescricao.getText(), cat,
                Double.parseDouble(TfCusto.getText()), Double.parseDouble(TfVenda.getText()));
            if (new ProdutoDAO().inserir(p)) { mostrarAlerta("Sucesso", "Produto cadastrado!", Alert.AlertType.INFORMATION); limparFormulario(); carregarTabela(); }
        } catch (Exception e) { mostrarAlerta("Erro", e.getMessage(), Alert.AlertType.ERROR); }
    }

    @FXML
    private void atualizar() {
        if (idSelecionado == null) { mostrarAlerta("Atenção", "Selecione uma linha da tabela.", Alert.AlertType.WARNING); return; }
        try {
            String sql = "UPDATE produto SET NOME=?, DESCRICAO=?, VALOR_DE_CUSTO=?, VALOR_DE_VENDA=? WHERE PRODUTOID=?";
            java.sql.Connection con = new com.vendas.vendas.config.ConexaoSQlite().getConnection();
            java.sql.PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, TfNome.getText()); ps.setString(2, TfDescricao.getText());
            ps.setDouble(3, Double.parseDouble(TfCusto.getText())); ps.setDouble(4, Double.parseDouble(TfVenda.getText())); ps.setInt(5, idSelecionado);
            if (ps.executeUpdate() > 0) { mostrarAlerta("Sucesso", "Produto atualizado!", Alert.AlertType.INFORMATION); limparFormulario(); carregarTabela(); }
        } catch (Exception e) { mostrarAlerta("Erro", e.getMessage(), Alert.AlertType.ERROR); }
    }

    @FXML
    private void deletar() {
        try {
            Integer id = Integer.parseInt(TfIdDeletar.getText());
            String sql = "DELETE FROM produto WHERE PRODUTOID=?";
            java.sql.Connection con = new com.vendas.vendas.config.ConexaoSQlite().getConnection();
            java.sql.PreparedStatement ps = con.prepareStatement(sql); ps.setInt(1, id);
            if (ps.executeUpdate() > 0) { mostrarAlerta("Sucesso", "Produto removido!", Alert.AlertType.INFORMATION); limparFormulario(); carregarTabela(); }
        } catch (Exception e) { mostrarAlerta("Erro", "ID inválido: " + e.getMessage(), Alert.AlertType.ERROR); }
    }

    @FXML private void limparFormulario() { TfNome.clear(); TfDescricao.clear(); TfCusto.clear(); TfVenda.clear(); TfIdDeletar.clear(); CbCategoria.setValue(null); idSelecionado = null; }
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
