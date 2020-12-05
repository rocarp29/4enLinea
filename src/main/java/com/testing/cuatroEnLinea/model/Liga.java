package com.testing.cuatroEnLinea.model;

import java.util.ArrayList;
import java.util.List;

public class Liga {

    private List<PartidoLiga> partidos;
    
    public Liga(){
        this.partidos = new ArrayList<>();
    }
    public void setPartidos(List<PartidoLiga> partidos) {
        this.partidos = partidos;
    }

    
    public void getPartidos(List<PartidoLiga> partidos) {
        this.partidos = partidos;
    }

    


    

}