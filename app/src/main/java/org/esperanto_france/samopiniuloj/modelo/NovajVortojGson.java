package org.esperanto_france.samopiniuloj.modelo;

public class NovajVortojGson {

    private String respondo;
    private String kialo;
    private int eraro_id;
    private Vorto[] vortoj;


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

    public Vorto[] getVortoj() {
        return vortoj;
    }

    public void setVortoj(Vorto[] vortoj) {
        this.vortoj = vortoj;
    }
}
