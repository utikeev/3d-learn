package learn3d.probability

import org.knowm.xchart.CategoryChart
import org.knowm.xchart.CategoryChartBuilder
import javax.swing.JSlider

class HistogramWithSliders(
        private val seriesName: String,
        private val mapFunction: (x: Number, params: Map<String, Number>) -> Double,
        sliders: List<SliderWrapper>,
        private val params: MutableMap<String, Number>
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
                1,
                20,
                "Range",
                { event ->
                    val src = event.source as JSlider
                    params["range"] = src.value.toLong()
                },
                5)
        addSlider(rangeSlider)
    }

    override fun updateChart() {
        val yData = rangeList.map { mapFunction(it, params) }
        chart.updateCategorySeries(seriesName, rangeList, yData, null)
    }
}