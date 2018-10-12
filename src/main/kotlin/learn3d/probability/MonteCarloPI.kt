package learn3d.probability

import learn3d.geometry.le
import learn3d.utils.RGBColor
import learn3d.utils.drand
import learn3d.utils.plot.PlotWithPoints
import kotlin.math.sqrt

fun main(args: Array<String>) {
    val n = if (args.isNotEmpty()) args[0].toInt() else 1024
    var hits = 0
    val xs = mutableListOf<Double>()
    val ys = mutableListOf<Double>()
    val colors = mutableListOf<RGBColor>()
    (0..n).forEach { _ ->
        val x = drand(-1.0, 1.0)
        val y = drand(-1.0, 1.0)
        val l = sqrt(x * x + y * y)
        if (l le 1) hits++
        val r = if (l le 1) 0 else 1
        val g = if (l le 1) 1 else 0
        val b = 0
        xs.add(x)
        ys.add(y)
        colors.add(RGBColor.create(r, g, b))
    }
    val g = PlotWithPoints(xs, ys, "MonteCarlo", colors=colors)
    println("Area of unit disk: ${hits.toDouble() / n * 4} ($hits)")
    g.show()
}