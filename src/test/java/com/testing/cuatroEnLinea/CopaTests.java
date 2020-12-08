package com.testing.cuatroEnLinea;

import java.util.ArrayList;

import com.testing.cuatroEnLinea.model.Jugador;
import com.testing.cuatroEnLinea.model.PartidoCopa;
import com.testing.cuatroEnLinea.service.CopaService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

/***
 * Clase de pruebas unitarias de la clase copaService, la ppal para la logica de la Copa.
 */
@RunWith(MockitoJUnitRunner.class)
public class CopaTests {
	//Constructor vacio - requisito de Junit.
	public CopaTests(){

	}

	
	@InjectMocks
	private CopaService copaService;

	private Jugador jugadorPruebaUno;
	private Jugador jugadorPruebaDos;
	private Jugador jugadorPruebaTres;
	private ArrayList<Jugador> listaJugadorPrueba;

	private PartidoCopa partidoPruebaCuartos;
	private PartidoCopa partidoPruebaSemi;
	private PartidoCopa partidoPruebaTerminado;
	private ArrayList<PartidoCopa> listaPartidosCopa;



	@Before
	public void crearDatosPrueba(){
		this.partidoPruebaCuartos = new PartidoCopa("JugadorPruebaUno", "JugadorPruebaDos","pendiente", "cuartos");
		this.partidoPruebaSemi = new PartidoCopa("JugadorPruebaUno", "JugadorPruebaDos","pendiente", "semi");
		this.partidoPruebaTerminado = new PartidoCopa("JugadorPruebaUno", "JugadorPruebaDos","terminado", "final");
		this.partidoPruebaTerminado.setResultadoJugadorUno("victoria");
		this.partidoPruebaTerminado.setResultadoJugadorDos("derrota");
		this.listaPartidosCopa = new ArrayList<PartidoCopa>();
				

		this.jugadorPruebaUno = new Jugador("JugadorPruebaUno");
		this.jugadorPruebaDos = new Jugador("JugadorPruebaDos");
		this.jugadorPruebaTres = new Jugador("JugadorPruebaTres");

		this.listaJugadorPrueba = new ArrayList<Jugador>();
		this.listaJugadorPrueba.add(jugadorPruebaUno);
		this.listaJugadorPrueba.add(jugadorPruebaDos);

	}

	@Test
	public void probarNombre(){
		this.jugadorPruebaUno.setNombre("NombrePrueba");
		Assert.assertEquals(jugadorPruebaUno.getNombre(), "NombrePrueba");
	}

	@Test
	public void probarPartidoCopaGanado(){
		PartidoCopa partidoPrueba = new PartidoCopa("JugadorPruebaUno", "JugadorPruebaDos","terminado", "final");
		partidoPrueba.setResultadoJugadorDos("victoria");
		partidoPrueba.setResultadoJugadorUno("derrota");
		Assert.assertEquals("victoria", partidoPrueba.getResultadoJugadorDos());
		Assert.assertEquals("derrota", partidoPrueba.getResultadoJugadorUno());
	}

	@Test
	public void probarPartidoCopaJugadores(){
		partidoPruebaCuartos.setJugadorUno("j1");
		partidoPruebaCuartos.setJugadorDos("j2");
		Assert.assertEquals("j1", partidoPruebaCuartos.getJugadorUno());
		Assert.assertEquals("j2", partidoPruebaCuartos.getJugadorDos());
	}




	//Probamos que ande bien el filtro.
	@Test
	public void testCheckJugadorPartido() {

		Assert.assertEquals(true,copaService.checkJugadorPartido(this.partidoPruebaSemi, this.jugadorPruebaUno));
		
	}
	

}
