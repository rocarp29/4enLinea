var url = window.location.origin;
//guardo resultado
function sendResultado(jugadorUno, jugadorDos, resultadoJugadorUno, resultadoJugadorDos) {
    var id = parseInt(localStorage.getItem('idPartido'));

    var jsonAEnviarLiga = {
        "id": id,
        "estado": "terminado",
        "jugadorUno": jugadorUno,
        "jugadorDos": jugadorDos,
        "resultadoJugadorUno": resultadoJugadorUno,
        "resultadoJugadorDos": resultadoJugadorDos
    }
    var jsonAEnviarCopa = {
        "id": id,
        "estado": "terminado",
        "jugadorUno": jugadorUno,
        "jugadorDos": jugadorDos,
        "resultadoJugadorUno": resultadoJugadorUno,
        "resultadoJugadorDos": resultadoJugadorDos,
        "instanciaCopa": localStorage.getItem("instanciaCopa")

    }

    // var jsonParseado = JSON.parse(jsonAEnviar);
    console.log("Req partido terminado:" + JSON.stringify(jsonAEnviarLiga));
    var jsonParseado = {};

    var urlPost = "";
    if (localStorage.getItem('tipoJuego') == "liga") {
        urlPost = "/liga";
        jsonParseado = JSON.stringify(jsonAEnviarLiga);
    }
    if (localStorage.getItem('tipoJuego') == "copa") {
        urlPost = "/copa";
        jsonParseado = JSON.stringify(jsonAEnviarCopa);

    }

    console.log('JSON A Enviar' + JSON.stringify(jsonParseado));

    $.ajax({
        async: false,
        type: "POST",
        headers: {
            'Content-Type': 'application/json'
        },
        contentType: "application/json; charset=utf-8",
        data: jsonParseado,
        dataType: "text",

        url: url + urlPost + "/guardarPartido",
        success: function(data) {
            console.log(data);

        },
        error: function(e) {
            console.log("Error de guardado" + JSON.stringify(e));
        }
    });
    postSend();

}

function postSend() {
    var tipoJuego = localStorage.getItem('tipoJuego');

    var urlGet = "";
    if (tipoJuego == "copa") {
        urlGet = url + "/copa/partidoAJugar";
    }
    if (tipoJuego == "liga") {
        urlGet = url + "/liga/partidoAJugar";
    }

    var data = {};
    /**
     * obtengo proximo partido.
     */
    $.ajax({
        async: false,
        type: "GET",
        data: data,
        contentType: 'application/json',
        dataType: "json",
        url: urlGet,
        success: function(data) {
            console.log(JSON.stringify(data));
            if (data.jugadorUno != 'FINAL') {
                reset();

                localStorage.setItem('jugadorUno', data.jugadorUno);
                localStorage.setItem('jugadorDos', data.jugadorDos);
                localStorage.setItem('estado', data.estado);
                localStorage.setItem('idPartido', data.id);
                localStorage.setItem('tipoJuego', tipoJuego);
                localStorage.setItem('instanciaCopa', data.instanciaCopa);
                localStorage.setItem('resultadoJugadorUno', null);
                localStorage.setItem('resultadoJugadorDos', null);

                document.getElementById("nombreJugadorUno").innerHTML = data.jugadorUno;
                document.getElementById("nombreJugadorDos").innerHTML = data.jugadorDos;
                if (data.id != null) {
                    document.getElementById("idPartido").innerHTML = data.id + " - " + data.instanciaCopa;
                } else {
                    document.getElementById("idPartido").innerHTML = "Partido Unico";
                }



            } else {
                if (tipoJuego == "liga") {
                    alert("No hay mas partidos por jugar. Ver Resultados:");
                    window.location.href = "/liga";
                    return;
                } else {
                    alert("No hay mas partidos por jugar. Ver Copa:");
                    window.location.href = "/copa";
                    return;
                }

            }



        },
        error: function(e) {
            console.log("error" + JSON.stringify(e));
        }
    });



}

window.onload = function() {

    var jugadorUno = localStorage.getItem("jugadorUno");
    var jugadorDos = localStorage.getItem("jugadorDos");
    var estado = localStorage.getItem("estado");
    var idPartido = localStorage.getItem("idPartido");
    var instancia = localStorage.getItem("instanciaCopa");
    var tipoJuego = localStorage.getItem("tipoJuego");

    const TERMINADO = "terminado";


    document.getElementById("nombreJugadorUno").innerHTML = "<h5>" + jugadorUno + "</h5>";
    document.getElementById("nombreJugadorDos").innerHTML = "<h5>" + jugadorDos + "</h5>";

    if (tipoJuego != "partidoUnico") {
        document.getElementById("idPartido").innerHTML = idPartido + " - " + instancia;
    } else {
        document.getElementById("idPartido").innerHTML = "Partido Unico";
    }

    //turno inicial azul

    var turno = "fichaAzul";
    //condiciones iniciales de fichas azules
    var arrayFichasAzules = localStorage.getItem('fichasAzules');
    var arrayFichasAzules = JSON.parse(arrayFichasAzules);
    if (arrayFichasAzules !== null) {
        for (celda of arrayFichasAzules) {
            document.getElementById(celda).className = "fichaAzul";
        }
    }
    //condiciones iniciales de fichas amarillas
    var arrayFichasAmarillas = localStorage.getItem('fichasAmarillas');
    var arrayFichasAmarillas = JSON.parse(arrayFichasAmarillas);
    if (arrayFichasAmarillas !== null) {
        for (celda of arrayFichasAmarillas) {
            document.getElementById(celda).className = "fichaAmarilla";
        }
    }
    //boton volver
    $("#botonVolverIndex").on("click", volverIndex);

    $("#botonRecargar").on("click", function() {
        console.log("Recargo por aqui");
        document.location.reload;
    });



    //boton puntajes
    if (tipoJuego == "copa" || tipoJuego == "liga") {
        $("#resultados").on("click", function() {
            getResultados();
        });
    } else {
        $("#resultados").hide();
    }

    //condiciones iniciales de turno
    if (localStorage.getItem('turno') == "fichaAmarilla") {
        turno = "fichaAmarilla";
        document.getElementById("turno").style.backgroundColor = "#FFD966";
    }
    //establecer un EventListener para cada boton de flecha que agrega fichas
    var botones = document.getElementsByClassName("flecha");
    for (boton of botones) {
        boton.addEventListener("click", function(e) {
            //obtengo la columna sobre la que se hizo click
            if (document.getElementById("resultado").innerHTML == "") {
                var columna = e.target.id.substring(e.target.id.length - 1);
                var i, hueco, celda;
                //verificar en toda la columna la posicion y el color a ocupar 
                for (i = 6; i > 0; i--) {
                    var celda = "celda" + columna + i;
                    var hueco = document.getElementById(celda);
                    if (hueco.className == "hueco") {
                        hueco.className = turno;
                        //verifico si termino el juego con ganador o empate
                        var finHorizontal = verificar(1, i, "horizontal");
                        var finVertical = verificar(columna, 1, "vertical");
                        var diagonales = verificarDiagonales(columna, i)
                        jugadorUno = localStorage.getItem("jugadorUno");
                        jugadorDos = localStorage.getItem("jugadorDos");
                        //VICTORIA JUGADOR UNO

                        if (finHorizontal == "azul" || finVertical == "azul" || diagonales == "azul") {
                            var mensaje = "GANO AZUL : " + jugadorUno;
                            modalRecargar(mensaje);



                            jugadorUno = localStorage.getItem("jugadorUno");
                            jugadorDos = localStorage.getItem("jugadorDos");



                            $("#textoMensaje").innerHTML = mensaje;

                            document.getElementById("resultado").innerHTML = mensaje;

                            localStorage.setItem('estado', 'terminado');
                            localStorage.setItem('resultadoJugadorUno', 'victoria');
                            localStorage.setItem('resultadoJugadorDos', 'derrota');

                            sendResultado(jugadorUno, jugadorDos, "victoria", "derrota");




                            break;

                            //VICTORIA JUGADOR DOS
                        } else if (finHorizontal == "amarillo" || finVertical == "amarillo" || diagonales == "amarillo") {
                            var mensaje = "GANO AMARILLO: " + jugadorDos;
                            modalRecargar(mensaje);


                            $("#textoMensaje").innerHTML = mensaje;

                            document.getElementById("resultado").innerHTML = mensaje;

                            localStorage.setItem('estado', 'terminado');
                            localStorage.setItem('resultadoJugadorUno', 'derrota');
                            localStorage.setItem('resultadoJugadorDos', 'victoria');
                            sendResultado(jugadorUno, jugadorDos, "derrota", "victoria");



                            break;
                        }
                        //EMPATE
                        var huecos = document.getElementsByClassName("hueco");
                        if (huecos.length == 0) {
                            var mensaje = "EMPATE!";
                            $("#textoMensaje").innerHTML = mensaje;
                            modalRecargar(mensaje);

                            document.getElementById("resultado").innerHTML = mensaje;
                            localStorage.setItem('estado', 'terminado');
                            localStorage.setItem('resultadoJugadorUno', 'empate');
                            localStorage.setItem('resultadoJugadorDos', 'empate');
                            if (tipoJuego != "copa") {
                                sendResultado(jugadorUno, jugadorDos, "empate", "empate")
                            } else {
                                alert("Empate - se vuelve a jugar!");
                                reset();
                            }

                            break;
                        }
                        //cambio el turno y guardo el avance en localstorage
                        if (turno == "fichaAzul") {
                            turno = "fichaAmarilla";
                            document.getElementById("turno").style.backgroundColor = "#FFD966";

                            var azules = document.getElementsByClassName("fichaAzul");
                            var fichasAzules = [];
                            for (ficha of azules) {
                                fichasAzules.push(ficha.id)
                            }
                            localStorage.setItem('fichasAzules', JSON.stringify(fichasAzules));
                        } else {
                            turno = "fichaAzul";
                            document.getElementById("turno").style.backgroundColor = "#44546A";
                            var amarillas = document.getElementsByClassName("fichaAmarilla");
                            var fichasAmarillas = [];
                            for (ficha of amarillas) {
                                fichasAmarillas.push(ficha.id)
                            }
                            localStorage.setItem('fichasAmarillas', JSON.stringify(fichasAmarillas));
                        }

                        localStorage.setItem("turno", turno);

                        break;
                    }
                }
            }
        });
    }
}

function verificar(columna, fila, sentido) {
    var azul = 0;
    var amarillo = 0;
    var celda;
    var cantidad;
    if (sentido == "vertical") {
        cantidad = 6;
    } else {
        if (sentido = "horizontal") {
            cantidad = 7;
        }
    }
    for (var i = 1; i <= cantidad; i++) {
        switch (sentido) {
            case "horizontal":
                celda = "celda" + i + fila;
                break;
            case "vertical":
                celda = "celda" + columna + i;

        }
        if (document.getElementById(celda).className != "hueco") {
            if (document.getElementById(celda).className == "fichaAzul") {
                amarillo = 0;
                azul++;
                if (azul == 4) break;
            } else {
                azul = 0;
                amarillo++;
                if (amarillo == 4) break;
            }
        } else {
            amarillo = 0;
            azul = 0;
        }
    }
    if (azul == 4) {
        return "azul";
    } else if (amarillo == 4) {
        return "amarillo";
    } else {
        return "ninguno"
    }


}

function verificarDiagonales(columna, fila) {
    var celda;
    var cantidad = 4; //tablero es de 6 FILAS x 7 COLS
    var totalCol = 8; //COL + 1
    var totalFil = 7; //fil + 1
    //diagonal hacia abajo izquierda                
    var amarillo = 0;
    var azul = 0;
    for (var i = 0; i <= cantidad; i++) {
        var colAEvaluar = parseInt(columna) + i;
        var filAEvaluar = parseInt(fila) - i;
        var celdaAEvaluar = "celda" + colAEvaluar + filAEvaluar;
        console.log(celdaAEvaluar + " , " + totalCol + " , " + totalFil);
        if (0 < colAEvaluar && colAEvaluar < totalCol && 0 < filAEvaluar && filAEvaluar < totalFil) {
            if (document.getElementById(celdaAEvaluar).className != "hueco") {
                if (document.getElementById(celdaAEvaluar).className == "fichaAzul") {
                    azul++;
                    amarillo = 1;
                    if (azul == 4) {
                        console.log("abajo izquierda")
                        return "azul";
                        break;
                    }
                } else {
                    azul = 1;
                    amarillo++;
                    if (amarillo == 4) {
                        return "amarillo";
                        break;
                    }

                }
            }
        } else {
            break;

        }
    }
    amarillo = 0;
    azul = 0;
    //diagonal hacia ABAJO derecha
    for (var i = 0; i <= cantidad; i++) {
        var colAEvaluar = parseInt(columna) + i;
        var filAEvaluar = parseInt(fila) + i;
        var celdaAEvaluar = "celda" + colAEvaluar + filAEvaluar;
        console.log(celdaAEvaluar + " , " + totalCol + " , " + totalFil);
        if (0 < colAEvaluar && colAEvaluar < totalCol && 0 < filAEvaluar && filAEvaluar < totalFil) {
            if (document.getElementById(celdaAEvaluar).className != "hueco") {
                if (document.getElementById(celdaAEvaluar).className == "fichaAzul") {
                    azul++;
                    amarillo = 1;
                    if (azul == 4) {
                        return "azul";
                        break;
                    }
                } else {
                    azul = 1;
                    amarillo++;
                    if (amarillo == 4) {
                        return "amarillo";
                        break;
                    }

                }
            } else {
                break;
            }
        } else {
            break;

        }
    }
    amarillo = 0;
    azul = 0;
    //diagonal hacia abajo izquierda.
    for (var i = 0; i <= cantidad; i++) {
        var colAEvaluar = parseInt(columna) - i;
        var filAEvaluar = parseInt(fila) + i;
        celda = "celda" + colAEvaluar + filAEvaluar;
        var celdaAEvaluar = "celda" + colAEvaluar + filAEvaluar;

        console.log(celdaAEvaluar + " , " + totalCol + " , " + totalFil);
        if (0 < colAEvaluar && colAEvaluar < totalCol && 0 < filAEvaluar && filAEvaluar < totalFil) {
            if (document.getElementById(celdaAEvaluar).className != "hueco") {
                if (document.getElementById(celdaAEvaluar).className == "fichaAzul") {
                    azul++;
                    amarillo = 1;
                    if (azul == 4) {
                        return "azul";
                        break;
                    }
                } else {
                    azul = 1;
                    amarillo++;
                    if (amarillo == 4) {
                        return "amarillo";
                        break;
                    }

                }
            } else {
                break;
            }
        } else {
            break;

        }
    }
    amarillo = 0;
    azul = 0;
    //diagonal hacia arriba izquierda.
    for (var i = 0; i <= cantidad; i++) {
        var colAEvaluar = parseInt(columna) - i;
        var filAEvaluar = parseInt(fila) - i;
        celda = "celda" + colAEvaluar + filAEvaluar;
        var celdaAEvaluar = "celda" + colAEvaluar + filAEvaluar;

        console.log(celdaAEvaluar + " , " + totalCol + " , " + totalFil);
        if (0 < colAEvaluar && colAEvaluar < totalCol && 0 < filAEvaluar && filAEvaluar < totalFil) {
            if (document.getElementById(celdaAEvaluar).className != "hueco") {
                if (document.getElementById(celdaAEvaluar).className == "fichaAzul") {
                    azul++;
                    amarillo = 0;
                    if (azul == cantidad) {
                        return "azul";
                        break;
                    }
                } else {
                    azul = 0;
                    amarillo++;
                    if (amarillo == cantidad) {
                        return "amarillo";
                        break;
                    }

                }
            } else {
                break;
            }
        } else {
            break;
        }

    }
    return "sinDiagonales";
}

function volverIndex() {
    if ((localStorage.getItem("tipoJuego") != "copa") || (localStorage.getItem("tipoJuego") != "liga")) {
        //TODO: mostrar aviso de que s eborra?
        window.location.href = "/";
    } else {
        window.location.href = "/";
    }

}

function getResultados() {
    if (localStorage.getItem("tipoJuego") == "liga") {
        window.location.href = "/liga";
    }

    if (localStorage.getItem("tipoJuego") == "copa") {
        window.location.href = "/copa";
    }

}

function reset() {
    for (var i = 1; i <= 7; i++) {

        for (var j = 1; j <= 6; j++) {
            document.getElementById("celda" + i + j).className = "hueco";
        }

    }

    document.getElementById("resultado").innerHTML = "";
    localStorage.removeItem("fichasAzules");
    localStorage.removeItem("fichasAmarillas");
    localStorage.removeItem("arrayFichasAzules");
    localStorage.removeItem("arrayFichasAmarillas");

    document.getElementById("nombreJugadorUno").innerHTML = "";
    document.getElementById("nombreJugadorDos").innerHTML = "";


}

function modalRecargar(mensaje) {

    if (alert(mensaje)) {
        console.log("Recargo por este aqui");
        document.location.reload;
    }


}