fun main() {
    val input = readLine()!!.first()
    println(input.isUpperCase() || input in '1'..'9')
}