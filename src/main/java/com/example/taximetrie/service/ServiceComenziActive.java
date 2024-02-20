package com.example.taximetrie.service;

import com.example.taximetrie.domain.Comanda;
import com.example.taximetrie.domain.Persoana;
import com.example.taximetrie.event.ComandaActivaNoua;
import com.example.taximetrie.observer.Observable;
import com.example.taximetrie.observer.Observer;
import com.example.taximetrie.repository.PersoanaRepository;
import com.example.taximetrie.repository.RepositoryFactory;
import com.example.taximetrie.repository.RepositoryType;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ServiceComenziActive implements Observable<ComandaActivaNoua> {
    private Service service = Service.getInstance();
    private ArrayList<Comanda> comenziActive = new ArrayList<>();
    private static ServiceComenziActive instance = null;

    private ServiceComenziActive() throws SQLException {
    }

    public static ServiceComenziActive getInstance() throws SQLException {
        if(instance == null){
            instance = new ServiceComenziActive();
        }
        return instance;
    }
    public ArrayList<Comanda> getComenziActive(){
        return this.comenziActive;
    }

    public void comandaAcceptata(Comanda comanda) throws SQLException {
        service.saveComanda(comanda);
        Comanda toRemove=null;
        for (Comanda com:comenziActive) {
            if(com.getPersoana()==comanda.getPersoana() && com.getLocatie().equals(comanda.getLocatie()) && com.getData().isEqual(comanda.getData()))
                toRemove = com;
        }
        comenziActive.remove(toRemove);
        notifyObservers(new ComandaActivaNoua(null,comanda));
    }

    public void trimiteSpreClient(Comanda comanda, String timpDeAsteptare){
        notifyObservers(new ComandaActivaNoua(timpDeAsteptare,comanda));
    }

    public void addNewComandaActiva(String locatie, Persoana persoana){
        Comanda comanda = new Comanda(persoana,null, LocalDateTime.now(),locatie);
        comenziActive.add(comanda);
        notifyObservers(new ComandaActivaNoua(null,null));
    }
    List<Observer<ComandaActivaNoua>> observers = new ArrayList<>();
    @Override
    public void addObserver(Observer<ComandaActivaNoua> e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer<ComandaActivaNoua> e) {
        observers.remove(e);
    }

    @Override
    public void notifyObservers(ComandaActivaNoua t) {
        observers.stream().forEach(x-> {
            try {
                x.update(t);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
