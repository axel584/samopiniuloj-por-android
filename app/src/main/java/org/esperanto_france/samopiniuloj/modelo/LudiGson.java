package org.esperanto_france.samopiniuloj.modelo;

public class LudiGson {

    private String respondo;
    private String kialo;
    private int eraro_id;
    private Integer[] registritaj;
    private String[] kialoj;
    private Integer[] proponoj;

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

    public Integer[] getRegistritaj() {
        return registritaj;
    }

    public void setRegistritaj(Integer[] registritaj) {
        this.registritaj = registritaj;
    }

    public String[] getKialoj() {
        return kialoj;
    }

    public void setKialoj(String[] kialoj) {
        this.kialoj = kialoj;
    }

    public Integer[] getProponoj() {
        return proponoj;
    }

    public void setProponoj(Integer[] proponoj) {
        this.proponoj = proponoj;
    }
}
