package learn3d.utils.plot

import java.text.DecimalFormat
import java.util.*
import javax.swing.JLabel
import javax.swing.JSlider


class DoubleJSlider(private val center: Number,
                    private val range: Number,
                    initialValue: Int):
        JSlider(1,
                101,
                initialValue) {
    val value: Double
        get() {
            return scaleSlider(super.getValue(), center, range)
        }

    init {
        majorTickSpacing = 20
        labelTable = Hashtable<Int, JLabel>((1..101 step 20).map
        { it ->
            val label = scaleSlider(it, center, range)
            it to JLabel(DecimalFormat("#.##").format(label))
        }.toMap())
    }
}