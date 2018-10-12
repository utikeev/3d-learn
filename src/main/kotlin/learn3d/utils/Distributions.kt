package learn3d.utils

import learn3d.geometry.eq
import kotlin.math.*

fun normal(x: Number, mean: Number = .0, std: Number = 1.0): Double {
    val xx = x.toDouble()
    val mu = mean.toDouble()
    val sigma = std.toDouble()
    val mx = xx - mu
    return exp(-(mx * mx) / (2 * sigma * sigma)) / (max(sigma, 0.0000001) * sqrt(2 * PI))
}

fun henyeyGreensteinCosTheta(g: Number): Double {
    val mG = g.toDouble()
    val eps = drand()
    if (mG eq 0) {
        return 1 - 2 * eps
    }
    return (1 + mG * mG - ((1 - mG * mG) / (1 - mG + 2 * mG * eps)).pow(2)) / (2 * mG)
}