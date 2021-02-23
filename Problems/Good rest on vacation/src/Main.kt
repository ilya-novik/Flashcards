fun main() {
    val duration = readLine()!!.toInt()
    val food = readLine()!!.toInt()
    val flight = readLine()!!.toInt()
    val hotel = readLine()!!.toInt()
    println((duration - 1) * hotel + flight * 2 + food * duration)
}