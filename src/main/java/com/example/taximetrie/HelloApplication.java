package com.example.taximetrie;

import com.example.taximetrie.controller.HelloController;
import com.example.taximetrie.domain.Comanda;
import com.example.taximetrie.domain.Persoana;
import com.example.taximetrie.repository.ComandaRepository;
import com.example.taximetrie.repository.PersoanaRepository;
import com.example.taximetrie.service.Service;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.io.IOException;
import java.net.ConnectException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        HelloController helloController = fxmlLoader.getController();
        stage.setTitle("Login Screen");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}