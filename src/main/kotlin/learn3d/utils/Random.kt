package learn3d.utils

import java.util.concurrent.ThreadLocalRandom

fun drand(from: Double = .0, to: Double = .1): Double = ThreadLocalRandom.current().nextDouble(from, to)

fun intrand(to: Int, from: Int = 0): Int = ThreadLocalRandom.current().nextInt(to)