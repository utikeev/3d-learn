package learn3d.utils.plot

import javax.swing.JSlider
import javax.swing.event.ChangeEvent

class SliderWrapper(changeListener: (event: ChangeEvent) -> Unit,
                    center: Number = 50.0,
                    range: Number = 50.0,
                    val name: String = "Slider",
                    initialValue: Int = 50,
                    integerValues: Boolean = false) {
    val slider: JSlider =
            if (integerValues) IntegerJSlider (center, range, initialValue)
            else DoubleJSlider(center, range, unscaleSlider(initialValue, center, range))

    init {
        slider.addChangeListener(changeListener)
        slider.paintTicks = true
        slider.paintLabels = true
    }
}