package learn3d.probability

import learn3d.utils.plot.SliderWrapper
import learn3d.utils.plot.XYChartWithSliders
import learn3d.utils.plot.defaultEventChanger
import learn3d.utils.normal

fun main(args: Array<String>) {
    val mParams = mutableMapOf<String, Number>("sigma" to 1.0, "mu" to .0)
    val sigmaSlider = SliderWrapper(
            defaultEventChanger("sigma", mParams), 5, 5, 1, "Sigma")
    val muSlider = SliderWrapper(
            defaultEventChanger("mu", mParams), 0, 10, 0, "Mu")
    val g = XYChartWithSliders(
            { x: Number, params: Map<String, Number> ->
                normal(x, params["mu"]!!, params["sigma"]!!)
            },
            "Gaussian distribution",
            listOf(sigmaSlider, muSlider),
            mParams
    )
    g.show()
}