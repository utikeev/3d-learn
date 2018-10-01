package learn3d.probability

import learn3d.utils.plot.SliderWrapper
import learn3d.utils.plot.XYChartWithSliders
import javax.swing.JSlider
import kotlin.math.pow


fun main(args: Array<String>) {
    val mParams: MutableMap<String, Number> = mutableMapOf("power" to 2.0)
    val powerSlider = SliderWrapper(
            { event ->
                val src = event.source as JSlider
                mParams["power"] = src.value
            },
            5,
            4,
            "Power",
            2,
            true)

    val g = XYChartWithSliders({ i: Number, params: Map<String, Number> ->
        i.toDouble().pow(params["power"]!!.toInt())
    }, sliders = listOf(powerSlider), params = mParams)
    g.show()
}