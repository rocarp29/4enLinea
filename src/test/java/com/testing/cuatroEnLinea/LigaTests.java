package com.testing.cuatroEnLinea;



import java.util.ArrayList;
import java.util.Arrays;

import com.testing.cuatroEnLinea.model.Jugador;
import com.testing.cuatroEnLinea.model.PartidoLiga;
import com.testing.cuatroEnLinea.repository.LigaRepository;
import com.testing.cuatroEnLinea.service.LigaService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

/***
 * Clase de pruebas unitarias de la clase ligaService, la ppal para la logica de la Liga.
 */
@RunWith(SpringRunner.class)
public class LigaTests {
	//Constructor vacio - requisito de Junit.
	public LigaTests(){

	}

	
	@InjectMocks
	private LigaService ligaService;

	
	@Mock
	private LigaRepository ligaRepository;

	private Jugador jugadorPruebaUno;
	private Jugador jugadorPruebaDos;
	private Jugador jugadorPruebaTres;
	private ArrayList<Jugador> listaJugadorPrueba;
	private ArrayList<PartidoLiga> listaPartidosLiga;

	private PartidoLiga partidoPrueba;

	@Before
	public void crearDatosPrueba(){
		this.partidoPrueba = new PartidoLiga("JugadorPruebaUno", "JugadorPruebaDos","pendiente");
		this.jugadorPruebaUno = new Jugador("JugadorPruebaUno");
		this.jugadorPruebaDos = new Jugador("JugadorPruebaDos");
		this.jugadorPruebaTres = new Jugador("JugadorPruebaTres");

		this.listaJugadorPrueba = new ArrayList<Jugador>();
		this.listaJugadorPrueba.add(jugadorPruebaUno);
		this.listaJugadorPrueba.add(jugadorPruebaDos);


		this.listaPartidosLiga = new ArrayList<PartidoLiga>();
	}


	@Test
	public void probarPartidoCopaGanado(){
		PartidoLiga partidoPrueba = new PartidoLiga("JugadorPruebaUno", "JugadorPruebaDos","terminado", "final");
		partidoPrueba.setResultadoJugadorDos("victoria");
		partidoPrueba.setResultadoJugadorUno("derrota");
		Assert.assertEquals("victoria", partidoPrueba.getResultadoJugadorDos());
		Assert.assertEquals("derrota", partidoPrueba.getResultadoJugadorUno());
	}

	@Test
	public void probarPartidoCopaEmpate(){
		PartidoLiga partidoPrueba = new PartidoLiga("JugadorPruebaUno", "JugadorPruebaDos","terminado", "final");
		partidoPrueba.setResultadoJugadorDos("empate");
		partidoPrueba.setResultadoJugadorUno("empate");
		Assert.assertEquals("empate", partidoPrueba.getResultadoJugadorDos());
		Assert.assertEquals("empate", partidoPrueba.getResultadoJugadorUno());
	}

	@Test
	public void probarPartidoCopaJugadores(){
		partidoPrueba.setJugadorUno("j1");
		partidoPrueba.setJugadorDos("j2");
		Assert.assertEquals("j1", partidoPrueba.getJugadorUno());
		Assert.assertEquals("j2", partidoPrueba.getJugadorDos());
	}



	@Test
    public void testlistaJugadores() {
		ligaService.setlistaJugadores(listaJugadorPrueba);
		Assert.assertEquals(true, Arrays.equals(ligaService.getJugadores().toArray(),listaJugadorPrueba.toArray()));
	}
		
	@Test
    public void testListaPartidos() {
		Assert.assertEquals(ligaRepository.findAll(),ligaService.getPartidos() );
	}


	//Probamos que ande bien el filtro.
	@Test
	public void testCheckJugadorPartido() {

		Assert.assertEquals(true,ligaService.checkJugadorPartido(this.partidoPrueba, this.jugadorPruebaUno));
		//Assert.assertTrue(ligaService.checkJugadorPartido(partidoPrueba, jugadorPruebaTres));

	}

	@Test
	public void testCheckJugadorPartidoErroneo() {

		Assert.assertEquals(false,ligaService.checkJugadorPartido(this.partidoPrueba, this.jugadorPruebaTres));
	}
	
	@Test
    public void testEmpate() {
		ligaService.addPartidosEmpate(listaJugadorPrueba);
		Assert.assertEquals(listaJugadorPrueba, ligaService.getJugadores());

	}

	@Test
    public void testJugadoresPorDefecto() {
		ligaService.setJugadoresPorDefecto(listaJugadorPrueba);
		Assert.assertEquals(listaJugadorPrueba, ligaService.getJugadoresPorDefecto());
	}

}
