package learn3d.probability

import learn3d.utils.plot.XYChartWithSliders
import kotlin.math.pow


fun main(args: Array<String>) {
    val g = XYChartWithSliders({ i: Number, _: Map<String, Number> ->
        i.toDouble().pow(2)
    })
    g.show()
}