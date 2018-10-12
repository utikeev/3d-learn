package learn3d.geometry

import java.util.Objects
import kotlin.math.*

class Vector3D {
    var x: Double
        private set
    var y: Double
        private set
    var z: Double
        private set

    constructor(x: Double, y: Double, z: Double) {
        this.x = x
        this.y = y
        this.z = z
    }

    constructor() : this(.0, .0, .0)
    constructor(_x: Number) : this(_x.toDouble(), _x.toDouble(), _x.toDouble())
    constructor(_x: Number, _y: Number, _z: Number) : this(_x.toDouble(), _y.toDouble(), _z.toDouble())
    constructor(v: Vector3D) : this(v.x, v.y, v.z)

    private fun length2(): Double = x * x + y * y + z * z

    fun length(): Double = sqrt(length2())

    fun norm(): Vector3D {
        val len = length()
        if (len > 0) {
            // Multiplication works faster than division
            val invLen = 1 / len
            return Vector3D(x * invLen, y * invLen, z * invLen)
        }
        return Vector3D(this)
    }

    fun normalize() {
        val v = norm()
        this.x = v.x
        this.y = v.y
        this.z = v.z
    }

    infix fun dot(v: Vector3D): Double = x * v.x + y * v.y + z * v.z

    infix fun cross(v: Vector3D): Vector3D {
        return Vector3D(
                y * v.z - z * v.y,
                z * v.x - x * v.z,
                x * v.y - y * v.x
        )
    }

    operator fun plus(v: Vector3D): Vector3D = Vector3D(x + v.x, y + v.y, z + v.z)
    operator fun plus(x: Number): Vector3D = this + Vector3D(x)

    operator fun unaryMinus(): Vector3D = Vector3D(-x, -y, -z)

    operator fun minus(v: Vector3D): Vector3D = this + (-v)
    operator fun minus(x: Number): Vector3D = this - Vector3D(x)

    operator fun times(r: Number): Vector3D {
        val dr = r.toDouble()
        return Vector3D(x * dr, y * dr, z * dr)
    }
    operator fun times(m: Matrix44): Vector3D = m * this
    infix fun vecTimes(m: Matrix44): Vector3D = m vecTimes this
    override operator fun equals(other: Any?): Boolean {
        return other is Vector3D && other.x eq x && other.y eq y && other.z eq z
    }

    override fun hashCode(): Int = Objects.hash(x, y, z)

    fun theta(): Double = acos(z.coerceIn(-1.0, 1.0))
    fun cosTheta(): Double = z
    fun sinTheta2(): Double = max(.0, 1.0 - cosTheta() * cosTheta())
    fun sinTheta(): Double = sqrt(sinTheta2())

    fun phi(): Double {
        val p = atan2(y, x)
        return if (p lt .0) p + 2 * PI else p
    }
    fun cosPhi(): Double {
        val sinT = sinTheta()
        return if (sinT eq .0) 1.0 else (norm().x / sinT).coerceIn(-1.0, 1.0)
    }
    fun sinPhi(): Double {
        val sinT = sinTheta()
        return if (sinT eq .0) .0 else (norm().y / sinT).coerceIn(-1.0, 1.0)
    }

    override fun toString(): String = "[${DF.format(x)}, ${DF.format(y)}, ${DF.format(z)}]"
}

operator fun Number.times(v: Vector3D): Vector3D = v * this
operator fun Number.plus(v: Vector3D): Vector3D = v + this
operator fun Number.minus(v: Vector3D): Vector3D = v - this