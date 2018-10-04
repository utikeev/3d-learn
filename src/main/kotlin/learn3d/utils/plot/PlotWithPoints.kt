package learn3d.utils.plot

import learn3d.utils.RGBColor
import java.io.File
import java.io.FileWriter
import java.io.PrintWriter
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
            true -> "set arrow from graph 0,first ${it.coord} to graph 1,first ${it.coord} nohead"
            false -> "set arrow from ${it.coord},graph(0,0) to ${it.coord},graph(1,1) nohead"
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

    private fun printInstructions() {
        val fileGP = File("$name.gp")
        val out = PrintWriter(FileWriter(fileGP))
        out.println("rgb(r, g, b) = 65536 * int(r * 255) + 256 *  int(g * 255) + int(b * 255)")
        out.println("set title \"$name\"")
        out.println("set xlabel \"$xLabel\"")
        out.println("set ylabel \"$yLabel\"")
        out.println("set size ratio 1")
        additionalInstructions.forEach { out.println(it) }
        lineStrings().forEach { out.println(it) }
        out.println("plot \"$name.plt\" using 1:2:(rgb(\$3, \$4, \$5)) with points lc rgb variable")
        out.println("pause mouse close")
        out.close()
    }

    fun show() {
        printData()
        printInstructions()
        val prompt = Runtime.getRuntime()
        val proc = prompt.exec("gnuplot --persist $name.gp")
        val exit = proc.waitFor()
        println(exit)
    }
}