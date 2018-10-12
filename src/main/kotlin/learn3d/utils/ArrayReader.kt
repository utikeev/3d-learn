package learn3d.utils

private fun String.splitToDoubleList(): List<List<Double>> {
    val text = this.split("{")
            .flatMap { it.split("}") }
    val resultList = mutableListOf<List<Double>>()
    for (line in text) {
        if (!line.replace(",", "").isBlank()) {
            resultList.add(line.split(",").map { it.toDouble() })
        }
    }
    return resultList
}

fun readArrayFromResource(fileName: String) = Dummy::class.java.getResource(fileName).readText().splitToDoubleList()

class Dummy