import MetodoDeInterpolacion.*

val puntos = mutableMapOf<Float, Float>()
var metodoDeInterpolacionSeleccionado:MetodoDeInterpolacion = LAGRANGE
val interpolation: Interpolation =
    when(metodoDeInterpolacionSeleccionado){
        LAGRANGE -> Lagrange(puntos)
        NEWTON_GREGRY_PROGRESIVO -> TODO()
        NEWTON_GREGORY_REGRESIVO -> TODO()
    }

fun main(args: Array<String>) {
    println("Bienvenido a FINTER!")
    printMenu()

    var consoleInput = readLine()
    while (!consoleInput.equals("exit")){
        when(consoleInput){
            "ingresar datos" -> ingresarDatos()
            "mostrar calculo" -> mostrarCalculo()
            "especializar" -> especializar()
            "alterar valores" -> print("Todavia no esta desarrollada esta funcionalidad") //TODO alterar valores
            "help" -> printMenu()
            else -> wrongCommand()
        }
        consoleInput = readLine()
    }
}

fun printMenu(){
    println("Menu:")
    println("1. Ingresar Datos (ingresar datos)")
    println("2. Mostar pasos de calculo (mostrar calculo)")
    println("3. Especializar el polinomio en un valor k (especializar)")
    println("4. Alterar valores iniciales (alterar valores)")
    println("5. Finalizar (exit)")
}

fun wrongCommand() {
    println("No entiendo ese comando")
    println("Si necesitas ayuda ingresa help y mira los comandos validos")
}

fun ingresarDatos() {
    println("Seleccionaste ingresar datos")

    println("Ingrese una serie de puntos de la forma Xn Yn y para finalizar ingrese fin")

    var consoleInput = readLine().orEmpty()
    while (!consoleInput.trim().equals("fin")){
        try {
            val (x, y) = consoleInput.split(' ').map(String::toFloat)
            println("Ingresaste el putno P(${x}) = ${y}")
            puntos.put(x, y)
        }catch (e:IndexOutOfBoundsException){
            println("Ingresa 2 valores separados por un espacio")
        }catch (e: NumberFormatException){
            println("Ingresa 2 valores numericos, y para su parte decimal separado por punto")
        }
        consoleInput = readLine().orEmpty()
    }

//    println("Ahora elegi mediante que polinomio queres interpolar")
//    println("1. Lagrange (lagrange)")
//    println("2. Newton Gregory progresivo (ng progresivo)")
//    println("3. Newton Gregory regresivo (ng regresivo)")
    //TODO seleccionar un metodo, ahora por default esta lagrange

    println("Ya esta todo listo para que FINTER calcule el polinomio interpolante!")
}

fun mostrarCalculo() {
    interpolation.printSteps()
}

fun especializar() {
    println("Elija un valor para evaluar el polinomio")
    println(interpolation.polynomial())
    val k = readLine().orEmpty().toFloat()
    println("P($k)=${interpolation.evaluate(k)}")
}


