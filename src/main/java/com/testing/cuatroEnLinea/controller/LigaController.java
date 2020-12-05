package com.testing.cuatroEnLinea.controller;

import java.util.ArrayList;
import java.util.List;

import com.testing.cuatroEnLinea.model.Jugador;
import com.testing.cuatroEnLinea.model.PartidoLiga;
import com.testing.cuatroEnLinea.service.LigaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/liga")
public class LigaController {

	@Autowired
	private LigaService ligaService;

	@GetMapping("")
	public String mainView() {
		return "liga";
	}

	@ResponseBody
	@PostMapping(value = "/guardarPartido", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> postPartido(@RequestBody PartidoLiga partido) {

		
		partido.setEstado("terminado");
		if (ligaService.savePartido(partido)) {
			return new ResponseEntity<>("Partido Actualizado", HttpStatus.CREATED);
		} else
			return new ResponseEntity<>("Error al guardar partido", HttpStatus.BAD_REQUEST);

		

	}

	@ResponseBody
	@PostMapping(value = "/{id}")
	public ResponseEntity<Object> postPartidoById(@RequestParam Long id, @RequestBody PartidoLiga partido) {
		Long idPartido = id;
		//String jUno = partido.getJugadorUno();
		//String jDos = partido.getJugadorDos();

		PartidoLiga partidoEnBase = ligaService.getPartidoById(idPartido);

		if (partidoEnBase != null) {
			// si el aprtido existe pero no seta terminado
			if (partido.getEstado() == "terminado" && partidoEnBase.getEstado() != "terminado") {

				partidoEnBase.setResultadoJugadorUno(partido.getResultadoJugadorUno());
				partidoEnBase.setResultadoJugadorDos(partido.getResultadoJugadorDos());
				partidoEnBase.setEstado(partido.getEstado());

				return new ResponseEntity<>(partidoEnBase, HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>("Partido Ya terminado", HttpStatus.BAD_REQUEST);
			}

		} else {

			return new ResponseEntity<>("Partido No existe", HttpStatus.BAD_REQUEST);

		}
	}

	@ResponseBody
	@GetMapping(value = "/puntajes")
	public List<PartidoLiga> getPuntajes() {
		return ligaService.getPartidos();
	}

	@ResponseBody
	@GetMapping(value = "/all")
	public List<PartidoLiga> findAll() {
		return ligaService.getPartidos();
	}

	@ResponseBody
	@GetMapping(value = "/allTerminados")
	public List<PartidoLiga> findAllTerminados() {
		List<PartidoLiga> partidos = ligaService.getPartidos();
		List<PartidoLiga> tempList = new ArrayList<>();
		for (int i = 0; i < partidos.size(); i++) {
			String estadoPartido = partidos.get(i).getEstado();

			if (estadoPartido == "terminado") {
				tempList.add(partidos.get(i));
			}
		}
		return tempList;
	}

	@ResponseBody
	@PostMapping(value = "/resetLiga")
	public void resetearLiga() {
		ligaService.resetLiga();
	}


	@ResponseBody
	@PostMapping(value = "/desempate")
	public void createPartidosDesempate(@RequestBody ArrayList<Jugador> listaJugadores) {
		
		ligaService.addPartidosEmpate(listaJugadores);
	}

	@ResponseBody
	@GetMapping(value = "/jugadores")
	public List<Jugador> getJugadoresActuales() {
		return ligaService.getJugadores();

	}

	@ResponseBody
	@GetMapping(value = "/partidoAJugar")
	public PartidoLiga partidoAJugar() {
		List<PartidoLiga> partidos = ligaService.getPartidos();
		for (int i = 0; i < partidos.size(); i++) {
			String estadoPartido = partidos.get(i).getEstado();

			if (estadoPartido == "pendiente") {
				return partidos.get(i);
			}
		}
		PartidoLiga partidoFinal = new PartidoLiga("FINAL","FINAL", "FINAL");
		return partidoFinal;

	}

	@ResponseBody
	@GetMapping(value = "/{id}")
	public PartidoLiga findById(@PathVariable Long id) {
		PartidoLiga partido = ligaService.getPartidoById(id);
		return partido;

	}

	@PostMapping(value = "")
	public ResponseEntity<Object> updateliga(@PathVariable Long id, @RequestBody PartidoLiga partidoRequest) {
		PartidoLiga partido = ligaService.getPartidoById(id);
		if (partido != null) {
			// si el aprtido existe pero no seta terminado
			if (partidoRequest.getEstado() == "terminado") {

				partido.setResultadoJugadorUno(partidoRequest.getResultadoJugadorUno());
				partido.setResultadoJugadorDos(partidoRequest.getResultadoJugadorDos());
				partido.setEstado(partidoRequest.getEstado());

				return new ResponseEntity<>(partido, HttpStatus.OK);
			} else {
				if ((partidoRequest.getEstado().isEmpty()) && !(partidoRequest.getJugadorUno() == null)
						&& !(partidoRequest.getJugadorDos() == null))
					partido.setJugadorUno(partidoRequest.getJugadorUno());
				partido.setJugadorDos(partidoRequest.getJugadorDos());
				partido.setEstado(partidoRequest.getEstado());
				ligaService.createPartido(partido);
				return new ResponseEntity<>(partido, HttpStatus.CREATED);
			}

		} else {
			if (!(partidoRequest.getEstado().isEmpty()) && !(partidoRequest.getJugadorUno() == null)
					&& !(partidoRequest.getJugadorDos() == null)) {

				PartidoLiga partidoCreado = partidoRequest;
				ligaService.createPartido(partidoCreado);
				return new ResponseEntity<>(partidoCreado, HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>("Faltan datos elementales", HttpStatus.BAD_REQUEST);
			}
		}
	}

}
