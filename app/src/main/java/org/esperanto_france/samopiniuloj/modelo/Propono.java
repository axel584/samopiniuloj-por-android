package org.esperanto_france.samopiniuloj.modelo;


public class Propono {

    private Integer id;
    private Integer ludanto_id;
    private Integer vorto_id;
    private String propono;
    private Integer poento;
    private Integer vico;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLudanto_id() {
        return ludanto_id;
    }

    public void setLudanto_id(Integer ludanto_id) {
        this.ludanto_id = ludanto_id;
    }

    public Integer getVorto_id() {
        return vorto_id;
    }

    public void setVorto_id(Integer vorto_id) {
        this.vorto_id = vorto_id;
    }

    public String getPropono() {
        return propono;
    }

    public void setPropono(String propono) {
        this.propono = propono;
    }

    public Integer getPoento() {
        return poento;
    }

    public void setPoento(Integer poento) {
        this.poento = poento;
    }

    public Integer getVico() {
        return vico;
    }

    public void setVico(Integer vico) {
        this.vico = vico;
    }
}
