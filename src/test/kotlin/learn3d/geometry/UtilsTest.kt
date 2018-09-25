package learn3d.geometry

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class UtilsTest : StringSpec({
    "Equals" {
        val a = 1.0000000001
        val b = 1.0000000002
        a eq b shouldBe true
    }
    "Not equals" {
        val a = 1.000000001
        val b = 1.000000005
        a neq b shouldBe true
    }
    "Greater" {
        val a = 1.000000005
        val b = 1.000000001
        a gt b shouldBe true
    }
    "Less" {
        val a = 1.000000001
        val b = 1.000000005
        a lt b shouldBe true
    }
    "Less or equals" {
        val a = 1.000000001
        val b = 1.000000001
        val c = 1.000000005
        a le b shouldBe true
        b le c shouldBe true
    }
    "Greater or equals" {
        val a = 1.000000005
        val b = 1.000000005
        val c = 1.000000001
        a ge b shouldBe true
        b ge c shouldBe true
    }
    "DF test" {
        DF.format(1.0000000001) shouldBe "1"
        DF.format(2) shouldBe "2"
        DF.format(1.000000001) shouldBe "1.000000001"
        DF.format(123.456) shouldBe "123.456"
    }
})