package learn3d.utils.plot

import java.text.DecimalFormat
import java.util.*
import javax.swing.JLabel


class DoubleJSlider(private val center: Number,
                    private val range: Number,
                    initialValue: Int):
        ScaledSlider(1,
                101,
                initialValue) {
    override val scaledValue: Double
        get() {
            return doubleScaleSlider(super.getValue(), center, range)
        }

    init {
        majorTickSpacing = 20
        labelTable = Hashtable<Int, JLabel>((1..101 step 20).map
        { it ->
            val label = doubleScaleSlider(it, center, range)
            it to JLabel(DecimalFormat("#.##").format(label))
        }.toMap())
    }
}