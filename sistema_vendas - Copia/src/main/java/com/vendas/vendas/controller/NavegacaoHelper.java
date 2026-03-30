package com.vendas.vendas.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class NavegacaoHelper {

    public static void navegar(Stage stage, String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(
                NavegacaoHelper.class.getResource(fxmlPath)
            );
            BorderPane root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
