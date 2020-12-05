package com.testing.cuatroEnLinea.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "jugadores")
public class Jugador {

	@Id
	@Column(name = "numero")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long numero;

	@JsonProperty("nombre")
	private String nombre;

	@JsonProperty("puntaje")
	private Integer score;

	@JsonProperty("ganados")
	private Integer victorias;

	@JsonProperty("perdidos")
	private Integer derrotas;
	
	@JsonProperty("empatados")
	private Integer empates;



	public Jugador(String nombre) {
		this.nombre = nombre;
		this.score = 0;
		this.empates = 0;
		this.victorias = 0;
		this.derrotas = 0;
		this.empates = 0;
	}

	public Integer getDerrotas() {
		return derrotas;
	}

	public void setDerrotas(Integer derrotas) {
		this.derrotas = derrotas;
	}

	public Integer getVictorias() {
		return victorias;
	}

	public void setVictorias(Integer victorias) {
		this.victorias = victorias;
	}

	public Integer getEmpates() {
		return empates;
	}

	public void setEmpates(Integer empates) {
		this.empates = empates;
	}

	public Long getID() {
		return numero;
	}
	public void setID(Long numero) {
		this.numero = numero;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}

	public void resetScore (){
		this.score = 0;
	}


	public Jugador(){
		
	}
}
