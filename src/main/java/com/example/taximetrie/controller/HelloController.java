package com.example.taximetrie.controller;

import com.example.taximetrie.HelloApplication;
import com.example.taximetrie.domain.Persoana;
import com.example.taximetrie.domain.Sofer;
import com.example.taximetrie.service.Service;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class HelloController {
    @FXML
    private Button login = new Button();
    @FXML
    private TextField textField = new TextField();

    private Service service = Service.getInstance();

    public HelloController() throws SQLException {
    }

    @FXML
    protected void handleLogin() throws SQLException, IOException {
        String username = textField.getText();
        if(service.CheckSoferUsername(username)!=null){
            Sofer soferAutentificat = service.CheckSoferUsername(username);
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("sofer-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 500);
            SoferController soferController = fxmlLoader.getController();
            soferController.setSoferAutentificat(soferAutentificat);
            stage.setTitle("Taximetrist "+soferAutentificat.getUsername());
            stage.setScene(scene);
            stage.show();


        }
        else if(service.CheckPersoanaUsername(username)!=null){
            Persoana persoanaAutentificata = service.CheckPersoanaUsername(username);
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("persoana-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            PersoanaController persoanaController = fxmlLoader.getController();
            persoanaController.setPersoanaAutentificata(persoanaAutentificata);
            stage.setTitle("Client  "+persoanaAutentificata.getUsername());
            stage.setScene(scene);
            stage.show();

        }
    }
}