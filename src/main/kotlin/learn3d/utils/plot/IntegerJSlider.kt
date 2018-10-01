package learn3d.utils.plot

import java.util.*
import javax.swing.JLabel
import kotlin.math.max

class IntegerJSlider(private val center: Number,
                     private val range: Number,
                     initialValue: Int):
        ScaledSlider(1,
                range.toInt() * 2 + 1,
                intScaleSlider(initialValue, center, range)) {
    override val scaledValue: Number
        get() = intScaleSlider(value, center, range)

    init {
        val spacing = max((range.toInt() * 2) / 5, 1)
        majorTickSpacing = spacing
        labelTable = Hashtable<Int, JLabel>((1..(range.toInt() * 2 + 1) step spacing).map
        { it ->
            val label = intScaleSlider(it, center, range)
            it to JLabel(label.toString())
        }.toMap())
    }
}