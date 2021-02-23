fun isNumber(input: String): Any {
    return try {
        input.toInt()
    } catch (e: Exception) {
        input
    }
}