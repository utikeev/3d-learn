package learn3d.utils.plot

import kotlin.math.PI
import kotlin.math.exp
import kotlin.math.max
import kotlin.math.sqrt

fun normal(x: Number, mean: Number = .0, std: Number = 1.0): Double {
    val xx = x.toDouble()
    val mu = mean.toDouble()
    val sigma = std.toDouble()
    val mx = xx - mu
    return exp(-(mx * mx) / (2 * sigma * sigma)) / (max(sigma, 0.0000001) * sqrt(2 * PI))
}