package learn3d.probability

import org.knowm.xchart.CategoryChartBuilder
import org.knowm.xchart.SwingWrapper
import java.io.File
import java.io.FileWriter
import java.io.PrintWriter
import java.lang.Math.round
import java.nio.file.Files
import java.nio.file.Paths
import java.util.concurrent.ThreadLocalRandom

const val MAX_NUM = 20
const val MAX_FREQ = 50


fun main(args: Array<String>) {
    val minSampleSize = args[0].toInt()
    val maxSampleSize = args[1].toInt()
    var popMean = .0
    var popVar = .0
    var popSize = 0
    val population = (0..MAX_NUM).map {
        val i = ThreadLocalRandom.current().nextInt(1, MAX_FREQ + 1)
        popSize += i
        popMean += it * i
        popVar += it * it * i
        i
    }

    popMean /= popSize
    popVar /= popSize
    popVar -= popMean * popMean
    println("Size: $popSize, Mean: $popMean, Var: $popVar")

    val samples = 1000
    var expectedDistrMean = .0
    var expectedDistVar = .0
    val meanCount = (0..20).map { 0 }.toMutableList()
    val points = (0 until samples).map { _ ->
        //val n = ThreadLocalRandom.current().nextInt(minSampleSize, maxSimpleSize + 1)
        val n = maxSampleSize
        var sampleMean = .0
        var sampleVar = .0
        (0 until n).forEach { _ ->
            var item = ThreadLocalRandom.current().nextInt(0, popSize)
            run breaker@ {
                (0..MAX_NUM).forEach { k ->
                    item -= population[k]
                    if (item < 0) {
                        sampleMean += k
                        sampleVar += k * k
                        return@breaker
                    }
                }
            }
        }
        sampleMean /= n
        sampleVar /= n
        sampleVar -= sampleMean * sampleMean
        meanCount[round(sampleMean).toInt()]++
        expectedDistrMean += sampleMean
        expectedDistVar += sampleMean * sampleMean

        val c1 = listOf(1, 0, 0)
        val c2 = listOf(0, 0, 1)
        val t = (n - minSampleSize).toDouble() / (maxSampleSize - minSampleSize + 1)
        val r = c1[0] * (1 - t) + c2[0] * t
        val g = c1[1] * (1 - t) + c2[1] * t
        val b = c1[2] * (1 - t) + c2[2] * t
        "$sampleMean $sampleVar $r $g $b"
    }

    expectedDistrMean /= samples
    expectedDistVar /= samples
    expectedDistVar -= expectedDistrMean * expectedDistrMean

    val g = CategoryChartBuilder().
            width(600).
            height(400).
            xAxisTitle("Number").
            yAxisTitle("# of times").
            build()
    g.addSeries("Sample distribution", (0..20).toList(), meanCount)
    SwingWrapper(g).displayChart()

    println("Expected mean: $expectedDistrMean, expected variance: $expectedDistVar")

    val file = Paths.get("sampling.plt")
    Files.write(file, points)

    //TODO: gnuplot lib?
    val fileGP = File("sampling.gp")
    val out = PrintWriter(FileWriter(fileGP))
    out.println("rgb(r, g, b) = 65536 * int(r * 255) + 256 *  int(g * 255) + int(b * 255)")
    out.println("set title \"Sampling\"")
    out.println("set xlabel \"Sample mean\"")
    out.println("set ylabel \"Sample variance\"")
    out.println("set arrow from $popMean, graph 0 to $popMean, graph 1 nohead")
    out.println("plot \"sampling.plt\" using 1:2:(rgb(\$3, \$4, \$5)) with points lc rgb variable, $popVar title \"\"")
    out.println("pause mouse close")
    out.close()

    val prompt = Runtime.getRuntime()
    val proc = prompt.exec("gnuplot --persist sampling.gp")
    val exit = proc.waitFor()
    println(exit)
}