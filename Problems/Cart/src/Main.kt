import kotlin.math.roundToInt

fun main() {
    val productType = readLine()!!
    val price = readLine()!!.toInt()
    val product = Product(productType, price)
    println(totalPrice(product))
}

class Product(val type: String, val price: Int)

fun totalPrice(product: Product): Int {
    return when (product.type) {
        "headphones" -> (product.price * 1.11).roundToInt()
        "smartphone" -> (product.price * 1.15).roundToInt()
        "tv" -> (product.price * 1.17).roundToInt()
        else -> (product.price * 1.19).roundToInt()
    }
}