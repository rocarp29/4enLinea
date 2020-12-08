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
public class JugadorTests {
	//Constructor vacio - requisito de Junit.

	public JugadorTests(){
	}

	@Test
	public void probarNombre(){
		Jugador jugadorPrueba = new Jugador("Prueba");
		jugadorPrueba.setNombre("nombrePrueba");
		Assert.assertNotEquals("Prueba",jugadorPrueba.getNombre() );
		Assert.assertEquals("nombrePrueba", jugadorPrueba.getNombre());

	}

	
	

}
