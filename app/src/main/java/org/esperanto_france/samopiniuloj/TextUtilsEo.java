package org.esperanto_france.samopiniuloj;

public class TextUtilsEo {

    final static String[][] replacements = {{"cx", "ĉ"},{"sx", "ŝ"},{"gx", "ĝ"},{"jx", "ĵ"},{"hx", "ĥ"},{"ux", "ŭ"},{"Cx", "Ĉ"},{"Sx", "Ŝ"},{"Gx", "Ĝ"},{"Jx", "Ĵ"},{"Hx", "Ĥ"},{"Ux", "Ŭ"}};


    final static String x2utf(String texte) {

        String sortie = texte;
        for(String[] replacement: replacements) {
            sortie = sortie.replace(replacement[0], replacement[1]);
        }
        return sortie;
    }

    final static String utf2x(String texte) {

        String sortie = texte;
        for(String[] replacement: replacements) {
            sortie = sortie.replace(replacement[1], replacement[0]);
        }
        return sortie;
    }

}
