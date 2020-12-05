package com.testing.cuatroEnLinea.service;

import com.testing.cuatroEnLinea.model.Jugador;
import com.testing.cuatroEnLinea.model.PartidoLiga;
import com.testing.cuatroEnLinea.repository.LigaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

@Service
public class LigaService {

    @Autowired
    private LigaRepository partidoRepository;

    private List<Jugador> listaJugadores;

    private List<Jugador> jugadoresPorDefecto;

        /**
     * Resetea los partidos de la liga y jugadores. TODO: Como un repositorio.
     */
    public void resetLiga() {
        partidoRepository.deleteAll();
        listaJugadores = jugadoresPorDefecto;
        for (int i = 0; i < (jugadoresPorDefecto.size()); i++) {
            for (int k = 0; k < (jugadoresPorDefecto.size() - 1); k++) {
                if (jugadoresPorDefecto.get(i) != jugadoresPorDefecto.get(k)) {
                    if (getPartidoByJugadores(jugadoresPorDefecto.get(i), jugadoresPorDefecto.get(k)) == null) {
                        createPartido(new PartidoLiga(jugadoresPorDefecto.get(i).getNombre(),
                                jugadoresPorDefecto.get(k).getNombre(), "pendiente", "liga"));
                        System.out.println("partido creado" + jugadoresPorDefecto.get(i).getNombre() + " vs "
                                + jugadoresPorDefecto.get(k).getNombre());
                    }
                }

            }
        }
    }

    public List<Jugador> getJugadores() {
        return listaJugadores;
    }

    public void setlistaJugadores(List<Jugador> listaJugadores) {
        this.listaJugadores = listaJugadores;
    }

    /**
     * Obtiene todos los partidos, sin filtros.
     */

    public List<PartidoLiga> getPartidos() {
        return partidoRepository.findAll();
    }

    /**
     * OJO!!! Borra todos los partidos
     */
    public void deletePartidos() {
        partidoRepository.deleteAll();
    }

    public void createPartido(PartidoLiga partido) {
        partidoRepository.save(partido);
    }

    /**
     * Obtiene partido via ID.
     */

    public PartidoLiga getPartidoById(Long id) {
        PartidoLiga partido = new PartidoLiga();
        try {
            partido = partidoRepository.getOne(id);
        } catch (EntityNotFoundException enfe) {
            partido = null;
        }
        return partido;
    }

    /**
     * Filtra los partidos de acuerdo a si esta el jugador involucrado o no
     */

    public PartidoLiga getPartidoByJugadores(Jugador jugadorUno, Jugador jugadorDos) {
        List<PartidoLiga> partidos = partidoRepository.findAll();

        List<PartidoLiga> partidoFilter = partidos.stream().filter(
                partido -> ((checkJugadorPartido(partido, jugadorUno) && checkJugadorPartido(partido, jugadorDos))))
                .collect(Collectors.toList());

        if (partidoFilter.size() > 0 && !partidoFilter.isEmpty()) {
            return partidoFilter.get(0);
        } else {
            return null;
        }

    }

    /**
     * Chequea si el jugador esta en un partido o no.
     */

    public boolean checkJugadorPartido(PartidoLiga partidoAFiltrar, Jugador jugador) {

        if ((partidoAFiltrar.getJugadorUno().equals(jugador.getNombre()))
                || (partidoAFiltrar.getJugadorDos().equals(jugador.getNombre()))) {
            return true;
        } else
            return false;

    }

    /**
     * Guarda un partido.
     */

    public boolean savePartido(PartidoLiga partido) {
        Long idPartido = partido.getIdPartidoLiga();
        //System.out.println(partido);
        if (partidoRepository.existsById(idPartido)) {
            System.out.println("Partido existe");

            PartidoLiga partidoResult = partidoRepository.save(partido);
            return true;
        } else
            return false;
    }

    /**
     * Itera sobre lista de jugadores para generar nuevas combinaciones para
     * desempatar partidos.
     * 
     * @param listaJugadores
     */
    public void addPartidosEmpate(ArrayList<Jugador> listaJugadores) {
        this.listaJugadores = listaJugadores;
        for (int i = 0; i < listaJugadores.size(); i++) {
            for (int k = 1; k < listaJugadores.size(); k++) {
                String jugadorUno = listaJugadores.get(i).getNombre();
                String jugadorDos = listaJugadores.get(k).getNombre();
                if (jugadorUno != jugadorDos) {
                    PartidoLiga partidoACrear = new PartidoLiga(jugadorUno,
                            jugadorDos, "pendiente", "liga");
                    if(getPartidoByJugadores(listaJugadores.get(i), listaJugadores.get(k)) != null){
                        partidoRepository.save(partidoACrear);
                    }
                        
                            
                }   
                    

            }
           
        }
    }

    /**
     * Setear los jugadores para arrancar el juego de liga
     */
    public void setJugadoresPorDefecto(List<Jugador> listaDefecto) {
        this.jugadoresPorDefecto = listaDefecto;
    }

    /**
     * Obtiene lista de jugadores por defecto.
     * 
     * @return Devuelve lista de jugadores por defecto.
     */
    public List<Jugador> getJugadoresPorDefecto() {
        return jugadoresPorDefecto;
    }


}
