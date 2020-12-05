const URL = window.location.origin;
const URL_ALL = URL + "/liga/all";
const URL_PARTIDO = URL + "/partido";
const URL_JUGADORES_ACTUALES = URL + "/liga/jugadores"
const URL_EMPATADOS = URL + "/liga/desempate"
const URL_PROX_PARTIDO = URL + "/liga/partidoAJugar";
const URL_RESET_LIGA = URL + "/liga/resetLiga";
window.onload = function() {
    //$('#modal-liga-empatada').addClass('close-modal');

    $("#botonVolverPartido").on("click", volverPartido);

    var datosPartidos = [];
    $.ajax({
        async: false,
        type: "GET",
        contentType: 'application/json',
        dataType: "json",
        url: URL_ALL,
        success: function(data) {
            datosPartidos = data;

        },
        error: function(e) {}
    });
    var arrayJugadores = [];

    $.ajax({
        async: false,
        type: "GET",
        contentType: 'application/json',
        dataType: "json",
        url: URL_JUGADORES_ACTUALES,
        success: function(data) {
            arrayJugadores = data;
            // fill data to Modal Body
            //fillData(data);
        },
        error: function(e) {
            //fillData(null);
        }
    });


    for (jugador of arrayJugadores) {
        jugador.puntaje = 0;
        jugador.ganados = 0;
        jugador.empatados = 0;
        jugador.perdidos = 0;
        for (partido of datosPartidos) {
            if (partido.jugadorUno == jugador.nombre) {
                if (partido.resultadoJugadorUno == "victoria") {
                    jugador.puntaje += 3;
                    jugador.ganados += 1;
                }
                if (partido.resultadoJugadorUno == "empate") {
                    jugador.puntaje += 1;
                    jugador.empatados += 1;
                }
                if (partido.resultadoJugadorUno == "derrota") {
                    jugador.puntaje += 0;
                    jugador.perdidos += 1;
                }
            }
            if (partido.jugadorDos == jugador.nombre) {
                if (partido.resultadoJugadorDos == "victoria") {
                    jugador.puntaje += 3;
                    jugador.ganados += 1;
                }
                if (partido.resultadoJugadorDos == "empate") {
                    jugador.puntaje += 1;
                    jugador.empatados += 1;
                }
                if (partido.resultadoJugadorDos == "derrota") {
                    jugador.puntaje += 0;
                    jugador.perdidos += 1;
                }
            }

        }

    }
    //creo la tabla dinamicamente via inyeccion html
    arrayJugadores.forEach((jugador, index) => createTable(jugador, index));


    if (!existeProximoPartido()) {
        showModalLigaTerminada();
        var listaJugadoresGanadores = [];
        var puntajeMaximo = 0;

        var maximoPuntaje = Math.max.apply(null, arrayJugadores.map(function(jugador) {
            return jugador.puntaje;
        }));
        console.log("Maximo punatje" + maximoPuntaje);

        for (jugador of arrayJugadores) {
            if (jugador.puntaje >= maximoPuntaje) {
                console.log("gano jugador: " + jugador.nombre);
                jugador.puntaje = 0;
                jugador.ganados = 0;
                jugador.empatados = 0;
                jugador.perdidos = 0;
                listaJugadoresGanadores.push(jugador);
            }
        }
        if (listaJugadoresGanadores.length > 1) {
            empateJugadores(listaJugadoresGanadores);
        } else {
            alert("Hay ganador. Vea los resultados , la liga sera reseteada.");
            resetLiga();
        }
    }


}


function createTable(jugador, index) {
    var nombreJugador = jugador.nombre;
    var puntajeJugador = jugador.puntaje;
    var victoriasJugador = jugador.ganados;
    var empateJugador = jugador.empatados;
    var derrotaJugador = jugador.perdidos;

    var tabla = document.getElementById("tabla");
    //para ordenar, cambiar insert row.
    var row = tabla.insertRow(-1);
    var cell1 = row.insertCell(0);
    var cell2 = row.insertCell(1);
    var cell3 = row.insertCell(2);
    var cell4 = row.insertCell(3);
    var cell5 = row.insertCell(4);

    cell1.innerHTML = nombreJugador;
    cell2.innerHTML = puntajeJugador;
    cell3.innerHTML = victoriasJugador;
    cell4.innerHTML = empateJugador;
    cell5.innerHTML = derrotaJugador;

}

function volverPartido() {
    window.location.href = "/";

}

function empateJugadores(arrayJugadoresEmpatados) {
    var jsonParseado = JSON.stringify(arrayJugadoresEmpatados);
    console.log("JSON Desempate" + jsonParseado);
    $.ajax({
        async: false,
        type: "POST",
        headers: {
            'Content-Type': 'application/json'
        },
        contentType: "application/json; charset=utf-8",
        data: jsonParseado,
        dataType: "text",
        url: URL_EMPATADOS,
        success: function(data) {

            var mensajeEmpate = "Liga empatada  - Se debe jugar liga de desempate";
            alert(mensajeEmpate);
            localStorage.clear();
        },
        error: function(e) {
            console.log("Error de guardado de jugadores" + JSON.stringify(e));
        }
    });


}

function existeProximoPartido() {
    var data = {};
    var existeProxPartido = true;

    $.ajax({
        async: false,
        type: "GET",
        data: data,
        contentType: 'application/json',
        dataType: "json",
        url: URL_PROX_PARTIDO,
        success: function(data) {
            console.log(JSON.stringify(data));
            //TODO: ver que hace en final de la liga.
            if (data.id != null) {
                console.log("¡Existe prox partido!");
                existeProxPartido = true;
            } else {
                console.log("¡No Existe prox partido!");
                existeProxPartido = false;

            }

        },
        error: function(e) {
            console.log("error");
        }
    });
    return existeProxPartido;

}

function resetLiga() {
    var data = JSON.stringify({});

    $.ajax({
        async: false,
        type: "POST",
        headers: {
            'Content-Type': 'application/json'
        },
        contentType: "application/json; charset=utf-8",
        data: data,
        dataType: "text",
        url: URL_RESET_LIGA,
        success: function(data) {
            console.log("Liga reseteada");
        },
        error: function(e) {
            console.log("Error de guardado de jugadores" + JSON.stringify(e));
        }
    });

}

function showModalLigaTerminada() {
    //alert("Liga termianda - Ver resultados")


}


function showModalLigaEmpatada() {
    //('#modal-liga-empatada').addClass('show-modal');
    ('#modal-liga-ligaEmpatadaButton').on('click', function() {
        var data = {};
        var existeProxPartido = true;

        $.ajax({
            async: false,
            type: "GET",
            data: data,
            contentType: 'application/json',
            dataType: "json",
            url: URL_PROX_PARTIDO,
            success: function(data) {
                console.log(JSON.stringify("Partido post empate" + data));
                localStorage.clear();
                localStorage.setItem('jugadorUno', data.jugadorUno);
                localStorage.setItem('jugadorDos', data.jugadorDos);
                localStorage.setItem('estado', data.estado);
                localStorage.setItem('idPartido', data.id);
                localStorage.setItem('tipoJuego', tipoJuego);
                localStorage.setItem('instanciaCopa', tipoJuego);
                localStorage.setItem('resultadoJugadorUno', null);
                localStorage.setItem('resultadoJugadorDos', null);
                window.location.href = URL_PARTIDO;


            },
            error: function(e) {
                console.log("error");
            }
        });

    });
}