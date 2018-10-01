package learn3d.utils.plot

import org.knowm.xchart.QuickChart
import org.knowm.xchart.XYChart
import javax.swing.JSlider

class XYChartWithSliders(
        private val mapFunction: (x: Number, params: Map<String, Number>) -> Double,
        private val seriesName: String = "Default",
        sliders: List<SliderWrapper> = listOf(),
        private val params: MutableMap<String, Number> = mutableMapOf()
) : PlotWithSliders<XYChart>(
        sliders,
        QuickChart.getChart("Sample", "X", "Y", seriesName, listOf(0), listOf(0))) {

    private val rangeList: List<Double>
        get() {
            var i = -params["range"]!!.toDouble() / 2
            val l = mutableListOf<Double>()
            while (i <= params["range"]!!.toDouble() / 2) {
                l.add(i)
                i += params["range"]!!.toDouble() / params["point"]!!.toDouble()
            }
            return l
        }

    init {
        params["range"] = 10
        val rangeSlider = SliderWrapper(
                1,
                100,
                "Range",
                { event ->
                    val src = event.source as JSlider
                    params["range"] = src.value
                },
                params["range"]!!.toInt())
        addSlider(rangeSlider)
        params["point"] = 10
        val pointSlider = SliderWrapper(
                1,
                100,
                "Points",
                { event ->
                    val src = event.source as JSlider
                    params["point"] = src.value
                },
                params["point"]!!.toInt())
        addSlider(pointSlider)
    }

    override fun updateChart() {
        val yData = rangeList.map { mapFunction(it, params) }
        chart.updateXYSeries(seriesName, rangeList, yData, null)
    }
}