package com.testing.cuatroEnLinea.service;


import com.testing.cuatroEnLinea.model.Jugador;
import com.testing.cuatroEnLinea.model.PartidoCopa;
import com.testing.cuatroEnLinea.repository.CopaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;


@Service
public class CopaService{

    @Autowired
    private CopaRepository partidoRepository;

    
    private List<Jugador> listaJugadores;

    private List<Jugador> jugadoresPorDefecto;

    private List<String> jugadoresSemi;

    private List<String> jugadoresFinal;
    
    private int cdadPartidosCuartos = 4;
    private int cdadPartidosSemi = 2;
    private int cdadPartidosFinal = 1;
    

    public List<PartidoCopa> getPartidos() {
        return partidoRepository.findAll();
    }

    public List<PartidoCopa> getPartidosByInstancia(String instancia) {
        List<PartidoCopa> listaPartidosGuardados = partidoRepository.findAll();
        List<PartidoCopa> listaPartidoInstancia = new ArrayList<>();

        for (int i = 0; i < listaPartidosGuardados.size(); i ++){
            PartidoCopa partidoAEvaluar = listaPartidosGuardados.get(i);
            System.out.println("i + size "+i + " , " + listaPartidosGuardados.size() + " @ " +
                "id " + partidoAEvaluar.getIdPartidoCopa() + " , " + partidoAEvaluar.getInstanciaCopa());
            if(partidoAEvaluar.getInstanciaCopa() == instancia ) {
                listaPartidoInstancia.add(partidoAEvaluar);//
                System.out.println("@S@  ID PARTIDO AGREGADO:" + partidoAEvaluar.getIdPartidoCopa());

            }
        }
        System.out.println("@S@  TAMAÑO LISTA :" + listaPartidoInstancia.size());
        return listaPartidoInstancia;
        
    }

    public List<PartidoCopa> getPartidosTerminados(String instancia) {
        List<PartidoCopa> listaPartidosGuardados = getPartidos();
        List<PartidoCopa> listaPartidoInstancia = new ArrayList<>();

        for (int i = 0; i < listaPartidosGuardados.size(); i ++){
            PartidoCopa partidoAEvaluar = listaPartidosGuardados.get(i);
            if(partidoAEvaluar.getInstanciaCopa() == instancia){
                listaPartidoInstancia.add(partidoAEvaluar);
            }
        }
        return listaPartidoInstancia;
        
    } 

    public void deletePartidos(){
        partidoRepository.deleteAll();
    }

    public String savePartido(PartidoCopa partido) {
        /*System.out.println("partido @@@@" + partido.toString());*/
        Long partidoId = partido.getIdPartidoCopa();
        //System.out.println("partidoId + @@@@" + partidoId);
        partidoRepository.save(partido);        
        // Guardo jugador en la lista de cada instancia.
        
        String instanciaPartidoActual = partido.getInstanciaCopa();
        String ganadorPartido = getGanadorPartido(partido);
        if(instanciaPartidoActual.matches("cuartos") && partido.getEstado().matches("terminado")){
            System.out.println("ganador "+ganadorPartido);
            jugadoresSemi.add(ganadorPartido);
        }
        if(instanciaPartidoActual.matches("semi") && partido.getEstado().matches("terminado")){
            jugadoresFinal.add(ganadorPartido);
        }

        //logica para obtebner generar partidos de la proxima instancia. 
        String proximaInstancia = getProximaInstancia(instanciaPartidoActual);
        System.out.println("@@@@@" + proximaInstancia);
        
        if(!(proximaInstancia.equalsIgnoreCase(instanciaPartidoActual))){
            if(proximaInstancia.equalsIgnoreCase("semi")){
                for(int i = 0; i < cdadPartidosSemi; i++){
                    System.out.println("semi" + i);
                    PartidoCopa partidoAGuardar = new PartidoCopa(jugadoresSemi.get(2 * i), jugadoresSemi.get((2 * i)+1), "pendiente", "semi");
                    partidoRepository.save(partidoAGuardar);
                    
                }
                return "semi";                    
            }
            if(proximaInstancia.equalsIgnoreCase("final")){
                for(int i = 0; i < cdadPartidosFinal; i++){
                    System.out.println("final"+i + jugadoresFinal.get(2 * i) + jugadoresFinal.get((2 * i) + 1));
                    PartidoCopa partidoAGuardar = new PartidoCopa(jugadoresFinal.get(2 * i), jugadoresFinal.get((2 * i)+1), "pendiente", "final");
                    partidoRepository.save(partidoAGuardar);
                }
                 return "final";
            }
            if(proximaInstancia.equalsIgnoreCase("terminado")){
                return "terminado";
            }

        }
        return instanciaPartidoActual;

    }
    public void createPartido(PartidoCopa partido) {
            partidoRepository.save(partido);
    }

    public boolean existePartidoById(Long id){
        System.out.println("id @@@" + id);
        PartidoCopa partido = new PartidoCopa();
        try {
             partido= partidoRepository.getOne(id);
             return true;
        }catch(EntityNotFoundException enfe){
            return false;
        }
        

    }


    public PartidoCopa getPartidoById(Long id){
        System.out.println("id @@@" + id);
        PartidoCopa partido = new PartidoCopa();
        try {
             partido= partidoRepository.getOne(id);
        }catch(EntityNotFoundException enfe){
            partido = null;
        }
        return partido;

    }

    public List<Jugador> getListaJugadores() {
        return listaJugadores;
    }

    public void setListaJugadores(List<Jugador> listaJugadores) {
        this.listaJugadores = listaJugadores;
    }

    public List<Jugador> getJugadoresPorDefecto() {
        return jugadoresPorDefecto;
    }

    public void setJugadoresPorDefecto(List<Jugador> jugadoresPorDefecto) {
        this.jugadoresPorDefecto = jugadoresPorDefecto;
    }

    public boolean checkJugadorPartido(PartidoCopa partidoAFiltrar, Jugador jugador) {

        if ((partidoAFiltrar.getJugadorUno().equals(jugador.getNombre()))
                || (partidoAFiltrar.getJugadorDos().equals(jugador.getNombre()))) {
            return true;
        } else
            return false;

    }


    public String getProximaInstancia(String instanciaActual){
        int cdadPartidos = 0;
        String proximaInstancia = "";
        System.out.println("instanciaActual" + instanciaActual );
        List<PartidoCopa> listaPartidos = getPartidos();
        List<PartidoCopa> partidosInstancia = new ArrayList<>(); 
        for(PartidoCopa partido:listaPartidos){
            String instanciaEvaluada = partido.getInstanciaCopa();
            /*System.out.println("evaluacion para lista: " +partido.getIdPartidoCopa() +  instanciaEvaluada + " ;  " + instanciaActual + " estado: " + partido.getEstado() + "true" 
                + partido.getEstado().equalsIgnoreCase("terminado") );*/
            if(instanciaActual.equalsIgnoreCase(instanciaEvaluada) && partido.getEstado().equalsIgnoreCase("terminado")){
                partidosInstancia.add(partido);
            }
        }
        //usar equals ignopre case
        //System.out.println("partidoInstanciasize: " + partidosInstancia.size());        
        if(instanciaActual.equalsIgnoreCase("cuartos")){
            System.out.println("cuartos: " + cdadPartidosCuartos +"tamaño "+ partidosInstancia.size());
            if(partidosInstancia.size() >= cdadPartidosCuartos){
                return "semi";
            }else{
                return instanciaActual;
            }
        }
        if(instanciaActual.equalsIgnoreCase("semi")){
            //System.out.println("semi partidos: " + cdadPartidosSemi + " " + (partidosInstancia.size() == cdadPartidosSemi));
            if(partidosInstancia.size() >= cdadPartidosSemi){
                return "final";
            }else{
                return instanciaActual;
            }
        }
        if(instanciaActual.equalsIgnoreCase("final")){
            //System.out.println("final Partidos: " + cdadPartidosFinal + " " + (partidosInstancia.size() == cdadPartidosSemi));
            if(partidosInstancia.size() >= cdadPartidosFinal){
                return "terminado";
            }else{
                return instanciaActual;
            }
        }else{
            return "error";
        }

    }

	public int getCdadPartidosCuartos() {
		return cdadPartidosCuartos;
	}

	public void setCdadPartidosCuartos(int cdadPartidosCuartos) {
		this.cdadPartidosCuartos = cdadPartidosCuartos;
	}

	public int getCdadPartidosSemi() {
		return cdadPartidosSemi;
	}

	public void setCdadPartidosSemi(int cdadPartidosSemi) {
		this.cdadPartidosSemi = cdadPartidosSemi;
	}

	public int getCdadPartidosFinal() {
		return cdadPartidosFinal;
	}

	public void setCdadPartidosFinal(int cdadPartidosFinal) {
		this.cdadPartidosFinal = cdadPartidosFinal;
    }
    
    public String getGanadorPartido(PartidoCopa partido){
        if(partido.getResultadoJugadorUno().matches("victoria")){
            return partido.getJugadorUno();
        }
        if(partido.getResultadoJugadorDos().matches("victoria")){
            return partido.getJugadorDos();
        }
        else return "pendiente";
    }

    public void resetCopa(){
        this.listaJugadores = this.jugadoresPorDefecto;
        this.jugadoresSemi.clear();
        this.jugadoresFinal.clear();
        partidoRepository.deleteAll();
        for(int i = 0; i < (listaJugadores.size()/2); i++){
            System.out.println("Jugadores: " + listaJugadores.get(i).getNombre() + listaJugadores.get(i+1).getNombre() );
            PartidoCopa partidoInstance = new PartidoCopa(listaJugadores.get(2*i).getNombre(), listaJugadores.get((2*i)+1).getNombre(),"pendiente","cuartos");
            //como autoincrement se guarda en cache, se setea el ID.
            partidoInstance.setIdPartidoCopa((long) i+1);
            createPartido(partidoInstance);
            
        }
    }

    public List<String> getJugadoresSemi() {
        return jugadoresSemi;
    }

    public void setJugadoresSemi(List<String> jugadoresSemi) {
        this.jugadoresSemi = jugadoresSemi;
    }

    public List<String> getJugadoresFinal() {
        return jugadoresFinal;
    }

    public void setJugadoresFinal(List<String> jugadoresFinal) {
        this.jugadoresFinal = jugadoresFinal;
    }




    
}
