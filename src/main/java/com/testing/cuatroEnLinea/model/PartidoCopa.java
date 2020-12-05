package com.testing.cuatroEnLinea.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "PartidoCopa")
public class PartidoCopa {


    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="idCopa")
    @JsonProperty("id")
    private Long idPartidoCopa;

   /* @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Jugador jugadorUno;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Jugador jugadorDos;
    */
    @JsonProperty("jugadorUno")
    @Column(name="JugadorUno")
    private String jugadorUno;

    @JsonProperty("jugadorDos")
    @Column(name="JugadorDos")
    private String jugadorDos;

    @JsonProperty("estado")
    @Column(name = "Estado")
    private String estado;

    @JsonProperty("resultadoJugadorUno")
    @Column(name = "ResultadoJug1")
    private String resultadoJugadorUno;

    @JsonProperty("resultadoJugadorDos")
    @Column(name = "ResultadoJug2")
    private String resultadoJugadorDos;

       
    @JsonProperty("instanciaCopa")
    @Column(name = "instanciaCopa")
    private String instanciaCopa = "cuartos";





    public PartidoCopa(){
    }



    public PartidoCopa(String jugadorUno, String jugadorDos,String estado){
        this.jugadorUno = jugadorUno;
        this.jugadorDos = jugadorDos;
        this.estado = estado;

    }

    public PartidoCopa(String jugadorUno, String jugadorDos,String estado, String instancia){
        this.jugadorUno = jugadorUno;
        this.jugadorDos = jugadorDos;
        this.estado = estado;
        this.instanciaCopa = instancia;
    }

    public PartidoCopa(String jugadorUno, String jugadorDos,String estado, String instancia,
        String resultadoJugadorUno, String resultadoJugadorDos){
            this.jugadorUno = jugadorUno;
            this.jugadorDos = jugadorDos;
            this.estado = estado;
            this.resultadoJugadorUno = resultadoJugadorUno;
            this.resultadoJugadorUno = resultadoJugadorDos;
            this.instanciaCopa = instancia;
    }
        
    public Long getIdPartidoCopa() {
        return idPartidoCopa;
    }

    public String getResultadoJugadorUno() {
        return resultadoJugadorUno;
    }

    public String getResultadoJugadorDos(){
        return resultadoJugadorDos;
    }

    public void setIdPartidoCopa(Long idPartidoCopa) {
        this.idPartidoCopa = idPartidoCopa;
    }

    public String getJugadorUno() {
        return jugadorUno;
    }

    public void setJugadorUno(String jugadorUno) {
        this.jugadorUno = jugadorUno;
    }

    public String getJugadorDos() {
        return jugadorDos;
    }

    public void setJugadorDos(String jugadorDos) {
        this.jugadorDos = jugadorDos;
    }

   public void setEstado(String estado){
       this.estado = estado;
    } 

    public String getEstado(){
        return this.estado;
    }

    public void setResultadoJugadorUno(String resultadoJugadorUno){
        this.resultadoJugadorUno = resultadoJugadorUno;
    }

    public void setResultadoJugadorDos(String resultadoJugadorDos){
        this.resultadoJugadorDos = resultadoJugadorDos;
    }
    public String getInstanciaCopa() {
        return instanciaCopa;
    }

    public void setInstanciaCopa(String instanciaCopa) {
        this.instanciaCopa = instanciaCopa;
    }

    public String toString(){
        return "id: "+ idPartidoCopa + "j1" + jugadorUno + " j2 " + jugadorDos + 
            "estado: " + estado + "instancia : " + instanciaCopa + 
                "res: " + resultadoJugadorUno + "; " + resultadoJugadorDos;


    }




    
    
}
