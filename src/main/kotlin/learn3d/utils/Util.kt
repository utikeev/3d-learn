package learn3d.utils

const val INFINITY = 1e+9

fun mix(a: Number, b: Number, mix: Number) = b.toDouble() * mix.toDouble() + a.toDouble() * (1 - mix.toDouble())