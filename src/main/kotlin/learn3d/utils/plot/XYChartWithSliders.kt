package learn3d.utils.plot

import org.knowm.xchart.QuickChart
import org.knowm.xchart.XYChart
import kotlin.math.pow

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
                i += (params["range"]!!.toDouble() + 1) / params["point"]!!.toDouble()
            }
            return l
        }

    init {
        params["range"] = 50
        val rangeSlider = SliderWrapper(
                defaultEventChanger("range", params),
                initialValue = params["range"]!!.toInt(),
                name = "Range",
                integerValues = true)
        addSlider(rangeSlider)
        params["point"] = 400
        val pointSlider = SliderWrapper(
                defaultEventChanger("point", params),
                600, 500, params["point"]!!.toInt(), "Points", true)
        addSlider(pointSlider)
        val ySlider = SliderWrapper(
                { event ->
                    val src = event.source as ScaledSlider
                    chart.styler.yAxisMax = 10.0.pow(src.scaledValue.toDouble())
                    chart.styler.yAxisMin = -(10.0.pow(src.scaledValue.toDouble()))
                },
                3, 10, 3, "Y restriction")
        addSlider(ySlider)
    }

    override fun updateChart() {
        val yData = rangeList.map { mapFunction(it, params) }
        chart.updateXYSeries(seriesName, rangeList, yData, null)
    }
}