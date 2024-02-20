package com.example.taximetrie.controller;

import com.example.taximetrie.domain.Comanda;
import com.example.taximetrie.domain.Persoana;
import com.example.taximetrie.event.ComandaActivaNoua;
import com.example.taximetrie.observer.Observer;
import com.example.taximetrie.service.ServiceComenziActive;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class PersoanaController implements Observer<ComandaActivaNoua> {
    @FXML
    Label numeLabel = new Label();
    @FXML
    TextField locatieTextField = new TextField();
    @FXML
    Label label1 = new Label();
    @FXML
    Label nr_masina = new Label();
    @FXML
    Label label2 = new Label();
    @FXML
    Label locatia = new Label();
    @FXML
    Label label3 = new Label();
    @FXML
    Button accept = new Button();
    @FXML
    Button decline = new Button();

    ServiceComenziActive serviceComenziActive = ServiceComenziActive.getInstance();
    Persoana persoanaAutentificata;

    Comanda comandaCurentaPreluata = null;

    public PersoanaController() throws SQLException {

    }
    public void setPersoanaAutentificata(Persoana persoanaAutentificata) {
        this.persoanaAutentificata = persoanaAutentificata;
        this.serviceComenziActive.addObserver(this);
        numeLabel.setText(persoanaAutentificata.getNume());
        label1.setVisible(false);
        nr_masina.setVisible(false);
        label2.setVisible(false);
        locatia.setVisible(false);
        label3.setVisible(false);
        accept.setVisible(false);
        decline.setVisible(false);
    }

    public void handleAddComandaActiva(){
        if(!locatieTextField.getText().isEmpty()) {
            String locatie = locatieTextField.getText();
            this.serviceComenziActive.addNewComandaActiva(locatie, persoanaAutentificata);
        }
    }

    public void handleAccept() throws SQLException {
        this.serviceComenziActive.comandaAcceptata(comandaCurentaPreluata);
    }

    public void handleDecline(){
        label1.setVisible(false);
        nr_masina.setVisible(false);
        label2.setVisible(false);
        locatia.setVisible(false);
        label3.setVisible(false);
        accept.setVisible(false);
        decline.setVisible(false);
        comandaCurentaPreluata = null;
    }

    @Override
    public void update(ComandaActivaNoua comandaActivaNoua) throws SQLException {
        if (comandaActivaNoua.getNewData() != null && comandaActivaNoua.getOldData() != null && comandaActivaNoua.getNewData().getPersoana().getId().equals(persoanaAutentificata.getId())) {
            label1.setVisible(true);
            nr_masina.setVisible(true);
            label2.setVisible(true);
            locatia.setVisible(true);
            label3.setVisible(true);
            accept.setVisible(true);
            decline.setVisible(true);
            nr_masina.setText(comandaActivaNoua.getNewData().getSofer().getIndicativMasina());
            locatia.setText(comandaActivaNoua.getOldData());
            comandaCurentaPreluata = comandaActivaNoua.getNewData();
        }
        else if(comandaActivaNoua.getOldData()==null && comandaActivaNoua.getNewData()!=null){
            label1.setVisible(false);
            nr_masina.setVisible(false);
            label2.setVisible(false);
            locatia.setVisible(false);
            label3.setVisible(false);
            accept.setVisible(false);
            decline.setVisible(false);
            comandaCurentaPreluata = null;
        }

    }
}
