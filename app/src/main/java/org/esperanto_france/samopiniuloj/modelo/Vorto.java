package org.esperanto_france.samopiniuloj.modelo;


public class Vorto {

    private Integer id;

    private Integer tago;
    private Integer monato;
    private Integer jaro;

    private String vorto;
    private String dosiero;

    public Integer getTago() {
        return tago;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTago(Integer tago) {
        this.tago = tago;
    }

    public Integer getMonato() {
        return monato;
    }

    public void setMonato(Integer monato) {
        this.monato = monato;
    }

    public Integer getJaro() {
        return jaro;
    }

    public void setJaro(Integer jaro) {
        this.jaro = jaro;
    }

    public String getVorto() {
        return vorto;
    }

    public void setVorto(String vorto) {
        this.vorto = vorto;
    }

    public String getDosiero() {
        return dosiero;
    }

    public void setDosiero(String dosiero) {
        this.dosiero = dosiero;
    }
}
