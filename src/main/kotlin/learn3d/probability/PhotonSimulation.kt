package learn3d.probability

import kotlinx.coroutines.experimental.*
import learn3d.geometry.*
import learn3d.utils.drand
import learn3d.utils.henyeyGreensteinCosTheta
import java.io.File
import kotlin.math.*

const val nPhotons = 50000
const val sigmaA = 1
const val sigmaS = 3
const val d = 0.4
const val g = 0.75
const val width = 256
const val height = 256
const val absorbChances = 5
const val slabWidth = 0.5

fun spin(mu: Vector3D): Vector3D {
    val cosTheta = henyeyGreensteinCosTheta(g)
    val phi = 2 * PI * drand()
    val sinTheta = sqrt(1.0 - cosTheta * cosTheta)
    val sinPhi = sin(phi)
    val cosPhi = cos(phi)
    return when {
        mu.z eq 1 -> Vector3D(sinTheta * cosPhi, sinTheta * sinPhi, cosTheta)
        mu.z eq -1 -> Vector3D(sinTheta * cosPhi, -sinTheta * sinPhi, -cosTheta)
        else -> {
            val denominator = sqrt(1.0 - mu.z * mu.z)
            val muZCosPhi = mu.z * cosPhi
            val ux = sinTheta * (mu.x * muZCosPhi - mu.y * sinPhi) / denominator + mu.x * cosTheta
            val uy = sinTheta * (mu.y * muZCosPhi + mu.x * sinPhi) / denominator + mu.y * cosTheta
            val uz = -denominator * sinTheta * cosPhi + mu.z * cosTheta
            Vector3D(ux, uy, uz)
        }
    }
}

fun photonSim(): DoubleArray {
    val sigmaT = sigmaA + sigmaS

    var reflectance = .0
    var transmittance = .0
    val mRecords = DoubleArray(width * height)

    for (i in 0 until nPhotons) {
        var weight = 1.0
        var position = Vector3D()
        var direction = Vector3D(0, 0, 1)
        while (true) {
            val s = -ln(drand()) / sigmaT
            val boundary = when {
                direction.z lt 0 -> -position.z / direction.z
                direction.z gt 0 -> (d - position.z) / direction.z
                else -> .0
            }
            if (s gt boundary) {
                val xi = ((position.x + slabWidth / 2) / slabWidth * width).toInt()
                val yi = ((position.y + slabWidth / 2) / slabWidth * height).toInt()
                if (direction.z > 0) {
                    transmittance += weight
                    if (xi in 0 until width && yi in 0 until height) {
                        mRecords[xi + yi * width] += weight
                    }
                } else {
                    reflectance += weight
                }
                break
            }
            position += s * direction
            val dw = sigmaA.toDouble() / sigmaT
            weight = max(weight - dw, .0)

            if (weight lt 0.001) {
                if (drand() > 1.0 / absorbChances) {
                    break
                }
                weight *= absorbChances
            }
            direction = spin(direction)
        }
    }
    return mRecords
}

fun main(args: Array<String>) {
    val nPasses = 128
    val pixels = DoubleArray(width * height)

    val startTime = System.nanoTime()
    val recordList = mutableListOf<DoubleArray>()
    val jobs = List(nPasses) {
        GlobalScope.launch {
            recordList.add(photonSim())
            print(".")
        }
    }
    runBlocking {
        jobs.forEach { it.join() }
    }
    val endTime = System.nanoTime()
    println("\nTook ${(endTime - startTime).toDouble() / 1_000_000_000} seconds")

    val records = DoubleArray(width * height) { arg -> recordList.asSequence().map { it[arg] }.sum() }

    for (i in 0 until width * height) pixels[i] = records[i] / nPasses

    File("out.ppm").printWriter().use { out ->
        out.write("P3\n$width $height\n255\n")
        for (i in 0 until width * height) {
            val v = (255 * min(1.0, pixels[i])).toInt()
            out.write("$v $v $v\n")
        }
    }
    println("Done")
}