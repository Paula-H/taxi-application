package com.example.taximetrie.domain;

public class Sofer extends Persoana {
    private String indicativMasina;

    public Sofer(String username, String nume, String indicativMasina) {
        super(username, nume);
        this.indicativMasina = indicativMasina;
    }

    public String getIndicativMasina() {
        return indicativMasina;
    }

    public void setIndicativMasina(String indicativMasina) {
        this.indicativMasina = indicativMasina;
    }

    @Override
    public String toString() {
        String nume;
        return "Sofer{" +
                "id=" + id +
                ", username='" + super.getUsername() + '\'' +
                ", nume='" + super.getNume() + '\'' +
                ", indicativMasina='" + indicativMasina + '\'' +
                '}';
    }
}