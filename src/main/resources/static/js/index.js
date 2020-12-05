var url = window.location.href;

window.onload = function() {
    localStorage.clear();

    $("#botonJugarPartido").on('click', jugarPartido);
    $("#botonJugarLiga").on('click', jugarLiga);
    $("#botonJugarCopa").on('click', jugarCopa);


};

function jugarPartido() {
    if ((localStorage.getItem("estado") != "terminado") && (localStorage.getItem("tipoJuego") == 'liga' ||
            localStorage.getItem("tipoJuego") == 'copa')) {
        alertaBorrado(localStorage.getItem("tipoJuego"));
    } else {
        inicializarPartido();

    }
};

function inicializarPartido() {
    borrarLocal();
    var jugadorUno = "Jugador A";
    var jugadorDos = "Jugador B";
    localStorage.setItem('tipoJuego', 'partidoUnico');
    localStorage.setItem('jugadorUno', jugadorUno);
    localStorage.setItem('jugadorDos', jugadorDos);
    window.location.href = "/partido";
}

function alertaBorrado(instancia) {
    var mensaje = "Ya existe " + instancia + " en curso. Si prosigue, " + instancia + " se borrara ";
    //alert(mensaje);

    //$('#modal-div-borrado').addClass('show-modal');
    if (confirm(mensaje)) {
        if (instancia == "copa") {
            //Que hacer en caso de querer jugar copa
            borrarLocal();
            inicializarCopa();
        } else if (instancia == "liga") {
            borrarLocal();
            inicializarLiga();

        } else {
            //Que hacer en caso de querer jugar partido unico
            borrarLocal();
            inicializarPartido();
        }
    } else {
        return;
    }

}


function jugarLiga() {
    if ((localStorage.getItem("estado") != "terminado") && (localStorage.getItem("tipoJuego") == 'partido' ||
            localStorage.getItem("tipoJuego") == 'copa')) {
        alertaBorrado();
    } else {
        inicializarLiga();

    }
};

function jugarCopa() {
    if ((localStorage.getItem("estado") != "terminado") && (localStorage.getItem("tipoJuego") == 'partido' ||
            localStorage.getItem("tipoJuego") == 'liga')) {
        alertaBorrado();
    } else {
        inicializarCopa();

    }
};

function inicializarLiga() {
    var data = {};
    localStorage.setItem('tipoJuego', 'liga');

    $.ajax({
        async: false,
        type: "GET",
        data: data,
        contentType: 'application/json',
        dataType: "json",
        url: url + "/liga/partidoAJugar",
        success: function(data) {
            console.log(JSON.stringify(data));
            //TODO: ver que hace en final de la liga.

            localStorage.setItem('idPartido', data.id);
            localStorage.setItem('jugadorUno', data.jugadorUno);
            localStorage.setItem('jugadorDos', data.jugadorDos);
            localStorage.setItem('estado', data.estado);
            localStorage.setItem('instanciaCopa', data.instanciaCopa);




        },
        error: function(e) {
            console.log("error");
        }
    });
    window.location.href = "/partido";

};

function inicializarCopa() {


    var data = {};

    $.ajax({
        async: false,
        type: "GET",
        data: data,
        contentType: 'application/json',
        dataType: "json",
        url: url + "/copa/partidoAJugar",
        success: function(data) {
            if (data.jugadorUno != null) {
                console.log(JSON.stringify(data));
                localStorage.setItem('idPartido', data.id);
                localStorage.setItem('jugadorUno', data.jugadorUno);
                localStorage.setItem('jugadorDos', data.jugadorDos);
                localStorage.setItem('estado', data.estado);
                localStorage.setItem('instanciaCopa', data.instanciaCopa);
                localStorage.setItem("tipoJuego", "copa");
                localStorage.setItem('resultadoJugadorUno', null);
                localStorage.setItem('resultadoJugadorDos', null);
            } else {
                borrarCopaExistente();
            }

        },
        error: function(e) {
            console.log("error");
        }
    });
    window.location.href = "/partido";

};

function borrarLocal() {
    window.localStorage.clear();
};

function borrarCopaExistente() {
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