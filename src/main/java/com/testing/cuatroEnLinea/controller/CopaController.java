package com.testing.cuatroEnLinea.controller;

import java.util.List;

import com.testing.cuatroEnLinea.model.PartidoCopa;
import com.testing.cuatroEnLinea.service.CopaService;
import com.testing.cuatroEnLinea.service.LigaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/copa")
public class CopaController {

	@Autowired
	private CopaService copaService;

	
	
	@GetMapping("")
	public String mainView() {
		return "copa";
	}


	@PostMapping(value = "/guardarPartido", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> postPartido(@RequestBody PartidoCopa partido) {
		copaService.savePartido(partido);
        //Logica para buscar partido siguiente.
        String instanciaActual = partido.getInstanciaCopa();
        System.out.println("instancia Actual "+ instanciaActual);
		String proxInstancia = copaService.getProximaInstancia(instanciaActual);

		return new ResponseEntity<>(proxInstancia, HttpStatus.CREATED);


	}

	@ResponseBody
	@GetMapping(value = "/all")
	public List<PartidoCopa> findAll(){
		return copaService.getPartidos();
	}

	
	@DeleteMapping(value = "/deleteAll")
	public void deleteAll(){
		copaService.deletePartidos();
	}
	
	@ResponseBody
	@GetMapping(value = "/{id}")
	public PartidoCopa findById(@PathVariable Long id) {
		PartidoCopa partido = copaService.getPartidoById(id);
		return partido;

	}

	@ResponseBody
	@GetMapping(value = "/partidoAJugar")
	public PartidoCopa partidoAJugar() {
		List<PartidoCopa> partidos = copaService.getPartidos();
		for (int i = 0; i < partidos.size(); i++) {
			String estadoPartido = partidos.get(i).getEstado();

			if (estadoPartido == "pendiente") {
				return partidos.get(i);
			}
		}
		PartidoCopa partidoFinal = new PartidoCopa("FINAL","FINAL", "FINAL");
		return partidoFinal;

	}

	
	@ResponseBody
	@PostMapping(value = "/resetCopa")
	public void resetCopa() {
		copaService.resetCopa();
		
		
	}
	
	

}
