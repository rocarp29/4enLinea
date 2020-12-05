const URL = window.location.origin;
const URL_ALL = URL + "/copa/all";
const URL_PARTIDO = URL + "/partido";
const URL_JUGADORES_ACTUALES = URL + "/copa/jugadores"
const URL_EMPATADOS = URL + "/copa/desempate"
const URL_PROX_PARTIDO = URL + "/copa/partidoAJugar";
const URL_RESET_COPA = URL + "/copa/resetCopa";
window.onload = function() {
    //$('#modal-copa-empatada').addClass('close-modal');

    $("#botonVolverPartido").on("click", volverPartido);
    $("#botonVolverMenu").on("click", volverMenu);

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



    var listaPartidosCuartos = [];
    var listaPartidosSemi = [];
    var listaPartidoFinal = []

    for (partido of datosPartidos) {
        if (partido.instanciaCopa == "cuartos") {
            listaPartidosCuartos.push(partido);
        }
        if (partido.instanciaCopa == "semi") {
            listaPartidosSemi.push(partido);
        }
        if (partido.instanciaCopa == "final") {
            listaPartidoFinal.push(partido);
        }

    }

    createFilaInstancia("cuartos", listaPartidosCuartos);
    createFilaInstancia("semi", listaPartidosSemi);
    createFilaInstancia("final", listaPartidoFinal);



    if (!existeProximoPartido()) {
        var ganador = getGanador(listaPartidoFinal[0]);
        var tabla = document.getElementById("tabla");
        //para ordenar, cambiar insert row.

        var row = tabla.insertRow(-1);
        var cell1 = row.insertCell(0);
        var cell2 = row.insertCell(1);
        var cell3 = row.insertCell(2);
        var cell4 = row.insertCell(3);
        var cell5 = row.insertCell(4);

        cell1.innerHTML = "Ganador:";
        cell2.innerHTML = "";
        cell3.innerHTML = ganador;
        cell4.innerHTML = "";
        cell5.innerHTML = "";

        //document.getElementById("ganador").innerHTML("Ganador : " + ganador);
        alert("Hay ganador - " + ganador + " Vea los resultados, la copa sera reseteada.");
        resetCopa();
    }


}

function getGanador(partido) {
    var j1 = partido.jugadorUno;
    var j2 = partido.jugadorUno;
    var ganador = "";
    if (partido.resultadoJugadorUno == "victoria") {
        ganador = j1;
    }
    if (partido.resultadoJugadorDos == "victoria") {
        ganador = j2;
    }
    return ganador;
}

function createFilaInstancia(instancia, listaInstancia) {
    var jugadorUno = partido.jugadorUno;
    var jugadorDos = partido.jugadorDos;
    var resultadoJUno = partido.resultadoJugadorUno;
    var resultadoJDos = partido.resultadoJugadorDos;

    var tabla = document.getElementById("tabla");
    //para ordenar, cambiar insert row.

    var row = tabla.insertRow(-1);
    var cell1 = row.insertCell(0);
    var cell2 = row.insertCell(1);
    var cell3 = row.insertCell(2);
    var cell4 = row.insertCell(3);
    var cell5 = row.insertCell(4);

    var texto = ["Aun no jugado", "Aun no jugado", "Aun no jugado", "Aun no jugado"];
    for (var i = 0; i < listaInstancia.length; i++) {
        if (instancia == "cuartos") {
            var textoCuartos = listaInstancia[i].jugadorUno + " vs " + listaInstancia[i].jugadorDos;
            texto[i] = textoCuartos;
        }
        if (instancia == "semi") {
            var textoSemi = listaInstancia[i].jugadorUno + " vs " + listaInstancia[i].jugadorDos;
            texto[i] = textoSemi;
        }
        if (instancia == "final") {
            var textoFinal = listaInstancia[i].jugadorUno + " vs " + listaInstancia[i].jugadorDos;
            texto[i] = textoFinal;


        }

    }

    if (instancia == "cuartos") {
        cell1.innerHTML = "Cuartos:";
        cell2.innerHTML = texto[1];
        cell3.innerHTML = texto[2];
        cell4.innerHTML = texto[3];
        cell5.innerHTML = texto[3];

    }

    if (instancia == "semi") {
        cell1.innerHTML = "Semifinal:";
        cell2.innerHTML = " ";
        cell3.innerHTML = texto[0];
        cell4.innerHTML = texto[1];;
        cell5.innerHTML = " ";

    }
    if (instancia == "final") {
        cell1.innerHTML = "Final:";
        cell2.innerHTML = " ";
        cell3.innerHTML = texto[0];
        cell4.innerHTML = " ";
        cell5.innerHTML = " ";
    }



}



function createTable(partido, index) {
    var jugadorUno = partido.jugadorUno;
    var jugadorDos = partido.jugadorDos;
    var resultadoJUno = partido.resultadoJugadorUno;
    var resultadoJDos = partido.resultadoJugadorDos;

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
    window.location.href = "/partido";

}

function volverMenu() {
    window.location.href = "/";

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
            //TODO: ver que hace en final de la copa.
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

function resetCopa() {
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
        url: URL_RESET_COPA,
        success: function(data) {
            console.log("Copa reseteada");
        },
        error: function(e) {
            console.log("Error de guardado de jugadores" + JSON.stringify(e));
        }
    });

}