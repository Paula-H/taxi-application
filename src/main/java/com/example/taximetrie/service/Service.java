package com.example.taximetrie.service;

import com.example.taximetrie.domain.Comanda;
import com.example.taximetrie.domain.Persoana;
import com.example.taximetrie.domain.Sofer;
import com.example.taximetrie.event.EventImplementation;
import com.example.taximetrie.observer.Observable;
import com.example.taximetrie.observer.Observer;
import com.example.taximetrie.repository.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

public class Service {
    private RepositoryFactory repositoryFactory = new RepositoryFactory();
    private PersoanaRepository persoanaRepository = (PersoanaRepository) repositoryFactory.createRepository(RepositoryType.Persoana);
    private SoferRepository soferRepository = (SoferRepository) repositoryFactory.createRepository(RepositoryType.Sofer);
    private ComandaRepository comandaRepository = (ComandaRepository) repositoryFactory.createRepository(RepositoryType.Comanda);
    private static Service instance=null;
    private Service() throws SQLException {
    }

    public static Service getInstance() throws SQLException {
        if(instance==null){
            instance = new Service();
        }
        return instance;
    }

    public void saveComanda(Comanda comanda) throws SQLException {
        this.comandaRepository.save(comanda);
    }

    public Sofer CheckSoferUsername(String username) throws SQLException {
        ArrayList<Sofer> soferi = new ArrayList<>();
        this.soferRepository.findAll().forEach(soferi::add);
        for (Sofer sofer:soferi) {
            if(sofer.getUsername().equals(username))
                return sofer;
        }
        return null;
    }

    public Persoana CheckPersoanaUsername(String username) throws SQLException {
        ArrayList<Persoana> persoane = new ArrayList<>();
        this.persoanaRepository.findAll().forEach(persoane::add);
        for (Persoana persoana:persoane) {
            if(persoana.getUsername().equals(username))
                return persoana;
        }
        return null;
    }

    public ArrayList<Persoana> clientiSofer(Sofer sofer) throws SQLException {
        Set<Persoana> clienti = new HashSet<>();
        this.comandaRepository.findAll().forEach(comanda -> {
            if(comanda.getSofer().getId().equals(sofer.getId()))
                clienti.add(comanda.getPersoana());
        });
        ArrayList<Persoana> clientiFinal = new ArrayList<>();
        clienti.forEach(clientiFinal::add);
        return clientiFinal;
    }

    public Persoana celMaiFidelClient(Sofer sofer) throws SQLException {
        Map<Persoana, Integer> mapClienti = new HashMap<>();

        clientiSofer(sofer).forEach(persoana -> {
            mapClienti.put(persoana, 0);
        });

        Integer maxCurse = 0;
        Persoana clientFidel = null;

        Set<Comanda> comenzi = new HashSet<>();
        this.comandaRepository.findAll().forEach(comenzi::add);
        if(comenzi.isEmpty()){
            return clientFidel;
        }

        this.comandaRepository.findAll().forEach(comanda -> {
            if(comanda.getSofer().getId().equals(sofer.getId())) {
                Integer oldValue = mapClienti.get(comanda.getPersoana());
                int newValue = (oldValue != null) ? oldValue + 1 : 1;
                mapClienti.put(comanda.getPersoana(), newValue);
            }
        });



        for (Map.Entry<Persoana, Integer> entry : mapClienti.entrySet()) {
            if (entry.getValue() > maxCurse) {
                maxCurse = entry.getValue();
                clientFidel = entry.getKey();
            }
        }

        return clientFidel;
    }

    public Float comenziPeZi(Sofer sofer) throws SQLException {
        LocalDateTime threeMonthsAgo = LocalDateTime.now().minusMonths(3);
        ArrayList<Comanda> comenziUltimele3Luni = new ArrayList<>();
        this.comandaRepository.findAll().forEach(comanda -> {
            if(comanda.getSofer().getId().equals(sofer.getId())){
                if(comanda.getData().isAfter(threeMonthsAgo)){
                    comenziUltimele3Luni.add(comanda);
                }
            }
        });
        return comenziUltimele3Luni.size()/90.0F;
    }

    public ArrayList<Comanda> comenziPentruSofer(Sofer sofer) throws SQLException {
        ArrayList<Comanda> comenzi = new ArrayList<>();
        this.comandaRepository.findAll().forEach(comanda -> {
            if(comanda.getSofer().getId().equals(sofer.getId()))
                comenzi.add(comanda);
        });
        return comenzi;
    }

}
