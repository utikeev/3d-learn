package learn3d.geometry

import io.kotlintest.inspectors.forAll
import io.kotlintest.matchers.plusOrMinus
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import kotlin.math.sqrt


class Vector3DTest: StringSpec({
    class VectorAndLength(val x: Number, val y: Number, val z: Number, val l: Number)
    val v1 = Vector3D(1, 2, 3)
    val v2 = Vector3D(3, 2, 1)
    val vectorsWithLengths = listOf(
            VectorAndLength(1, 2, 2, 3),
            VectorAndLength(2, 3, 6, 7),
            VectorAndLength(4, 4, 7, 9),
            VectorAndLength(2, 10, 11, 15)
    )

    "Zero constructor" {
        val v = Vector3D()
        v.x eq 0 shouldBe true
        v.y eq 0 shouldBe true
        v.z eq 0 shouldBe true
    }

    "One number constructor" {
        val v = Vector3D(2)
        v.x eq 2 shouldBe true
        v.y eq 2 shouldBe true
        v.z eq 2 shouldBe true
    }

    "Three numbers constructor" {
        val v = Vector3D(2, 3.4, 5f)
        v.x eq 2 shouldBe true
        v.y eq 3.4 shouldBe true
        v.z eq 5 shouldBe true
    }

    "Other vector constructor" {
        val tv = Vector3D(v1)
        v1.x eq tv.x shouldBe true
        v1.y eq tv.y shouldBe true
        v1.z eq tv.z shouldBe true
    }

    "Equals" {
        val tv = Vector3D(1.0000000001, 2.00000000002, 3.0000000000004)
        tv shouldBe v1
    }

    "Length" {
        vectorsWithLengths.forAll {
            Vector3D(it.x, it.y, it.z).length() eq it.l shouldBe true
        }
    }
    "Normalize" {
        vectorsWithLengths.forAll {
            val a = Vector3D(it.x, it.y, it.z)
            val b = a.norm()
            b.x eq it.x.toDouble() / it.l.toDouble() shouldBe true
            b.y eq it.y.toDouble() / it.l.toDouble() shouldBe true
            b.z eq it.z.toDouble() / it.l.toDouble() shouldBe true
            b.length() eq 1 shouldBe true
            a.normalize()
            a shouldBe b
        }
        val a = Vector3D()
        a.norm() shouldBe Vector3D()
    }

    "Dot product" {
        v1 dot v2 eq 10 shouldBe true
        dot(v1, v2) eq 10 shouldBe true
    }

    "Cross product" {
        v1 cross v2 shouldBe Vector3D(-4, 8, -4)
        cross(v1, v2) shouldBe Vector3D(-4, 8, -4)
    }

    "Plus" {
        v1 + v2 shouldBe Vector3D(4, 4, 4)
        v1 + 3 shouldBe Vector3D(4, 5, 6)
    }

    "Minus" {
        v1 - v2 shouldBe Vector3D(-2, 0, 2)
        v1 - 6 shouldBe Vector3D(-5, -4, -3)
    }

    "Unary minus" {
        -v1 shouldBe Vector3D(-1, -2, -3)
    }

    "Times number" {
        v1 * 3 shouldBe Vector3D(3, 6, 9)
    }

    val tm = Matrix44(
            1, 2, 3, 4,
            5, 6, 7, 8,
            9, -9, -8, -7,
            -6, -5, -4, -3
    )

    "Times matrix" {
        v1 * tm shouldBe Vector3D(-8, 4.5, 2.75)
    }

    "Vectimes matrix" {
        v1 vecTimes tm shouldBe Vector3D(38, -13, -7)
        vecTimes(v1, tm) shouldBe Vector3D(38, -13, -7)
    }

    "Theta" {
        val tv = Vector3D(0, 0, 0.5)
        tv.theta() eq Math.PI / 3 shouldBe true
        tv.cosTheta() eq 0.5 shouldBe true
        tv.sinTheta2() eq 3.0 / 4 shouldBe true
        tv.sinTheta() eq sqrt(3.0) / 2.0 shouldBe true
        val tv2 = Vector3D(0, Math.sqrt(27.0), 3)
        tv2.theta() eq 0 shouldBe true
        tv2.normalize()
        tv2.theta() eq Math.PI / 3 shouldBe true
    }

    "Phi" {
        val tv = Vector3D(1, 1, 0)
        tv.phi() eq Math.PI / 4 shouldBe true
        tv.sinPhi() eq sqrt(2.0) / 2 shouldBe true
        tv.cosPhi() eq sqrt(2.0) / 2 shouldBe true
        val tv2 = Vector3D(-1, 1, 0)
        tv2.phi() eq 3 * Math.PI / 4 shouldBe true
        val tv4 = Vector3D(-1, -1, 0)
        tv4.phi() eq 5 * Math.PI / 4 shouldBe true
        val tv3 = Vector3D(1, -1, 0)
        tv3.phi() eq 7 * Math.PI / 4 shouldBe true
    }

    "ToString" {
        v1.toString() shouldBe "[1, 2, 3]"
        Vector3D(1.0, 25.47, 3.0000100000001).toString() shouldBe "[1, 25.47, 3.00001]"
    }

    "Hash code" {
        v1.hashCode() shouldBe listOf(1.0, 2.0, 3.0).hashCode()
    }
})