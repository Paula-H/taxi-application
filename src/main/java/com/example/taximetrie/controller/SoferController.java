package com.example.taximetrie.controller;

import com.example.taximetrie.domain.Comanda;
import com.example.taximetrie.domain.Persoana;
import com.example.taximetrie.domain.Sofer;
import com.example.taximetrie.event.ComandaActivaNoua;
import com.example.taximetrie.observer.Observer;
import com.example.taximetrie.service.Service;
import com.example.taximetrie.service.ServiceComenziActive;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.time.LocalDate;

public class SoferController implements Observer<ComandaActivaNoua> {
    Sofer soferAutentificat;
    Persoana celMaiFidelClient;
    @FXML
    ListView<Persoana> clienti = new ListView<>();
    @FXML
    ListView<Comanda> comenzi = new ListView<>();

    @FXML
    ListView<Comanda> comenziActive = new ListView<>();
    @FXML
    Label clientFidel = new Label();
    @FXML
    Label medieComenzi = new Label();
    @FXML
    DatePicker datePicker = new DatePicker();
    @FXML
    TextField timpAsteptare = new TextField();

    ObservableList<Persoana> clientiLista =  FXCollections.observableArrayList();
    ObservableList<Comanda> comenziLista =  FXCollections.observableArrayList();
    ObservableList<Comanda> comenziActiveLista =  FXCollections.observableArrayList();

    ObservableList<Comanda> filteredComenzi = FXCollections.observableArrayList();

    private Service service = Service.getInstance();

    private ServiceComenziActive serviceComenziActive = ServiceComenziActive.getInstance();

    public SoferController() throws SQLException {
    }

    public void setSoferAutentificat(Sofer sofer) throws SQLException {
        this.soferAutentificat = sofer;
        serviceComenziActive.addObserver(this);
        initializeModel();
        initializeComenziActive();
    }

    public void initializeModel() throws SQLException {
        clientiLista.clear();
        comenziLista.clear();
        this.service.clientiSofer(soferAutentificat).forEach(clientiLista::add);
        this.service.comenziPentruSofer(soferAutentificat).forEach(comenziLista::add);
        celMaiFidelClient = this.service.celMaiFidelClient(soferAutentificat);
        clientFidel.setText("");
        if(celMaiFidelClient!=null)
            clientFidel.setText(celMaiFidelClient.getNume());
        else{
            clientFidel.setText("No one");
        }
        medieComenzi.setText(String.valueOf(this.service.comenziPeZi(soferAutentificat)));
        //clienti.getItems().clear();
        clienti.setItems(clientiLista);
        //comenzi.getItems().clear();
        comenzi.setItems(comenziLista);

        datePicker.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
                if(newValue != null){
                    try {
                        filterComenziByDate(newValue);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

    }

    private void filterComenziByDate(LocalDate selectedDate) throws SQLException {
        filteredComenzi.clear();
        for (Comanda comanda : comenziLista) {
            if (comanda.getData().toLocalDate().isEqual(selectedDate)) {
                filteredComenzi.add(comanda);
            }
        }
        comenzi.setItems(filteredComenzi);
    }

    public void handleOnoreazaComanda(){
        Comanda selectedComanda = comenziActive.getSelectionModel().getSelectedItem();

        if (selectedComanda != null) {
            if(!timpAsteptare.getText().isEmpty()){
                selectedComanda.setSofer(soferAutentificat);
                this.serviceComenziActive.trimiteSpreClient(selectedComanda,timpAsteptare.getText());
            }
        }

    }

    public void initializeComenziActive(){
        this.comenziActiveLista.clear();
        this.serviceComenziActive.getComenziActive().forEach(comenziActiveLista::add);
        comenziActive.setItems(comenziActiveLista);
    }

    @Override
    public void update(ComandaActivaNoua comandaActivaNoua) throws SQLException {
        if(comandaActivaNoua.getNewData()==null && comandaActivaNoua.getOldData()==null) {
            initializeComenziActive();
            initializeModel();
        }
        else if(comandaActivaNoua.getOldData()==null && comandaActivaNoua.getNewData()!=null){
            initializeComenziActive();
            initializeModel();
        }
    }
}
