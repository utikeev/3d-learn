package learn3d.utils.plot

import javax.swing.JSlider

abstract class ScaledSlider(min: Int, max: Int, initial: Int): JSlider(min, max, initial) {
    abstract val scaledValue: Number;
}