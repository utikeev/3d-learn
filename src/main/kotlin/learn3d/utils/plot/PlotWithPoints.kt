package learn3d.utils.plot

import learn3d.utils.RGBColor
import java.nio.file.Files
import java.nio.file.Paths

class PlotWithPoints(
        private val xs: List<Number>,
        private val ys: List<Number>,
        private val name: String = "example",
        private val xLabel: String = "r",
        private val yLabel: String = "y",
        private val colors: List<RGBColor> = listOf(),
        private val additionalInstructions: List<String> = listOf()) {

    private data class Line (val coord: Double, val horizontal: Boolean)

    private val lines = mutableListOf<Line>()

    fun addHorizontalLine(x: Number): PlotWithPoints {
        lines.add(Line(x.toDouble(), true))
        return this
    }

    fun addVerticalLine(y: Number): PlotWithPoints {
        lines.add(Line(y.toDouble(), false))
        return this
    }

    private fun lineStrings(): List<String> = lines.map {
        when (it.horizontal) {
            true -> "set arrow from graph 0,first ${it.coord} to graph 1,first ${it.coord} nohead;"
            false -> "set arrow from ${it.coord},graph(0,0) to ${it.coord},graph(1,1) nohead;"
        }
    }

    private fun printData() {
        if (xs.size != ys.size) throw IllegalArgumentException("Xs size (${xs.size}) isn't equal to ys' (${ys.size})")
        if (xs.size != colors.size) throw IllegalArgumentException("Xs size (${xs.size}) isn't equal to colors' (${colors.size})")
        val strings = (0 until xs.size).map {
            val color = colors[it]
            "${xs[it]} ${ys[it]} ${color.r} ${color.g} ${color.b}"
        }
        val file = Paths.get("$name.plt")
        Files.write(file, strings)
    }

    private fun printInstructions(): String {
        val lines = mutableListOf<String>()
        lines.add("rgb(r, g, b) = 65536 * int(r * 255) + 256 *  int(g * 255) + int(b * 255);")
        lines.add("set title \"$name\";")
        lines.add("set xlabel \"$xLabel\";")
        lines.add("set ylabel \"$yLabel\";")
        lines.add("set size ratio 1;")
        lines.addAll(additionalInstructions)
        lines.addAll(lineStrings())
        lines.add("plot \"$name.plt\" using 1:2:(rgb(\$3, \$4, \$5)) with points lc rgb variable;")
        lines.add("pause mouse close;")
        return lines
                .joinToString(separator = "")
                .replace("\"", "\\\"")
                .replace("\$", "\\\$")
    }

    fun show() {
        printData()
        val p = ProcessBuilder("/bin/sh", "-c", "/usr/bin/gnuplot --persist -e \"${printInstructions()}\"")
        val exit = p.start().waitFor()
        println(exit)
    }
}