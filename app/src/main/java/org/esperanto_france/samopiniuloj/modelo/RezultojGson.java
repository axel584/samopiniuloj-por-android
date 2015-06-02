package org.esperanto_france.samopiniuloj.modelo;

import java.util.List;

public class RezultojGson {

    private String respondo;
    private String kialo;
    private int eraro_id;
    private int tago;
    private int monato;
    private int jaro;
    private List<Propono> proponoj;
    private List<Ludanto> ludantoj;

    public String getRespondo() {
        return respondo;
    }

    public void setRespondo(String respondo) {
        this.respondo = respondo;
    }

    public String getKialo() {
        return kialo;
    }

    public void setKialo(String kialo) {
        this.kialo = kialo;
    }

    public int getEraro_id() {
        return eraro_id;
    }

    public void setEraro_id(int eraro_id) {
        this.eraro_id = eraro_id;
    }

    public int getTago() {
        return tago;
    }

    public void setTago(int tago) {
        this.tago = tago;
    }

    public int getMonato() {
        return monato;
    }

    public void setMonato(int monato) {
        this.monato = monato;
    }

    public int getJaro() {
        return jaro;
    }

    public void setJaro(int jaro) {
        this.jaro = jaro;
    }

    public List<Propono> getProponoj() {
        return proponoj;
    }

    public void setProponoj(List<Propono> proponoj) {
        this.proponoj = proponoj;
    }

    public List<Ludanto> getLudantoj() {
        return ludantoj;
    }

    public void setLudantoj(List<Ludanto> ludantoj) {
        this.ludantoj = ludantoj;
    }
}
