fun main() {
    var string = readLine()!!
    val substring = readLine()!!

    var counter = 0
    while (true) {
        if (substring in string) {
            counter++
            string = string.substringBefore(substring) + string.substringAfter(substring)
        } else {
            break
        }
    }

    println(counter)
}