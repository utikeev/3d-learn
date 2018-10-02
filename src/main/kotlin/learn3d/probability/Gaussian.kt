package learn3d.probability

import learn3d.utils.plot.DoubleJSlider
import learn3d.utils.plot.SliderWrapper
import learn3d.utils.plot.XYChartWithSliders
import kotlin.math.PI
import kotlin.math.exp
import kotlin.math.sqrt

fun main(args: Array<String>) {
    val mParams = mutableMapOf<String, Number>("sigma" to 1.0, "mu" to .0)
    val sigmaSlider = SliderWrapper(
            { event ->
                val src = event.source as DoubleJSlider
                mParams["sigma"] = src.scaledValue
            },
            5, 4,"Sigma", 1
    )
    val muSlider = SliderWrapper(
            { event ->
                val src = event.source as DoubleJSlider
                mParams["mu"] = src.scaledValue
            },
            0, 10, "Mu", 0
    )
    val g = XYChartWithSliders(
            {x: Number, params: Map<String, Number> ->
                val xx = x.toDouble()
                val mu = params["mu"]!!.toDouble()
                val sigma = params["sigma"]!!.toDouble()
                val mx = xx - mu
                exp(-(mx * mx) / (2 * sigma * sigma)) / (sigma * sqrt(2 * PI))
            },
            "Gaussian distribution",
            listOf(sigmaSlider, muSlider),
            mParams
    )
    g.show()
}