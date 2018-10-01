package learn3d.geometry

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class Matrix44Test: StringSpec({
    val m1 = Matrix44(
            6, -5, 8, 4,
            9, 7, 5, 2,
            7, 5, 3, 7,
            -4, 8, -8, -3
            )
    val m2 = Matrix44(
            1, 2, 3, 4,
            5, 6, 7, 8,
            9, -9, -8, -7,
            -6, -5, -4, -3
    )

    "Default constructor" {
        val tm = Matrix44()
        tm shouldBe Matrix44(
                1, 0, 0, 0,
                0, 1, 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1
        )
    }

    "Equals" {
        m1 shouldBe Matrix44(
                6, -5, 8, 4,
                9, 7, 5, 2,
                7, 5, 3, 7,
                -4, 8, -8, -3.00000000000001
        )
    }

    "Other matrix constructor" {
        val tm = Matrix44(m1)
        tm shouldBe m1
    }

    "Numbers constructor" {
        val a = 1; val b = 2; val c = 3; val d = 4
        val e = 5; val f = 6; val g = 7; val h = 8
        val i = -8; val j = -7; val k = -6; val l = -5
        val m = -4; val n = -3; val o = -2; val p = -1
        val tm = Matrix44(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p)
        tm[0][0] eq a.toDouble() shouldBe true
        tm[0][1] eq b.toDouble() shouldBe true
        tm[0][2] eq c.toDouble() shouldBe true
        tm[0][3] eq d.toDouble() shouldBe true
        tm[1][0] eq e.toDouble() shouldBe true
        tm[1][1] eq f.toDouble() shouldBe true
        tm[1][2] eq g.toDouble() shouldBe true
        tm[1][3] eq h.toDouble() shouldBe true
        tm[2][0] eq i.toDouble() shouldBe true
        tm[2][1] eq j.toDouble() shouldBe true
        tm[2][2] eq k.toDouble() shouldBe true
        tm[2][3] eq l.toDouble() shouldBe true
        tm[3][0] eq m.toDouble() shouldBe true
        tm[3][1] eq n.toDouble() shouldBe true
        tm[3][2] eq o.toDouble() shouldBe true
        tm[3][3] eq p.toDouble() shouldBe true
    }

    "Table constructor" {
        val table = listOf(
                listOf(1, 2, 3, 4),
                listOf(5, 6, 7, 8),
                listOf(8, 7, 6, 5),
                listOf(4, 3, 2, 1)
        )
        val tm = Matrix44(table)
        table.forEachIndexed { i, row -> row.forEachIndexed { j, value -> tm[i][j] eq value shouldBe true } }
    }

    "Arrays constructors" {
        val tt: Array<Array<out Number>> = arrayOf(
                arrayOf(1.0, 2.0, 3.0, 4.0),
                arrayOf(1, 2, 3, 4),
                arrayOf(1.0, 2.0, 3.0, 4.0),
                arrayOf(1, 2, 3, 4)
        )
        val ti: Array<IntArray> = arrayOf(
                intArrayOf(1, 2, 3, 4),
                intArrayOf(1, 2, 3, 4),
                intArrayOf(1, 2, 3, 4),
                intArrayOf(1, 2, 3, 4)
        )
        val tl: Array<LongArray> = arrayOf(
                longArrayOf(1, 2, 3, 4),
                longArrayOf(1, 2, 3, 4),
                longArrayOf(1, 2, 3, 4),
                longArrayOf(1, 2, 3, 4)
        )
        val td: Array<DoubleArray> = arrayOf(
                doubleArrayOf(1.0, 2.0, 3.0, 4.0),
                doubleArrayOf(1.0, 2.0, 3.0, 4.0),
                doubleArrayOf(1.0, 2.0, 3.0, 4.0),
                doubleArrayOf(1.0, 2.0, 3.0, 4.0)
        )
        val tf: Array<FloatArray> = arrayOf(
                floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f),
                floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f),
                floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f),
                floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f)
        )
        Matrix44(tt) shouldBe Matrix44(ti)
        Matrix44(tt) shouldBe Matrix44(tl)
        Matrix44(tt) shouldBe Matrix44(td)
        Matrix44(tt) shouldBe Matrix44(tf)
    }

    "Get operator" {
        val row = listOf(6, -5, 8, 4)
        m1[0].forEachIndexed { key, value -> value eq row[key] shouldBe true}
    }

    "Hash code" {
        val table = arrayOf(
                doubleArrayOf(1.0, 2.0, 3.0, 4.0),
                doubleArrayOf(5.0, 6.0, 7.0, 8.0),
                doubleArrayOf(8.0, 7.0, 6.0, 5.0),
                doubleArrayOf(4.0, 3.0, 2.0, 1.0)
        )
        val tm = Matrix44(table)
        tm.hashCode() shouldBe table.hashCode()
    }

    "Plus" {
        val res = listOf(
                listOf(7, -3, 11, 8),
                listOf(14, 13, 12, 10),
                listOf(16, -4, -5, 0),
                listOf(-10, 3, -12, -6)
        )
        m1 + m2 shouldBe Matrix44(res)
        m2 + m1 shouldBe Matrix44(res)
    }

    "Unary minus" {
        val res = listOf(
                listOf(-6, 5, -8, -4),
                listOf(-9, -7, -5, -2),
                listOf(-7, -5, -3 ,-7),
                listOf(4, -8, 8, 3)
        )
        -m1 shouldBe Matrix44(res)
    }

    "Minus" {
        val res = listOf(
                listOf(5, -7, 5, 0),
                listOf(4, 1, -2, -6),
                listOf(-2, 14, 11, 14),
                listOf(2, 13, -4, 0)
        )
        m1 - m2 shouldBe Matrix44(res)
        m2 - m1 shouldBe -Matrix44(res)
    }

    "Times" {
        val res1 = listOf(
                listOf(29, -110, -97, -84),
                listOf(77, 5, 28, 51),
                listOf(17, -18, 4, 26),
                listOf(-18, 127, 120, 113)
        )
        val res2 = listOf(
                listOf(29, 56, -5, 17),
                listOf(101, 116, 27, 57),
                listOf(-55, -204, 59, -17),
                listOf(-97, -49, -61, -53)
        )
        m1 * m2 shouldBe Matrix44(res1)
        m2 * m1 shouldBe Matrix44(res2)
    }

    "Transpose" {
        val res = listOf(
                listOf(1, 5, 9, -6),
                listOf(2, 6, -9, -5),
                listOf(3, 7, -8, -4),
                listOf(4, 8, -7, -3)
        )
        m2.transpose() shouldBe Matrix44(res)
    }

    "Inverse" {
        val res = listOf(
                listOf(5.56, -0.77, -0.93, 4.73),
                listOf(-3.0, 0.5, 0.5, -2.5),
                listOf(-5.36, 0.87, 0.83, -4.63),
                listOf(-1.12, 0.04, 0.36, -0.96)
        )
        val tm = Matrix44(m1)
        tm.inverse()
        m1.inverted() shouldBe Matrix44(res)
        tm shouldBe Matrix44(res)

        val tm2 = Matrix44(
                1, 0, 0, 0,
                0, 0, 1, 0,
                0, 1, 0, 0,
                0, 0, 0, 1
        )
        tm2.inverted() shouldBe tm2

        val errM = Matrix44(
                1, 0, 0, 0,
                0, 0, 0, 0,
                0, 0, 0, 0,
                0, 0, 0, 0
        )
        errM.inverted() shouldBe Matrix44()
    }

    val v = Vector3D(1, 2, 3)

    "Point times" {
        m2 * v shouldBe Vector3D(-8, 4.5, 2.75)
    }

    "Vector times" {
        m2 vecTimes v shouldBe Vector3D(38, -13, -7)
        vecTimes(m2, v) shouldBe Vector3D(38, -13, -7)
    }

    "ToString" {
        val res1 = """
            |  1,  2,  3,  4 |
            |  5,  6,  7,  8 |
            |  9, -9, -8, -7 |
            | -6, -5, -4, -3 |
        """.trimIndent()
        m2.toString() shouldBe res1
    }
})