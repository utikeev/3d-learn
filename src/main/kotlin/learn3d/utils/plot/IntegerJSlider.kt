package learn3d.utils.plot

import java.util.*
import javax.swing.JLabel
import javax.swing.JSlider
import kotlin.math.max

class IntegerJSlider(center: Number,
                     range: Number,
                     initialValue: Int):
        JSlider(1,
                range.toInt() * 2 + 1,
                initialValue - 1 + center.toInt() - range.toInt()) {
    init {
        val spacing = max((range.toInt() * 2) / 5, 1)
        majorTickSpacing = spacing
        labelTable = Hashtable<Int, JLabel>((1..(range.toInt() * 2 + 1) step spacing).map
        { it ->
            val label = it - 1 + center.toInt() - range.toInt()
            it to JLabel(label.toString())
        }.toMap())
    }
}