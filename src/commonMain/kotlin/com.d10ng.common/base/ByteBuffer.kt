@file:JsExport
package com.d10ng.common.base

import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * 字节数据处理
 */
enum class ByteOrder {
    BIG_ENDIAN,
    LITTLE_ENDIAN;

    companion object {
        fun nativeOrder(): ByteOrder {
            return LITTLE_ENDIAN
        }
    }
}

class ByteBuffer private constructor(private val capacity: Int) {
    private var limit: Int = capacity
    private var position: Int = 0
    private val array: ByteArray = ByteArray(capacity)
    private var bigEndian: Boolean = true
    private var nativeByteOrder: Boolean = true

    companion object {
        fun allocate(capacity: Int): ByteBuffer {
            if (capacity < 0) {
                throw IllegalArgumentException("Capacity must be non-negative")
            }
            return ByteBuffer(capacity)
        }

        fun wrap(array: ByteArray): ByteBuffer {
            return ByteBuffer(array.size).apply {
                put(array)
                rewind()
            }
        }
    }

    fun order(byteOrder: ByteOrder): ByteBuffer {
        bigEndian = (byteOrder == ByteOrder.BIG_ENDIAN)
        nativeByteOrder = (bigEndian == (ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN))
        return this
    }

    fun capacity(): Int = capacity

    fun limit(): Int = limit

    @JsName("setLimit")
    fun limit(newLimit: Int): ByteBuffer {
        if (newLimit < 0 || newLimit > capacity) {
            throw IllegalArgumentException("Limit out of bounds")
        }
        limit = newLimit
        if (position > limit) position = limit
        return this
    }

    fun position(): Int = position

    @JsName("setPosition")
    fun position(newPosition: Int): ByteBuffer {
        if (newPosition < 0 || newPosition > limit) {
            throw IllegalArgumentException("Position out of bounds")
        }
        position = newPosition
        return this
    }

    fun remaining(): Int = limit - position

    fun hasRemaining(): Boolean = position < limit

    fun clear(): ByteBuffer {
        position = 0
        limit = capacity
        return this
    }

    fun flip(): ByteBuffer {
        limit = position
        position = 0
        return this
    }

    fun rewind(): ByteBuffer {
        position = 0
        return this
    }

    fun get(): Byte {
        if (position >= limit) {
            throw BufferUnderflowException()
        }
        return array[position++]
    }

    @JsName("getByteByIndex")
    operator fun get(index: Int): Byte {
        if (index < 0 || index >= limit) {
            throw IndexOutOfBoundsException()
        }
        return array[index]
    }

    @JsName("getBytesByIndex")
    operator fun get(dst: ByteArray, offset: Int = 0, length: Int = dst.size): ByteBuffer {
        if (remaining() < length) {
            throw BufferUnderflowException()
        }
        dst.forEachIndexed { index, _ ->
            if (index >= offset && index < offset + length) {
                dst[index] = get()
            }
        }
        return this
    }

    fun put(b: Byte): ByteBuffer {
        if (position >= limit) {
            throw BufferOverflowException()
        }
        array[position++] = b
        return this
    }

    @JsName("putByteByIndex")
    fun put(index: Int, b: Byte): ByteBuffer {
        if (index < 0 || index >= limit) {
            throw IndexOutOfBoundsException()
        }
        array[index] = b
        return this
    }

    @JsName("putBytesByIndex")
    fun put(src: ByteArray, offset: Int = 0, length: Int = src.size): ByteBuffer {
        if (remaining() < length) {
            throw BufferOverflowException()
        }
        src.forEachIndexed { index, b ->
            if (index >= offset && index < offset + length) {
                put(b)
            }
        }
        return this
    }

    fun array(): ByteArray = array.copyOf()

    fun getInt(): Int {
        if (remaining() < 4) {
            throw BufferUnderflowException()
        }
        val bytes = ByteArray(4)
        get(bytes)
        return if (bigEndian) {
            ((bytes[0].toInt() and 0xff) shl 24) or
                    ((bytes[1].toInt() and 0xff) shl 16) or
                    ((bytes[2].toInt() and 0xff) shl 8) or
                    (bytes[3].toInt() and 0xff)
        } else {
            ((bytes[3].toInt() and 0xff) shl 24) or
                    ((bytes[2].toInt() and 0xff) shl 16) or
                    ((bytes[1].toInt() and 0xff) shl 8) or
                    (bytes[0].toInt() and 0xff)
        }
    }

    fun putInt(value: Int): ByteBuffer {
        if (remaining() < 4) {
            throw BufferOverflowException()
        }
        val bytes = ByteArray(4)
        if (bigEndian) {
            bytes[0] = (value shr 24).toByte()
            bytes[1] = (value shr 16).toByte()
            bytes[2] = (value shr 8).toByte()
            bytes[3] = value.toByte()
        } else {
            bytes[0] = value.toByte()
            bytes[1] = (value shr 8).toByte()
            bytes[2] = (value shr 16).toByte()
            bytes[3] = (value shr 24).toByte()
        }
        put(bytes)
        return this
    }

    @JsName("getIntByIndex")
    fun getInt(index: Int): Int {
        if (index < 0 || index > limit - 4) {
            throw IndexOutOfBoundsException()
        }
        val originalPosition = position
        position = index
        val value = getInt()
        position = originalPosition
        return value
    }

    @JsName("putIntByIndex")
    fun putInt(index: Int, value: Int): ByteBuffer {
        if (index < 0 || index > limit - 4) {
            throw IndexOutOfBoundsException()
        }
        val originalPosition = position
        position = index
        putInt(value)
        position = originalPosition
        return this
    }

    fun getShort(): Short {
        if (remaining() < 2) {
            throw BufferUnderflowException()
        }
        val bytes = ByteArray(2)
        get(bytes)
        return if (bigEndian) {
            ((bytes[0].toInt() and 0xff) shl 8 or (bytes[1].toInt() and 0xff)).toShort()
        } else {
            ((bytes[1].toInt() and 0xff) shl 8 or (bytes[0].toInt() and 0xff)).toShort()
        }
    }

    fun putShort(value: Short): ByteBuffer {
        if (remaining() < 2) {
            throw BufferOverflowException()
        }
        val bytes = ByteArray(2)
        if (bigEndian) {
            bytes[0] = (value.toInt() shr 8).toByte()
            bytes[1] = value.toByte()
        } else {
            bytes[0] = value.toByte()
            bytes[1] = (value.toInt() shr 8).toByte()
        }
        put(bytes)
        return this
    }

    @JsName("getShortByIndex")
    fun getShort(index: Int): Short {
        if (index < 0 || index > limit - 2) {
            throw IndexOutOfBoundsException()
        }
        val originalPosition = position
        position = index
        val value = getShort()
        position = originalPosition
        return value
    }

    @JsName("putShortByIndex")
    fun putShort(index: Int, value: Short): ByteBuffer {
        if (index < 0 || index > limit - 2) {
            throw IndexOutOfBoundsException()
        }
        val originalPosition = position
        position = index
        putShort(value)
        position = originalPosition
        return this
    }

    fun getFloat(): Float {
        if (remaining() < 4) {
            throw BufferUnderflowException()
        }
        val bytes = ByteArray(4)
        get(bytes)
        return Float.fromBits(
            if (bigEndian) {
                ((bytes[0].toInt() and 0xff) shl 24) or
                        ((bytes[1].toInt() and 0xff) shl 16) or
                        ((bytes[2].toInt() and 0xff) shl 8) or
                        (bytes[3].toInt() and 0xff)
            } else {
                ((bytes[3].toInt() and 0xff) shl 24) or
                        ((bytes[2].toInt() and 0xff) shl 16) or
                        ((bytes[1].toInt() and 0xff) shl 8) or
                        (bytes[0].toInt() and 0xff)
            }
        )
    }

    fun putFloat(value: Float): ByteBuffer {
        if (remaining() < 4) {
            throw BufferOverflowException()
        }
        val bits = value.toBits()
        val bytes = ByteArray(4)
        if (bigEndian) {
            bytes[0] = (bits shr 24).toByte()
            bytes[1] = (bits shr 16).toByte()
            bytes[2] = (bits shr 8).toByte()
            bytes[3] = bits.toByte()
        } else {
            bytes[0] = bits.toByte()
            bytes[1] = (bits shr 8).toByte()
            bytes[2] = (bits shr 16).toByte()
            bytes[3] = (bits shr 24).toByte()
        }
        put(bytes)
        return this
    }

    @JsName("getFloatByIndex")
    fun getFloat(index: Int): Float {
        if (index < 0 || index > limit - 4) {
            throw IndexOutOfBoundsException()
        }
        val originalPosition = position
        position = index
        val value = getFloat()
        position = originalPosition
        return value
    }

    @JsName("putFloatByIndex")
    fun putFloat(index: Int, value: Float): ByteBuffer {
        if (index < 0 || index > limit - 4) {
            throw IndexOutOfBoundsException()
        }
        val originalPosition = position
        position = index
        putFloat(value)
        position = originalPosition
        return this
    }

    fun getDouble(): Double {
        if (remaining() < 8) {
            throw BufferUnderflowException()
        }
        val bytes = ByteArray(8)
        get(bytes)
        return Double.fromBits(
            if (bigEndian) {
                ((bytes[0].toLong() and 0xff) shl 56) or
                        ((bytes[1].toLong() and 0xff) shl 48) or
                        ((bytes[2].toLong() and 0xff) shl 40) or
                        ((bytes[3].toLong() and 0xff) shl 32) or
                        ((bytes[4].toLong() and 0xff) shl 24) or
                        ((bytes[5].toLong() and 0xff) shl 16) or
                        ((bytes[6].toLong() and 0xff) shl 8) or
                        (bytes[7].toLong() and 0xff)
            } else {
                ((bytes[7].toLong() and 0xff) shl 56) or
                        ((bytes[6].toLong() and 0xff) shl 48) or
                        ((bytes[5].toLong() and 0xff) shl 40) or
                        ((bytes[4].toLong() and 0xff) shl 32) or
                        ((bytes[3].toLong() and 0xff) shl 24) or
                        ((bytes[2].toLong() and 0xff) shl 16) or
                        ((bytes[1].toLong() and 0xff) shl 8) or
                        (bytes[0].toLong() and 0xff)
            }
        )
    }

    fun putDouble(value: Double): ByteBuffer {
        if (remaining() < 8) {
            throw BufferOverflowException()
        }
        val bits = value.toBits()
        val bytes = ByteArray(8)
        if (bigEndian) {
            bytes[0] = (bits shr 56).toByte()
            bytes[1] = (bits shr 48).toByte()
            bytes[2] = (bits shr 40).toByte()
            bytes[3] = (bits shr 32).toByte()
            bytes[4] = (bits shr 24).toByte()
            bytes[5] = (bits shr 16).toByte()
            bytes[6] = (bits shr 8).toByte()
            bytes[7] = bits.toByte()
        } else {
            bytes[7] = (bits shr 56).toByte()
            bytes[6] = (bits shr 48).toByte()
            bytes[5] = (bits shr 40).toByte()
            bytes[4] = (bits shr 32).toByte()
            bytes[3] = (bits shr 24).toByte()
            bytes[2] = (bits shr 16).toByte()
            bytes[1] = (bits shr 8).toByte()
            bytes[0] = bits.toByte()
        }
        put(bytes)
        return this
    }

    @JsName("getDoubleByIndex")
    fun getDouble(index: Int): Double {
        if (index < 0 || index > limit - 8) {
            throw IndexOutOfBoundsException()
        }
        val originalPosition = position
        position = index
        val value = getDouble()
        position = originalPosition
        return value
    }

    @JsName("putDoubleByIndex")
    fun putDouble(index: Int, value: Double): ByteBuffer {
        if (index < 0 || index > limit - 8) {
            throw IndexOutOfBoundsException()
        }
        val originalPosition = position
        position = index
        putDouble(value)
        position = originalPosition
        return this
    }

    fun compact(): ByteBuffer {
        val remainingBytes = remaining()
        array.copyInto(array, 0, position, limit)
        position(remainingBytes)
        limit(capacity)
        return this
    }

    fun getRemainingBytes(): ByteArray {
        val data = array.copyOfRange(position, limit)
        position = limit
        return data
    }
}

class BufferOverflowException : Exception("ByteBuffer overflow")
class BufferUnderflowException : Exception("ByteBuffer underflow")