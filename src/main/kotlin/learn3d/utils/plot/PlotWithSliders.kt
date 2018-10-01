package learn3d.utils.plot

import org.knowm.xchart.XChartPanel
import org.knowm.xchart.internal.chartpart.Chart
import java.awt.FlowLayout
import javax.swing.*

abstract class PlotWithSliders<T : Chart<*, *>>(sliders: List<SliderWrapper>, protected val chart: T) {

    private var panel: XChartPanel<T>
    private val sliderPanel = JPanel()
    private val frame: JFrame = JFrame("Window")

    init {
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        sliderPanel.layout = FlowLayout(FlowLayout.LEFT)
        panel = XChartPanel(this.chart)
        sliders.forEach { addSlider(it) }
        val bigPanel = Box.createVerticalBox()
        bigPanel.add(sliderPanel)
        bigPanel.add(panel)
        frame.add(bigPanel)
    }

    fun show() {
        drawData()
        frame.pack()
        frame.isVisible = true
    }

    protected abstract fun updateChart()

    private fun drawData() {
        updateChart()
        panel.revalidate()
        panel.repaint()
    }

    protected fun addSlider(sliderWrapper: SliderWrapper) {
        val panel = Box.createVerticalBox()
        sliderWrapper.slider.addChangeListener {
            drawData()
        }
        panel.add(sliderWrapper.slider)
        val label = JLabel(sliderWrapper.name)
        label.alignmentX = JLabel.CENTER_ALIGNMENT
        panel.add(label)
        sliderPanel.add(panel)
    }
}