package learn3d.probability

import org.knowm.xchart.CategoryChartBuilder
import org.knowm.xchart.QuickChart
import org.knowm.xchart.SwingWrapper
import java.util.concurrent.ThreadLocalRandom

fun main(args: Array<String>) {
    var sum = 0
    val xs = (1..20000).toList()
    val ys2 = mutableListOf(0, 0, 0, 0, 0, 0)
    val ys1 = xs.map {
        val ran = ThreadLocalRandom.current().nextInt(1, 7)
        sum += ran
        ys2[ran - 1]++
        sum.toDouble() / it
    }
    val g = QuickChart.getChart(
            "Dice roll",
            "# of trials",
            "Sample mean",
            "Population mean",
            xs,
            ys1)
    SwingWrapper(g).displayChart()

    val g2 = CategoryChartBuilder().
            width(400).
            height(300).
            xAxisTitle("Number").
            yAxisTitle("# of times").
            build()
    g2.addSeries("Dice roll", (1..6).toList(), ys2)
    SwingWrapper(g2).displayChart()
}