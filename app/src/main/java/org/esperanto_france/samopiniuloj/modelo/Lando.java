package org.esperanto_france.samopiniuloj.modelo;

/**
 * Created by axel on 28/04/15.
 */
public class Lando {

    private String kodo;
    private String nomo;


    public Lando(String kodo, String nomo) {
        this.kodo = kodo;
        this.nomo = nomo;
    }

    public String getKodo() {
        return kodo;
    }

    public void setKodo(String kodo) {
        this.kodo = kodo;
    }

    public String getNomo() {
        return nomo;
    }

    public void setNomo(String nomo) {
        this.nomo = nomo;
    }
}
