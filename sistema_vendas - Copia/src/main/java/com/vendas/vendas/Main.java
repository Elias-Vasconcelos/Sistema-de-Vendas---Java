package com.vendas.vendas;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.vendas.vendas.controller.TelaHomeController;



public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(TelaHomeController.class.getResource("fxml/TelaHome.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setTitle("TelaHome");
        stage.setScene(scene);
        stage.show();
    }
}
