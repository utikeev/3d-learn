package learn3d.utils.plot

import org.knowm.xchart.CategoryChart
import org.knowm.xchart.CategoryChartBuilder

class HistogramWithSliders(
        private val mapFunction: (x: Number, params: Map<String, Number>) -> Double,
        private val seriesName: String = "Default",
        sliders: List<SliderWrapper> = listOf(),
        private val params: MutableMap<String, Number> = mutableMapOf()
) : PlotWithSliders<CategoryChart>(
        sliders,
        CategoryChartBuilder().width(800).height(600).title("Sample").xAxisTitle("X").yAxisTitle("Y").build()
) {
    private val rangeList: List<Long>
        get() = (0..params["range"]!!.toLong()).toList()

    init {
        chart.addSeries(seriesName, listOf(0), listOf(0))
        params["range"] = 10
        val rangeSlider = SliderWrapper(
                defaultEventChanger("range", params),
                10, 9, 5, "Range", true)
        addSlider(rangeSlider)
    }

    override fun updateChart() {
        val yData = rangeList.map { mapFunction(it, params) }
        chart.updateCategorySeries(seriesName, rangeList, yData, null)
    }
}