package learn3d.geometry

import kotlin.math.sqrt

class Sphere(val center: Vector3D,
             private val radius: Double,
             val surfaceColor: Vector3D,
             val reflection: Double = .0,
             val transparency: Double = .0,
             val emissionColor: Vector3D = Vector3D(0)) {

    private val radius2: Double
        get() = radius * radius

    fun intersect(rayOrig: Vector3D, rayDir: Vector3D): Pair<Double, Double>? {
        val l = center - rayOrig
        val tca = l dot rayDir
        if (tca lt 0) return null
        val d2 = (l dot l) - tca * tca
        if (d2 gt radius2) return null
        val thc = sqrt(radius2 - d2)
        return (tca - thc) to (tca + thc)
    }
}