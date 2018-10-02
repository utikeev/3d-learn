package learn3d.probability

import learn3d.utils.plot.SliderWrapper
import learn3d.utils.plot.XYChartWithSliders
import learn3d.utils.plot.defaultEventChanger
import kotlin.math.PI
import kotlin.math.exp
import kotlin.math.max
import kotlin.math.sqrt

fun main(args: Array<String>) {
    val mParams = mutableMapOf<String, Number>("sigma" to 1.0, "mu" to .0)
    val sigmaSlider = SliderWrapper(
            defaultEventChanger("sigma", mParams), 5, 5, 1, "Sigma")
    val muSlider = SliderWrapper(
            defaultEventChanger("mu", mParams), 0, 10, 0, "Mu")
    val g = XYChartWithSliders(
            { x: Number, params: Map<String, Number> ->
                val xx = x.toDouble()
                val mu = params["mu"]!!.toDouble()
                val sigma = params["sigma"]!!.toDouble()
                val mx = xx - mu
                exp(-(mx * mx) / (2 * sigma * sigma)) / (max(sigma, 0.0000001) * sqrt(2 * PI))
            },
            "Gaussian distribution",
            listOf(sigmaSlider, muSlider),
            mParams
    )
    g.show()
}