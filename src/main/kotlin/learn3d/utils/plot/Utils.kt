package learn3d.utils.plot


import javax.swing.event.ChangeEvent

fun intScaleSlider(value: Number, center: Number, range: Number): Int {
    return value.toInt() - 1 + center.toInt() - range.toInt()
}

fun doubleScaleSlider(value: Number, center: Number, range: Number): Double {
    val oldRange = 100
    val newRange = range.toDouble() * 2
    return ((value.toDouble() - 1) * newRange) / oldRange + (center.toDouble() - range.toDouble())
}

fun unscaleSlider(value: Number, center: Number, range: Number): Int {
    val oldRange = 100
    val newRange = range.toDouble() * 2
    return (oldRange * (value.toDouble() - center.toDouble() + range.toDouble()) / newRange).toInt() + 1
}

fun defaultEventChanger(name: String, params: MutableMap<String, Number>): (ChangeEvent) -> Unit {
    return { event: ChangeEvent ->
        val src = event.source as ScaledSlider
        params[name] = src.scaledValue
    }
}