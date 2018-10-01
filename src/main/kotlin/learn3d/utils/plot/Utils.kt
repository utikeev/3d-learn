package learn3d.utils.plot

fun scaleSlider(value: Number, center: Number, range: Number, oldCenter: Number = 50): Double {
    return (value.toDouble() - oldCenter.toDouble()) / oldCenter.toDouble() * range.toDouble() + center.toDouble()
}

fun unscaleSlider(value: Number, center: Number, range: Number, oldCenter: Number = 50): Int {
    return ((oldCenter.toDouble() * (value.toDouble() - center.toDouble())) / range.toDouble() + oldCenter.toDouble()).toInt()
}