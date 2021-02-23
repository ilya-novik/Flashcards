fun main() {
    val rooms = readLine()!!.toInt()
    val price = readLine()!!.toInt()
    val house = House(rooms, price)
    println(totalPrice(house))
}

open class House(var rooms: Int, var price: Int) {
    init {
        rooms = when {
            rooms < 1 -> 1
            else -> rooms
        }
        price = when {
            price < 0 -> 0
            price > 1_000_000 -> 1_000_000
            else -> price
        }
    }
}

class Cabin(rooms: Int, price: Int) : House(rooms, price) {
    val finalPrice = price
}

class Bungalow(rooms: Int, price: Int) : House(rooms, price) {
    val finalPrice = (price * 1.2).toInt()
}

class Cottage(rooms: Int, price: Int) : House(rooms, price) {
    val finalPrice = (price * 1.25).toInt()
}

class Mansion(rooms: Int, price: Int) : House(rooms, price) {
    val finalPrice = (price * 1.4).toInt()
}

class Palace(rooms: Int, price: Int) : House(rooms, price) {
    val finalPrice = (price * 1.6).toInt()
}

fun totalPrice(house: House): Int {
    val rooms = house.rooms
    val price = house.price
    return when (rooms) {
        1 -> Cabin(rooms, price).finalPrice
        in 2..3 -> Bungalow(rooms, price).finalPrice
        4 -> Cottage(rooms, price).finalPrice
        in 5..7 -> Mansion(rooms, price).finalPrice
        else -> Palace(rooms, price).finalPrice
    }
}
