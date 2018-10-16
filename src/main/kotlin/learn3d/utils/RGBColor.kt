package learn3d.utils

import learn3d.geometry.Vector3D

class RGBColor(val r: Double, val g: Double, val b: Double) {
    companion object {
        fun create(r: Number, g: Number, b: Number, scaled: Boolean = true): RGBColor {
            if (!scaled) return RGBColor(r.toDouble() / 255, g.toDouble() / 255, b.toDouble() / 255)
            return RGBColor(r.toDouble(), g.toDouble(), b.toDouble())
        }

        fun createFromVector(v: Vector3D, scaled: Boolean = true): RGBColor = create(v.x, v.y, v.z, scaled)
    }
}