import kotlin.math.*

fun main() {
    val (a, b, c) = Array(3) { readLine()!!.toFloat() }
    val discriminant = b.pow(2) - 4 * a * c
    val x1 = (-b - sqrt(discriminant)).div(2 * a)
    val x2 = (-b + sqrt(discriminant)).div(2 * a)
    if (x1 < x2) println("$x1 $x2")
    else println("$x2 $x1")
}