package learn3d.utils.plot

import javax.swing.JSlider
import javax.swing.event.ChangeEvent

class SliderWrapper(changeListener: (event: ChangeEvent) -> Unit,
                    center: Number = 50.0,
                    range: Number = 50.0,
                    initialValue: Int = 50,
                    val name: String = "Slider",
                    integerValues: Boolean = false) {
    val slider: JSlider =
            if (integerValues) IntegerJSlider (center, range, initialValue)
            else DoubleJSlider(center, range, initialValue)

    init {
        slider.addChangeListener(changeListener)
        slider.paintTicks = true
        slider.paintLabels = true
    }
}