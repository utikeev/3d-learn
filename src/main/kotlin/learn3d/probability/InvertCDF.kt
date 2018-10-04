package learn3d.probability

import learn3d.utils.plot.normal
import org.knowm.xchart.QuickChart
import org.knowm.xchart.SwingWrapper
import java.util.concurrent.ThreadLocalRandom


fun main(args: Array<String>) {
    val nBins = 32
    val minBound = -5.0
    val maxBound = 5.0
    val dx = (maxBound - minBound) / nBins
    var sum = .0
    val transform = { it: Number ->
        minBound + (maxBound - minBound) * (it.toDouble() / nBins)
    }
    val cdf = (0..nBins).map { .0 }.toMutableList()
    for (n in 0..nBins) {
        cdf[n] = when (n) {
            0 -> .0
            nBins -> 1.0
            else -> {
                val x = transform(n)
                val pdfX = dx * normal(x)
                sum += pdfX
                cdf[n - 1] + pdfX
            }
        }
    }
    println("Sum: $sum")
    val sample = {
        val r = ThreadLocalRandom.current().nextDouble()
        val off = cdf.indexOfLast { it < r }
        val t = (r - cdf[off]) / (cdf[off + 1] - cdf[off])
        val x = (off + t) / nBins
        minBound + (maxBound - minBound) * x
    }

    //Simulation of normal
    val numSims = 100000
    val numBins = 100
    val bins = (0 until 100).map { 0 }.toMutableList()
    (0..numSims).forEach { _ ->
        val diff = sample()
        val whichBin = (numBins * (diff - minBound) / (maxBound - minBound)).toInt()
        bins[whichBin]++
    }

    val g = QuickChart.getChart(
            "InvertCD", "x", "ys", "cdf",
            (0 until numBins).map { 5 * (2 * it.toDouble() / numBins - 1) },
            (0 until numBins).map { bins[it].toDouble() / numSims })
    SwingWrapper(g).displayChart()
}