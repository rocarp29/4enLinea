package com.testing.cuatroEnLinea;

import java.util.ArrayList;
import java.util.List;

import com.testing.cuatroEnLinea.model.Copa;
import com.testing.cuatroEnLinea.model.Jugador;
import com.testing.cuatroEnLinea.model.Liga;
import com.testing.cuatroEnLinea.model.PartidoCopa;
import com.testing.cuatroEnLinea.model.PartidoLiga;
import com.testing.cuatroEnLinea.service.CopaService;
import com.testing.cuatroEnLinea.service.LigaService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan ({"com.testing.cuatroEnLinea.*"})
//@SpringBootApplication(scanBasePackages = {"boot.registration"} , exclude = JpaRepositoriesAutoConfiguration.class)
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class CuatroEnLineaApplication {

	

	public static void main(String[] args) {
		SpringApplication.run(CuatroEnLineaApplication.class, args);
		

	}

	@Bean
	public CommandLineRunner initData2
			(CopaService copaService, LigaService ligaService) {
		return (args) -> {

			Copa copa = new Copa();
			Liga liga = new Liga();

			final String ESTADO_PENDIENTE = "pendiente";
			final String ESTADO_TERMINADO = "terminado";


			Jugador jugadorUno = new Jugador("JugadorUno");
			Jugador jugadorDos = new Jugador("JugadorDos");
			Jugador jugadorTres = new Jugador("JugadorTres");
			Jugador jugadorCuatro = new Jugador("JugadorCuatro");
			/*Jugador jugadorCinco = new Jugador("JugadorCinco");
			Jugador jugadorSeis = new Jugador("JugadorSeis");
			Jugador jugadorSiete = new Jugador("JugadorSiete");
			Jugador jugadorOcho = new Jugador("JugadorOcho");*/
			

			List<Jugador> jugadores = new ArrayList<>();
			jugadores.add(jugadorUno);
			jugadores.add(jugadorDos);
			jugadores.add(jugadorTres);
			jugadores.add(jugadorCuatro);
			/*jugadores.add(jugadorCinco);
			jugadores.add(jugadorSeis);
			jugadores.add(jugadorSiete);
			jugadores.add(jugadorOcho);*/

			ligaService.setJugadoresPorDefecto(jugadores);
			ligaService.resetLiga();
			
			copaService.setJugadoresPorDefecto(jugadores);
			copaService.setCdadPartidosCuartos(4);
			copaService.setCdadPartidosSemi(2);
			copaService.setCdadPartidosFinal(1);
			List<String> nombreJugadoreSemi = new ArrayList<>();
			List<String> nombreJugadoresFinal = new ArrayList<>();

			copaService.setJugadoresSemi(nombreJugadoreSemi);
			copaService.setJugadoresFinal(nombreJugadoresFinal);
			copaService.resetCopa();

			for(int i = 0; i < (jugadores.size()); i++ ){
				for(int k = 0; k < (jugadores.size() - 1); k ++ ){
					if(jugadores.get(i) != jugadores.get(k)){
						if(ligaService.getPartidoByJugadores(jugadores.get(i), jugadores.get(k)) == null){
							ligaService.createPartido(new PartidoLiga(jugadores.get(i).getNombre(), 
								jugadores.get(k).getNombre(), ESTADO_PENDIENTE, "liga"));
							System.out.println("partido creado" + jugadores.get(i).getNombre() +" vs "+ jugadores.get(k).getNombre());
						}
					}
					
				}
			}



		};
	}

}
