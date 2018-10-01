package learn3d.geometry

import kotlin.math.abs


class Matrix44 {
    private var x = arrayOf(
            doubleArrayOf(1.0, .0, .0, .0),
            doubleArrayOf(.0, 1.0, .0, .0),
            doubleArrayOf(.0, .0, 1.0, .0),
            doubleArrayOf(.0, .0, .0, 1.0)
    )

    constructor()
    constructor(_m: Matrix44) {
        _m.x.forEachIndexed { i, arr -> x[i] = arr.copyOf() }
    }

    constructor(a: Number, b: Number, c: Number, d: Number,
                e: Number, f: Number, g: Number, h: Number,
                i: Number, j: Number, k: Number, l: Number,
                m: Number, n: Number, o: Number, p: Number
    ) {
        x[0][0] = a.toDouble()
        x[0][1] = b.toDouble()
        x[0][2] = c.toDouble()
        x[0][3] = d.toDouble()
        x[1][0] = e.toDouble()
        x[1][1] = f.toDouble()
        x[1][2] = g.toDouble()
        x[1][3] = h.toDouble()
        x[2][0] = i.toDouble()
        x[2][1] = j.toDouble()
        x[2][2] = k.toDouble()
        x[2][3] = l.toDouble()
        x[3][0] = m.toDouble()
        x[3][1] = n.toDouble()
        x[3][2] = o.toDouble()
        x[3][3] = p.toDouble()
    }

    constructor(table: Array<DoubleArray>) {
        x = table
    }
    // I'm sorry, Georgiy Aleksandrovich
    constructor(table: List<List<Number>>) {
        table.forEachIndexed { i, row -> row.forEachIndexed { j, value -> x[i][j] = value.toDouble() } }
    }
    constructor(table: Array<Array<out Number>>) {
        table.forEachIndexed { i, row -> row.forEachIndexed { j, value -> x[i][j] = value.toDouble() } }
    }
    constructor(table: Array<IntArray>) {
        table.forEachIndexed { i, row -> row.forEachIndexed { j, value -> x[i][j] = value.toDouble() } }
    }
    constructor(table: Array<FloatArray>) {
        table.forEachIndexed { i, row -> row.forEachIndexed { j, value -> x[i][j] = value.toDouble() } }
    }
    constructor(table: Array<LongArray>) {
        table.forEachIndexed { i, row -> row.forEachIndexed { j, value -> x[i][j] = value.toDouble() } }
    }

    operator fun get(i: Int): DoubleArray = x[i]

    override operator fun equals(other: Any?): Boolean =
            (other is Matrix44 && (0..3).all { i -> (0..3).all { j -> x[i][j] eq other[i][j] } })

    override fun hashCode(): Int = x.hashCode()

    operator fun plus(rhs: Matrix44): Matrix44 {
        val res = Matrix44()
        (0..3).forEach { i -> (0..3).forEach { j -> res[i][j] = x[i][j] + rhs[i][j] } }
        return res
    }

    operator fun unaryMinus(): Matrix44 {
        val res = Matrix44()
        (0..3).forEach { i -> (0..3).forEach { j -> res[i][j] = -x[i][j] } }
        return res
    }

    operator fun minus(rhs: Matrix44): Matrix44 = this + (-rhs)

    operator fun times(rhs: Matrix44): Matrix44 {
        val res = Matrix44()
        (0..3).forEach { i ->
            (0..3).forEach { j ->
                res[i][j] =
                        x[i][0] * rhs[0][j] +
                        x[i][1] * rhs[1][j] +
                        x[i][2] * rhs[2][j] +
                        x[i][3] * rhs[3][j]
            }
        }

        return res
    }

    // We use row-major layout: x * A. It makes it more intuitive when combining transformations
    operator fun times(rhs: Vector3D): Vector3D {
        var xx = rhs.x * x[0][0] + rhs.y * x[1][0] + rhs.z * x[2][0] + x[3][0]
        var yy = rhs.x * x[0][1] + rhs.y * x[1][1] + rhs.z * x[2][1] + x[3][1]
        var zz = rhs.x * x[0][2] + rhs.y * x[1][2] + rhs.z * x[2][2] + x[3][2]
        val ww = rhs.x * x[0][3] + rhs.y * x[1][3] + rhs.z * x[2][3] + x[3][3]
        if (ww neq 1.0 && ww neq .0) {
            xx /= ww
            yy /= ww
            zz /= ww
        }
        return Vector3D(xx, yy, zz)
    }

    infix fun vecTimes(rhs: Vector3D): Vector3D {
        val xx = rhs.x * x[0][0] + rhs.y * x[1][0] + rhs.z * x[2][0]
        val yy = rhs.x * x[0][1] + rhs.y * x[1][1] + rhs.z * x[2][1]
        val zz = rhs.x * x[0][2] + rhs.y * x[1][2] + rhs.z * x[2][2]
        return Vector3D(xx, yy, zz)
    }

    fun transpose(): Matrix44 {
        val r = Matrix44()
        for (i in 0..3)
            for (j in 0..3)
                r[i][j] = x[j][i]
        return r
    }

    fun inverted(): Matrix44 {
        val res = Matrix44()
        val t = Matrix44(this)
        for (col in 0..3) {
            // If pivot equals to 0, swap current row with one which has max (by abs) value in this column
            if (t[col][col] eq 0) {
                var big = col
                for (row in 0..3) {
                    if (abs(t[row][col]) > abs(t[big][col])) {
                        big = row
                    }
                }
                if (big == col) {
                    return Matrix44()
                }
                for (j in 0..3) {
                    t[col][j] = t[big][j].also { t[big][j] = t[col][j] }
                    res[col][j] = res[big][j].also { res[big][j] = res[col][j] }
                }
            }

            // Turn each non-pivot value in row to 0 with subtracting
            for (row in 0..3) {
                if (row != col) {
                    val coeff = t[row][col] / t[col][col]
                    if (coeff neq 0) {
                        for (j in 0..3) {
                            t[row][j] -= coeff * t[col][j]
                            res[row][j] -= coeff * res[col][j]
                        }
                        t[row][col] = 0.0
                    }
                }
            }
        }

        // Scale pivot coefficient to 1
        for (row in 0..3) {
            for (col in 0..3) {
                res[row][col] /= t[row][row]
            }
        }

        return res
    }

    fun inverse(): Matrix44 {
        this.x = inverted().x
        return this
    }

    override fun toString(): String {
        val str = x.map { row -> row.map { d -> DF.format(d) } }
        val maxLen = str.flatten().maxBy { it.length }?.length
        return str.joinToString(
                separator = System.lineSeparator(),
                transform = { row ->
                    "| " + row.joinToString(
                            separator = ", ",
                            transform = { s -> "%${maxLen ?: 1}s".format(s) }
                    ) + " |"
                }
        )
    }
}