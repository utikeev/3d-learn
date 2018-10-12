package learn3d.probability

import learn3d.geometry.Vector3D
import learn3d.utils.RGBColor
import learn3d.utils.drand
import learn3d.utils.readArrayFromResource
import java.io.File
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow


const val N = 32
const val nBins = 36
const val lambdaMin = 380
const val lambdaMax = 730
const val squareSize = 64

val colorMatching = readArrayFromResource("/rawData/mcBeth/colorMatchingFunc.txt")
val xyzToRGB = readArrayFromResource("/rawData/mcBeth/xyzToRGB.txt")
val spectralData = readArrayFromResource("/rawData/mcBeth/spectralData.txt")

fun xyzToRGB(v: Vector3D): RGBColor {
    val r = max(.0, v.x * xyzToRGB[0][0] + v.y * xyzToRGB[0][1] + v.z * xyzToRGB[0][2])
    val g = max(.0, v.x * xyzToRGB[1][0] + v.y * xyzToRGB[1][1] + v.z * xyzToRGB[1][2])
    val b = max(.0, v.x * xyzToRGB[2][0] + v.y * xyzToRGB[2][1] + v.z * xyzToRGB[2][2])
    return RGBColor.create(r, g, b, true)
}

fun linerp(f: List<Double>, i: Int, t: Double, maxi: Int) =
        f[i] * (1 - t) + f[min(maxi, i + 1)] * t

fun mcIntegration(curveIndex: Int, v: Vector3D): Vector3D {
    var sum = .0
    var x = v.x
    var y = v.y
    var z = v.z
    for (i in 0 until N) {
        val lambda = drand() * (lambdaMax - lambdaMin)
        var b = lambda / 10
        var j = b.toInt()
        var t = b - j
        val fx = linerp(spectralData[curveIndex], j, t, nBins - 1)
        b = lambda / 5
        j = b.toInt()
        t = b - j
        x += linerp(colorMatching[0], j, t, 2 * nBins - 1) * fx
        y += linerp(colorMatching[1], j, t, 2 * nBins - 1) * fx
        z += linerp(colorMatching[2], j, t, 2 * nBins - 1) * fx
        sum += linerp(colorMatching[1], j, t, 2 * nBins - 1)
    }
    sum *= (lambdaMax - lambdaMin) / N
    x *= (lambdaMax - lambdaMin) / N / sum
    y *= (lambdaMax - lambdaMin) / N / sum
    z *= (lambdaMax - lambdaMin) / N / sum
    return Vector3D(x, y, z)
}

fun main(args: Array<String>) {
    val width = squareSize * 6
    val height = squareSize * 4
    val epochs = 12
    val buffer = DoubleArray(3 * width * height)
    val pixels = DoubleArray(3 * width * height)
    repeat(epochs) {
        var pixIndex = 0
        println("Current epoch $it")
        for (y in 0 until height) {
            val row = y / squareSize
            for (x in 0 until width) {
                val column = x / squareSize
                val v = mcIntegration(row * 6 + column, Vector3D())
                val color = xyzToRGB(v)
                buffer[pixIndex * 3] += color.r
                buffer[pixIndex * 3 + 1] += color.g
                buffer[pixIndex * 3 + 2] += color.b
                pixIndex++
            }
        }
    }
    val gamma = 1 / 2.2
    for (i in 0 until width * height) {
        pixels[i * 3] = (buffer[i * 3] / epochs).pow(gamma)
        pixels[i * 3 + 1] = (buffer[i * 3 + 1] / epochs).pow(gamma)
        pixels[i * 3 + 2] = (buffer[i * 3 + 2] / epochs).pow(gamma)
    }

    println("Printing image")
    File("mcbeth.ppm").printWriter().use { out ->
        out.write("P3\n$width $height\n255\n")
        for (i in 0 until width * height) {
            val r = (min(1.0, pixels[i * 3]) * 255).toInt()
            val g = (min(1.0, pixels[i * 3 + 1]) * 255).toInt()
            val b = (min(1.0, pixels[i * 3 + 2]) * 255).toInt()
            out.write("$r $g $b\n")
        }
    }
}