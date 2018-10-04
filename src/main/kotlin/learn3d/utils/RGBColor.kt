package learn3d.utils

class RGBColor(val r: Double, val g: Double, val b: Double) {
    companion object {
        fun create(r: Number, g: Number, b: Number, scaled: Boolean = true): RGBColor {
            if (!scaled) return RGBColor(r.toDouble() / 255, g.toDouble() / 255, b.toDouble() / 255)
            return RGBColor(r.toDouble(), g.toDouble(), b.toDouble())
        }
    }
}