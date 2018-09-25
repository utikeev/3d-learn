package learn3d.probability

import kotlin.math.pow

fun fact(n: Long) = when {
    n < 2 -> 1L
    else -> {
        (2..n).reduce { acc, i -> acc * i }
    }
}

fun bin(n: Long, k: Long) = when (n) {
    k -> 1L
    else -> {
        var ans = 1L
        for (i in n - k + 1..n) ans *= i
        ans / fact(k)
    }
}

fun main(args: Array<String>) {
    val mFun2 = {i: Number, params: Map<String, Number> ->
        val n = params["range"]!!.toLong()
        val p = 1.0 / 2
        bin(n, i.toLong()) * p.pow(i.toInt()) * (1 - p).pow((n - i.toInt()).toDouble())
    }
    val g2 = HistogramWithSliders("Bernoulli", mFun2, listOf(), mutableMapOf())
    g2.show()
}