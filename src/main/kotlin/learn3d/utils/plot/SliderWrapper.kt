package learn3d.utils.plot

import javax.swing.JSlider
import javax.swing.event.ChangeEvent

class SliderWrapper(minBound: Int,
                    maxBound: Int,
                    val name: String,
                    changeListener: (event: ChangeEvent) -> Unit,
                    initialValue: Int = (maxBound - minBound) / 2) {
    val slider: JSlider = JSlider(minBound, maxBound, initialValue)

    init {
        slider.addChangeListener(changeListener)
        slider.paintTicks = true
        slider.paintLabels = true
        slider.majorTickSpacing = (maxBound - minBound) / 5
    }
}