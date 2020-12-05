package com.testing.cuatroEnLinea.model;

import java.util.ArrayList;
import java.util.List;

public class Copa {
    private List<PartidoCopa> partidos;

    public Copa(){
        this.partidos = new ArrayList<>();

    }    


    public void setPartidos(List<PartidoCopa> partidos) {
        this.partidos = partidos;
    }

    
    public void getPartidos(List<PartidoCopa> partidos) {
        this.partidos = partidos;
    }
}
