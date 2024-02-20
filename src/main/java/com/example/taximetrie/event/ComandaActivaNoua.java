package com.example.taximetrie.event;

import com.example.taximetrie.domain.Comanda;
import com.example.taximetrie.domain.Entity;

public class ComandaActivaNoua implements Event{
    private Comanda newData;
    private String oldData;

    public ComandaActivaNoua(String oldData, Comanda newData){
        this.newData = newData;
        this.oldData = oldData;
    }


    public Comanda getNewData() {
        return newData;
    }

    public void setNewData(Comanda newData) {
        this.newData = newData;
    }

    public String getOldData() {
        return oldData;
    }

    public void setOldData(String oldData) {
        this.oldData = oldData;
    }
}
