@file:JsExport
package com.d10ng.common.base

import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * 按位向固定容量的字节缓冲区写入数据。
 *
 * 缓冲区中的位按 MSB First 排列：位位置 `0` 对应第一个字节的最高有效位。
 * 整数写入同样按高位优先排列，但只使用 [writeBits] 指定的低位。
 *
 * 实例不是线程安全的。所有相对写入都会推进 [positionBits]。
 */
class BitWriter private constructor(private val buffer: ByteArray) {

    private var currentPositionBits: Long = 0

    /** 下一个写入位置，以位为单位。 */
    val positionBits: Long
        get() = currentPositionBits

    /** 缓冲区总容量，以位为单位。 */
    val capacityBits: Long
        get() = buffer.size.toLong() * Byte.SIZE_BITS

    /** 可继续写入的位数。 */
    val remainingBits: Long
        get() = capacityBits - currentPositionBits

    companion object {
        /**
         * 创建一个内容全为 `0` 的写入器。
         *
         * @param byteCapacity 缓冲区字节容量
         * @throws IllegalArgumentException 当 [byteCapacity] 为负数时
         */
        fun allocate(byteCapacity: Int): BitWriter {
            require(byteCapacity >= 0) {
                "byteCapacity must be non-negative, but was $byteCapacity"
            }
            return BitWriter(ByteArray(byteCapacity))
        }

        /**
         * 使用 [bytes] 的副本创建写入器。
         *
         * 后续写入不会修改传入的数组。写入 `0` 会正确清除已有的目标位。
         */
        fun wrap(bytes: ByteArray): BitWriter = BitWriter(bytes.copyOf())
    }

    /**
     * 设置下一个写入位置。
     *
     * @param newPositionBits 新位置，以位为单位
     * @return 当前写入器
     * @throws IllegalArgumentException 当位置不在 `0..capacityBits` 时
     */
    @JsName("setPositionBits")
    fun positionBits(newPositionBits: Long): BitWriter {
        require(newPositionBits in 0..capacityBits) {
            "positionBits must be in 0..$capacityBits, but was $newPositionBits"
        }
        currentPositionBits = newPositionBits
        return this
    }

    /**
     * 写入一个比特。
     *
     * @throws BufferOverflowException 当缓冲区没有剩余空间时
     */
    fun writeBit(value: Boolean): BitWriter {
        ensureWritable(1)
        writeBitAt(currentPositionBits, value)
        currentPositionBits++
        return this
    }

    /**
     * 将 [value] 的低 [bitCount] 位按高位优先写入。
     *
     * 例如 `writeBits(0b101, 3)` 会依次写入 `1、0、1`。
     *
     * @param value 要写入的值
     * @param bitCount 位数，范围为 `0..32`
     * @return 当前写入器
     * @throws IllegalArgumentException 当 [bitCount] 不在 `0..32` 时
     * @throws BufferOverflowException 当缓冲区剩余空间不足时
     */
    @JsName("writeIntBits")
    fun writeBits(value: Int, bitCount: Int): BitWriter {
        require(bitCount in 0..Int.SIZE_BITS) {
            "bitCount must be in 0..${Int.SIZE_BITS}, but was $bitCount"
        }
        return writeBits(value.toLong(), bitCount)
    }

    /**
     * 将 [value] 的低 [bitCount] 位按高位优先写入。
     *
     * @param value 要写入的值
     * @param bitCount 位数，范围为 `0..64`
     * @return 当前写入器
     * @throws IllegalArgumentException 当 [bitCount] 不在 `0..64` 时
     * @throws BufferOverflowException 当缓冲区剩余空间不足时
     */
    @JsName("writeLongBits")
    fun writeBits(value: Long, bitCount: Int): BitWriter {
        require(bitCount in 0..Long.SIZE_BITS) {
            "bitCount must be in 0..${Long.SIZE_BITS}, but was $bitCount"
        }
        ensureWritable(bitCount.toLong())

        for (shift in bitCount - 1 downTo 0) {
            writeBitAt(currentPositionBits, ((value ushr shift) and 1L) != 0L)
            currentPositionBits++
        }
        return this
    }

    /**
     * 将整个字节数组按 MSB First 写入。
     *
     * 即使当前位置没有按字节对齐，每个源字节内部仍从最高有效位开始写入。
     *
     * @param bytes 要写入的字节数组
     * @return 当前写入器
     * @throws BufferOverflowException 当缓冲区剩余空间不足时
     */
    fun writeBytes(bytes: ByteArray): BitWriter {
        ensureWritable(bytes.size.toLong() * Byte.SIZE_BITS)
        for (byte in bytes) {
            writeBits(byte.toInt() and 0xff, Byte.SIZE_BITS)
        }
        return this
    }

    /**
     * 将写入位置重置为 `0`，不修改缓冲区内容。
     */
    fun rewind(): BitWriter {
        currentPositionBits = 0
        return this
    }

    /**
     * 清空缓冲区并将写入位置重置为 `0`。
     */
    fun clear(): BitWriter {
        buffer.fill(0)
        currentPositionBits = 0
        return this
    }

    /**
     * 返回当前缓冲区内容的副本。
     */
    fun toByteArray(): ByteArray = buffer.copyOf()

    private fun ensureWritable(bitCount: Long) {
        if (remainingBits < bitCount) throw BufferOverflowException()
    }

    private fun writeBitAt(bitPosition: Long, value: Boolean) {
        val byteIndex = (bitPosition / Byte.SIZE_BITS).toInt()
        val bitIndex = Byte.SIZE_BITS - 1 - (bitPosition % Byte.SIZE_BITS).toInt()
        val mask = 1 shl bitIndex
        val byteValue = buffer[byteIndex].toInt()
        buffer[byteIndex] = if (value) {
            (byteValue or mask).toByte()
        } else {
            (byteValue and mask.inv()).toByte()
        }
    }
}
