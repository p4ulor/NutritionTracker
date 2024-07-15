package p4ulor.nutritiontracker.utils


fun readNotEmpty() = readlnOrNull()?.also {
    if(it.isEmpty() || it.isBlank()) null
}

fun readInt() = readlnOrNull()?.also {
    it.toIntOrNull()
}

fun printFormat(s: String, s2: String) {
    if (s2.isEmpty()) println("Error")
    else System.out.printf(s, s2)
}
