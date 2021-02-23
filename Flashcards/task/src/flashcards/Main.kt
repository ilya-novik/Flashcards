package flashcards

import java.io.File
import kotlin.random.Random

val log = mutableListOf<String>()
val wrongAnswers = mutableMapOf<String, Int>()

fun main(args: Array<String>) {
    val cardsList = mutableMapOf<String, String>()
    if (args.contains("-import")) executeArgument(args, cardsList = cardsList)

    while (true) {
        printAndSave("Input the action (add, remove, import, export, ask, exit):")
        when (readAndSave()) {
            "exit" -> {
                printAndSave()
                break
            }
            "add" -> addCard(cardsList)
            "remove" -> remove(cardsList)
            "import" -> import(cardsList)
            "export" -> export(cardsList)
            "ask" -> ask(cardsList)
            "log" -> log()
            "hardest card" -> hardestCard()
            "reset stats" -> resetStats()
        }
        printAndSave()
    }


    if (args.contains("-export")) executeArgument(args, "-export", cardsList = cardsList)
    printAndSave("Bye bye!")
}

fun addCard(cardsList: MutableMap<String, String>): Boolean {
    printAndSave("The card:")
    val term = readAndSave()
    if (term in cardsList.keys) {
        printAndSave("The card \"$term\" already exists.")
        return false
    }

    printAndSave("The definition of the card:")
    val definition = readAndSave()
    if (definition in cardsList.values) {
        printAndSave("The definition \"$definition\" already exists.")
        return false
    }

    cardsList[term] = definition
    wrongAnswers[term] = 0
    printAndSave("The pair (\"$term\":\"$definition\") has been added.")
    return true
}

fun remove(cardsList: MutableMap<String, String>) {
    printAndSave("Which card?")
    val card = readAndSave()
    if (card in cardsList.keys) {
        cardsList.remove(card)
        printAndSave("The card has been removed.")
    } else {
        printAndSave("Can't remove \"$card\": there is no such card.")
    }
}

fun import(cardsList: MutableMap<String, String>, arg: String = "") {
    val fileName = if (arg == "") {
        printAndSave("File name:")
        readAndSave()
    } else {
        arg
    }
    try {
        val cards = File(fileName).readLines()
        var loaded = 0
        for (i in cards.indices step 3) {
            wrongAnswers[cards[i]] = if (cards[i] in cardsList.keys) {
                loaded--
                wrongAnswers[cards[i]]!! + cards[i + 2].toInt()
            } else {
                0
            }
            cardsList[cards[i]] = cards[i + 1]
            loaded++
        }
        printAndSave("$loaded cards have been loaded.")
    } catch (e: Exception) {
        printAndSave("File not found.")
    }
}

fun export(cardsList: MutableMap<String, String>, arg: String = "") {
    val fileName = if (arg == "") {
        printAndSave("File name:")
        readAndSave()
    } else {
        arg
    }
    try {
        val file = File(fileName)
        file.createNewFile()
        cardsList.forEach { file.appendText("${it.key}\n${it.value}\n${wrongAnswers[it.key]}\n") }
        printAndSave("${cardsList.size} cards have been saved.")
    } catch (e: Exception) {
        printAndSave("File not found.")
    }
}

fun ask(cardsList: MutableMap<String, String>) {
    printAndSave("How many times to ask?")
    val n = readAndSave().toInt()
    for (i in 1..n) {

        val card = cardsList.entries.elementAt(Random.nextInt(cardsList.size))
        val term = card.key
        val definition = card.value

        printAndSave("Print the definition of \"$term\":")
        val correct = when (val input = readAndSave()) {
            definition -> {
                printAndSave("Correct!")
                true
            }
            in cardsList.values -> {
                printAndSave(
                    "Wrong. The right answer is \"$definition\", but your definition is correct for \"${
                        cardsList.filterValues { it == input }.keys.first()
                    }\"."
                )
                false
            }
            else -> {
                printAndSave("Wrong. The right answer is \"$definition\".")
                false
            }
        }

        if (!correct) wrongAnswers[term] = wrongAnswers[term]!! + 1
    }
}

fun log() {
    printAndSave("File name:")
    try {
        val file = File(readAndSave())
        file.createNewFile()
        printAndSave("The log has been saved.")
        log.forEach { file.appendText("$it\n") }
    } catch (e: Exception) {
        printAndSave("File not found.")
    }
}

fun hardestCard() {
    var max = 0
    val hardestCards = mutableListOf<String>()
    wrongAnswers.forEach { if (it.value > max) max = it.value }
    if (max == 0) {
        printAndSave("There are no cards with errors.")
        return
    }
    wrongAnswers.forEach { if (it.value == max) hardestCards.add(it.key) }
    if (hardestCards.size == 1) {
        printAndSave("The hardest card is \"${hardestCards[0]}\". You have $max errors answering it.")
    } else {
        var terms = ""
        hardestCards.forEach { terms += "\"$it\", " }
        terms = terms.substringBeforeLast(',')
        printAndSave("The hardest cards are $terms. You have $max errors answering them.")
    }
}

fun resetStats() {
    wrongAnswers.clear()
    printAndSave("Card statistics have been reset.")
}

fun printAndSave(str: String = "") {
    println(str)
    log.add(str)
}

fun readAndSave(): String {
    val input = readLine()!!
    log.add(input)
    return input
}

fun executeArgument(
    args: Array<String>,
    function: String = "-import",
    cardsList: MutableMap<String, String>,
) {
    for (i in args.indices) {
        if (args[i] == function) {
            if (function == "-import") {
                import(cardsList, args[i + 1])
            } else {
                export(cardsList, args[i + 1])
            }
            break
        }
    }
    printAndSave()
}