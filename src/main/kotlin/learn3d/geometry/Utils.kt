package learn3d.geometry

import java.text.DecimalFormat
import kotlin.math.abs

const val PRECISION = 1e-9

val DF = DecimalFormat("#.#########")

infix fun Double.eq(a: Number) = abs(this - a.toDouble()) < PRECISION

infix fun Double.lt(a: Number) = a.toDouble() - this > PRECISION

infix fun Double.gt(a: Number) = this - a.toDouble() > PRECISION

infix fun Double.neq(a: Number) = !(this eq a.toDouble())

infix fun Double.le(a: Number) = !(this gt a.toDouble())

infix fun Double.ge(a: Number) = !(this lt a.toDouble())

fun cross(v1: Vector3D, v2: Vector3D) = v1 cross v2

fun dot(v1: Vector3D, v2: Vector3D) = v1 dot v2

fun vecTimes(v: Vector3D, m: Matrix44) = v vecTimes m

fun vecTimes(m: Matrix44, v: Vector3D) = m vecTimes v