package learn3d.probability

import learn3d.utils.plot.SliderWrapper
import learn3d.utils.plot.XYChartWithSliders
import learn3d.utils.plot.defaultEventChanger
import kotlin.math.floor
import kotlin.math.pow

fun pow(): XYChartWithSliders {
    val mParams: MutableMap<String, Number> = mutableMapOf("power" to 2.0)
    val powerSlider = SliderWrapper(
            defaultEventChanger("power", mParams),
            5, 4, 2, "Power", true)

    return XYChartWithSliders({ i: Number, params: Map<String, Number> ->
        i.toDouble().pow(params["power"]!!.toInt())
    }, sliders = listOf(powerSlider), params = mParams)
}

fun discreteUniform(): XYChartWithSliders {
    return XYChartWithSliders({ i: Number, _: Map<String, Number> ->
        floor(i.toDouble())
    })
}

fun geometric(): XYChartWithSliders {
    return XYChartWithSliders({ i: Number, _: Map<String, Number> ->
        val p = 0.5
        (1 - p).pow(i.toInt() - 1) * p
    })
}

fun main(args: Array<String>) {
    val g = discreteUniform()
    g.show()
    val g2 = pow()
    g2.show()
    val g3 = geometric()
    g3.show()
}