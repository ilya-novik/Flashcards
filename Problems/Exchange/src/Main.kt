fun main() {
    val input = readLine()!!
    print(input.last())
    print(input.subSequence(1 until input.lastIndex))
    print(input.first())
}