package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.controller.TelaHomeController;


public class App extends Application {

    public static void main(String[] args) {
        launch(args); // Inicia a aplicação JavaFX
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(TelaHomeController.class.getResource("fxml/TelaHome.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setTitle("Home");
        stage.setScene(scene);
        stage.show();
    }


}