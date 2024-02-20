package com.example.taximetrie.domain;

import java.time.LocalDateTime;

public class Comanda extends Entity<Long> {
    private Persoana persoana;
    private Sofer sofer;
    private LocalDateTime data;
    private String locatie;
    public Comanda(Persoana persoana, Sofer sofer, LocalDateTime data,String locatie) {
        this.persoana = persoana;
        this.sofer = sofer;
        this.data = data;
        this.locatie = locatie;
    }

    public Persoana getPersoana() {
        return persoana;
    }

    public void setPersoana(Persoana persoana) {
        this.persoana = persoana;
    }

    public Sofer getSofer() {
        return sofer;
    }

    public void setSofer(Sofer sofer) {
        this.sofer = sofer;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public String getLocatie() {
        return locatie;
    }

    public void setLocatie(String locatie) {
        this.locatie = locatie;
    }

    // Getters and setters for persoana, taximetrist, and data

    @Override
    public String toString() {
        if(getSofer()!=null)
            return "Ride for " +getPersoana().getNume()+ " to "+getLocatie()+".";
        return "Active ride (no driver) for "+getPersoana().getNume()+" to "+getLocatie()+".";
    }
}