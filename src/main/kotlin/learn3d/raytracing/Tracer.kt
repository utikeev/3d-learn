package learn3d.raytracing

import learn3d.geometry.*
import learn3d.utils.INFINITY
import learn3d.utils.RGBColor
import learn3d.utils.mix
import java.io.FileOutputStream
import kotlin.math.*

const val MAX_RAY_DEPTH = 5

fun trace(rayOrig: Vector3D, rayDir: Vector3D, spheres: List<Sphere>, depth: Int): Vector3D {
    var tNear = INFINITY
    var sphere: Sphere? = null
    for (s in spheres) {
        val p = s.intersect(rayOrig, rayDir)
        if (p != null) {
            val t0 = if (p.first lt 0) p.second else p.first
            if (t0 lt tNear) {
                tNear = t0
                sphere = s
            }
        }
    }
    if (sphere == null) return Vector3D(2)
    var surfaceColor = Vector3D() // color of the ray/surface of the object intersected by the ray
    val pHit = rayOrig + rayDir * tNear // point of intersection
    var nHit = pHit - sphere.center // normal at the intersection point
    nHit.normalize()
    val bias = 1e-4
    var inside = false
    if ((rayDir dot nHit) gt 0) {
        nHit = -nHit
        inside = true
    }
    if ((sphere.transparency gt 0 || sphere.reflection gt 0) && depth < MAX_RAY_DEPTH) {
        val facingRatio = -rayDir dot nHit
        val fresnelEffect = mix((1 - facingRatio).pow(3), 1, 0.1)
        val reflectionDir = rayDir - nHit * 2 * (rayDir dot nHit)
        reflectionDir.normalize()
        val reflection = trace(pHit + nHit * bias, reflectionDir, spheres, depth + 1)
        var refraction = Vector3D()
        if (sphere.transparency neq 0) {
            val ior = 1.1
            val eta = if (inside) ior else 1.0 / ior
            val cosI = -nHit dot rayDir
            val k = 1 - eta * eta * (1 - cosI * cosI)
            val refractionDir = rayDir * eta + nHit * (eta * cosI - sqrt(k))
            refractionDir.normalize()
            refraction = trace(pHit - nHit * bias, refractionDir, spheres, depth + 1)
        }
        surfaceColor = (reflection * fresnelEffect + refraction * (1 - fresnelEffect) * sphere.transparency) *
                sphere.surfaceColor
    } else {
        for ((i, s) in spheres.withIndex()) {
            if (s.emissionColor.x gt 0) {
                var transmission = Vector3D(1)
                val lightDirection = s.center - pHit
                lightDirection.normalize()
                for ((j, s2) in spheres.withIndex()) {
                    if (i != j && s2.intersect(pHit + nHit * bias, lightDirection) != null) {
                        transmission = Vector3D(0)
                        break
                    }
                }
                surfaceColor += sphere.surfaceColor * transmission * max(.0, nHit dot lightDirection) * s.emissionColor
            }
        }
    }

    return surfaceColor + sphere.emissionColor
}

fun render(spheres: List<Sphere>) {
    val width = 640
    val height = 480
    val image = Array(width * height) { RGBColor(.0, .0, .0) }
    val invWidth = 1.0 / width
    val invHeight = 1.0 / height
    val fov = 30
    val aspectRatio = width * invHeight
    val angle = tan(PI * 0.5 * fov / 180)
    var curPixel = 0
    for (y in 0 until height) {
        for (x in 0 until width) {
            val xx = (2 * ((x + 0.5) * invWidth) - 1) * angle * aspectRatio
            val yy = (1 - 2 * ((y + 0.5) * invHeight)) * angle
            val rayDir = Vector3D(xx, yy, -1)
            rayDir.normalize()
            image[curPixel++] = RGBColor.createFromVector(trace(Vector3D(), rayDir, spheres, 0))
        }
    }
    val fos = FileOutputStream("raytracing.ppm")
    val buffer = ByteArray(width * 3)
    fos.use {
        val header = "P6\n$width $height\n255\n".toByteArray()
        with (it) {
            write(header)
            for (y in 0 until height) {
                for (x in 0 until width) {
                    val c = image[y * width + x]
                    buffer[x * 3] = (min(1.0, c.r) * 255).toByte()
                    buffer[x * 3 + 1] = (min(1.0, c.g) * 255).toByte()
                    buffer[x * 3 + 2] = (min(1.0, c.b) * 255).toByte()
                }
                write(buffer)
            }
        }
    }
}

fun main(args: Array<String>) {
    val spheres = mutableListOf<Sphere>()
    spheres.add(Sphere(Vector3D(0, -10004, -20), 10000.0, Vector3D(0.2, 0.2, 0.2))) //Surface
    spheres.add(Sphere(Vector3D(0, 0, -20), 4.0, Vector3D(1, 0.32, 0.36), 1.0, 0.5))
    spheres.add(Sphere(Vector3D(5, -1, -15), 2.0, Vector3D(0.9, 0.76, 0.46), 1.0))
    spheres.add(Sphere(Vector3D(5, 0, -25), 3.0, Vector3D(0.65, 0.77, 0.97), 1.0))
    spheres.add(Sphere(Vector3D(-5.5, 0, -15), 3.0, Vector3D(0.9), 1.0))
    spheres.add(Sphere(Vector3D(-1, -1, -5), 0.25, Vector3D(0.2, 0.8, 0.3), 1.0))
    spheres.add(Sphere(Vector3D(0, 20, -30), 3.0, Vector3D(0), emissionColor = Vector3D(3))) //Visible light
    render(spheres)
}