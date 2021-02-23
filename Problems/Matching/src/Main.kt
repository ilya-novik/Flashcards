import java.math.BigInteger

fun main() {
    val numbers = mutableListOf<BigInteger>()
    numbers.add(BigInteger(readLine()!!))
    numbers.add(BigInteger(readLine()!!))
    numbers.add(BigInteger(readLine()!!))
    if (numbers[0] == numbers[1] && numbers[1] == numbers[2]) println(3)
    else if (numbers[0] == numbers[1] || numbers[1] == numbers[2] || numbers[0] == numbers[2]) println(2)
    else println(0)
}