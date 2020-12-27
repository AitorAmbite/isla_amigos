import kotlinx.coroutines.*

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

const val CUBOS_NECESARIOS = 4
const val LENA_NECESARIA = 2

val hamacaMutex = Mutex()
val hachaMutex = Mutex()

fun main() {
    comenzar()
    Thread.sleep(80000)
}

fun comenzar() {
    var cubosActuales:Deferred<Boolean>
    var lenaActual:Deferred<Boolean>
    var reservas:Deferred<Boolean>

    GlobalScope.launch {
        cubosActuales = amigoA()
        lenaActual = amigoB()
        reservas = amigoC()

        if(cubosActuales.await() && lenaActual.await() && reservas.await()){
            println("Barca construida y aprovisionada con exito")
        }
    }

}

fun amigoA():Deferred<Boolean> {
    return GlobalScope.async{
        repeat(CUBOS_NECESARIOS) {
            println("Voy a por agua")
            delay(4000)
            dormirHamaca("amigo A", 1000)
        }
        true
    }
}

fun amigoB():Deferred<Boolean> {
    return GlobalScope.async{
        repeat(LENA_NECESARIA) {
            println("El amigo B va a por lena")
            cogerHacha("amigo B", 5000)
            dormirHamaca("amigo B", 3000)
        }
        true
    }
}

fun amigoC():Deferred<Boolean> {
    return GlobalScope.async{
        println("El amigo c va a por ramas")
        delay(3000)
        println("El amigo c vuelve con ramas")
        println("El amigo c va a cazar")
        cogerHacha("amigo c",4000)
        true
    }

}

suspend fun dormirHamaca(amigo: String, tiempoEspera: Long) {
    println("El amigo $amigo, quiere descansar")
    hamacaMutex.withLock {
        println("El amigo $amigo, se tumba en la hamaca")
        delay(tiempoEspera)
        println("El amigo $amigo, se levanta de la hamaca")
        println("El amigo $amigo, deja de descansar")
    }
}

suspend fun cogerHacha(amigo: String, tiempoEspera: Long) {
    hachaMutex.withLock {
        println("El amigo $amigo coge el hacha")
        delay(tiempoEspera)
        println("El $amigo deja el hacha")
    }
}