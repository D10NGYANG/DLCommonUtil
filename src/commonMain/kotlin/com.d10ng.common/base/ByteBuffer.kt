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

/**
 * ByteBuffer 类,用于进行字节缓冲区操作。
 *
 * @param capacity 缓冲区容量,必须为非负数。
 */
class ByteBuffer private constructor(private val capacity: Int) {
    // 缓冲区的限制位置,表示缓冲区中可用数据的末尾位置。
    private var limit: Int = capacity
    // 缓冲区的当前位置,表示下一个读写操作的位置。
    private var position: Int = 0
    // 存储字节数据的底层字节数组。
    private val array: ByteArray = ByteArray(capacity)
    // 字节序标志,true 表示大端字节序,false 表示小端字节序。
    private var bigEndian: Boolean = true
    // 本地字节序标志,true 表示使用平台的本地字节序。
    private var nativeByteOrder: Boolean = true

    companion object {
        /**
         * 创建一个指定容量的 ByteBuffer 实例。
         *
         * @param capacity [Int] 缓冲区容量,必须为非负数。
         * @return 创建的 [ByteBuffer] 实例。
         * @throws IllegalArgumentException 如果容量为负数。
         */
        fun allocate(capacity: Int): ByteBuffer {
            if (capacity < 0) {
                throw IllegalArgumentException("Capacity must be non-negative")
            }
            return ByteBuffer(capacity)
        }

        /**
         * 将一个字节数组包装为 ByteBuffer 实例。
         *
         * @param array [ByteArray] 要包装的字节数组。
         * @return [ByteBuffer] 包装后的 ByteBuffer 实例,缓冲区位置被设置为 0,限制位置被设置为数组的长度。
         */
        fun wrap(array: ByteArray): ByteBuffer {
            return ByteBuffer(array.size).apply {
                put(array)
                rewind()
            }
        }
    }

    /**
     * 设置 ByteBuffer 的字节序。
     *
     * @param byteOrder [ByteOrder] 要设置的字节序,可以是 ByteOrder.BIG_ENDIAN 或 ByteOrder.LITTLE_ENDIAN。
     * @return [ByteBuffer] 返回当前 ByteBuffer 实例,以支持方法链式调用。
     */
    fun order(byteOrder: ByteOrder): ByteBuffer {
        // 根据传入的字节序设置 bigEndian 标志
        bigEndian = (byteOrder == ByteOrder.BIG_ENDIAN)
        // 判断设置的字节序是否与平台的本地字节序一致,并设置 nativeByteOrder 标志
        nativeByteOrder = (bigEndian == (ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN))
        return this
    }

    /**
     * 返回 ByteBuffer 的容量。
     *
     * @return [Int] ByteBuffer 的容量,即可以存储的最大字节数。
     */
    fun capacity(): Int = capacity

    /**
     * 返回 ByteBuffer 的限制位置。
     *
     * @return [Int] ByteBuffer 的限制位置,表示缓冲区中可用数据的末尾位置。
     */
    fun limit(): Int = limit

    /**
     * 设置 ByteBuffer 的限制位置。
     *
     * @param newLimit [Int] 新的限制位置,必须在 0 到 capacity 之间(包含 0 和 capacity)。
     * @return [ByteBuffer] 返回当前 ByteBuffer 实例,以支持方法链式调用。
     * @throws IllegalArgumentException 如果新的限制位置超出范围。
     */
    @JsName("setLimit")
    fun limit(newLimit: Int): ByteBuffer {
        // 检查新的限制位置是否在有效范围内
        if (newLimit !in 0 .. capacity) {
            throw IllegalArgumentException("Limit out of bounds")
        }
        // 更新限制位置
        limit = newLimit
        // 如果当前位置大于新的限制位置,将当前位置重置为新的限制位置
        if (position > limit) position = limit
        return this
    }

    /**
     * 返回 ByteBuffer 的当前位置。
     *
     * @return [Int] ByteBuffer 的当前位置,表示下一个读写操作的位置。
     */
    fun position(): Int = position

    /**
     * 设置 ByteBuffer 的当前位置。
     *
     * @param newPosition [Int] 新的当前位置,必须在 0 到 limit 之间(包含 0 和 limit)。
     * @return [ByteBuffer] 返回当前 ByteBuffer 实例,以支持方法链式调用。
     * @throws IllegalArgumentException 如果新的当前位置超出范围。
     */
    @JsName("setPosition")
    fun position(newPosition: Int): ByteBuffer {
        // 检查新的当前位置是否在有效范围内
        if (newPosition !in 0 .. limit) {
            throw IllegalArgumentException("Position out of bounds")
        }
        // 更新当前位置
        position = newPosition
        return this
    }

    /**
     * 返回 ByteBuffer 中剩余的可读/写字节数。
     *
     * @return [Int] 剩余的可读/写字节数,等于 limit - position。
     */
    fun remaining(): Int = limit - position

    /**
     * 判断 ByteBuffer 中是否还有剩余的可读/写字节。
     *
     * @return [Boolean] 如果 position 小于 limit,返回 true,表示还有剩余的可读/写字节;否则返回 false。
     */
    fun hasRemaining(): Boolean = position < limit

    /**
     * 清空 ByteBuffer,将位置设置为 0,限制设置为容量。
     *
     * @return [ByteBuffer] 返回当前 ByteBuffer 实例,以支持方法链式调用。
     */
    fun clear(): ByteBuffer {
        position = 0
        limit = capacity
        return this
    }

    /**
     * 翻转 ByteBuffer,将限制设置为当前位置,并将位置设置为 0。
     *
     * 通常在写入数据后调用 flip() 方法,将 ByteBuffer 从写模式切换到读模式。
     *
     * @return [ByteBuffer] 返回当前 ByteBuffer 实例,以支持方法链式调用。
     */
    fun flip(): ByteBuffer {
        limit = position
        position = 0
        return this
    }

    /**
     * 重置 ByteBuffer 的位置为 0,保持限制不变。
     *
     * 通常在读取完 ByteBuffer 后调用 rewind() 方法,可以重新从头开始读取数据。
     *
     * @return [ByteBuffer] 返回当前 ByteBuffer 实例,以支持方法链式调用。
     */
    fun rewind(): ByteBuffer {
        position = 0
        return this
    }

    /**
     * 从 ByteBuffer 中读取一个字节。
     *
     * @return [Byte] 读取的字节值。
     * @throws BufferUnderflowException 如果 ByteBuffer 中没有足够的可读字节。
     */
    fun get(): Byte {
        // 检查是否有足够的可读字节
        if (position >= limit) {
            throw BufferUnderflowException()
        }
        // 读取当前位置的字节,并将位置增加 1
        return array[position++]
    }

    /**
     * 从 ByteBuffer 中指定索引处读取一个字节。
     *
     * @param index [Int] 要读取的字节的索引,必须在 0 到 limit-1 之间。
     * @return [Byte] 读取的字节值。
     * @throws IndexOutOfBoundsException 如果索引超出范围。
     */
    @JsName("getByteByIndex")
    operator fun get(index: Int): Byte {
        // 检查索引是否在有效范围内
        if (index !in 0 ..< limit) {
            throw IndexOutOfBoundsException()
        }
        // 读取指定索引处的字节
        return array[index]
    }

    /**
     * 从 ByteBuffer 中读取一定数量的字节到目标字节数组中。
     *
     * @param dst [ByteArray] 目标字节数组。
     * @param offset [Int] 目标字节数组的起始偏移量,默认为 0。
     * @param length [Int] 要读取的字节数,默认为目标字节数组的长度。
     * @return [ByteBuffer] 返回当前 ByteBuffer 实例,以支持方法链式调用。
     * @throws BufferUnderflowException 如果 ByteBuffer 中没有足够的可读字节。
     */
    @JsName("getBytesByIndex")
    operator fun get(dst: ByteArray, offset: Int = 0, length: Int = dst.size): ByteBuffer {
        // 检查是否有足够的可读字节
        if (remaining() < length) {
            throw BufferUnderflowException()
        }
        // 遍历目标字节数组,将 ByteBuffer 中的字节读取到目标字节数组的指定范围内
        dst.forEachIndexed { index, _ ->
            if (index >= offset && index < offset + length) {
                dst[index] = get()
            }
        }
        return this
    }

    /**
     * 从 ByteBuffer 中读取指定数量的字节,并返回一个新的字节数组。
     *
     * @param size [Int] 要读取的字节数。
     * @return [ByteArray] 包含读取的字节的新字节数组。
     * @throws BufferUnderflowException 如果 ByteBuffer 中没有足够的可读字节。
     */
    fun getBytes(size: Int): ByteArray {
        // 检查是否有足够的可读字节
        if (remaining() < size) {
            throw BufferUnderflowException()
        }
        // 创建一个指定大小的新字节数组
        val bytes = ByteArray(size)
        // 将 ByteBuffer 中的字节读取到新字节数组中
        get(bytes)
        // 返回新字节数组
        return bytes
    }

    /**
     * 将一个字节写入 ByteBuffer。
     *
     * @param b [Byte] 要写入的字节值。
     * @return [ByteBuffer] 返回当前 ByteBuffer 实例,以支持方法链式调用。
     * @throws BufferOverflowException 如果 ByteBuffer 中没有足够的空间写入字节。
     */
    fun put(b: Byte): ByteBuffer {
        // 检查是否有足够的空间写入字节
        if (position >= limit) {
            throw BufferOverflowException()
        }
        // 将字节写入当前位置,并将位置增加 1
        array[position++] = b
        return this
    }

    /**
     * 将一个字节写入 ByteBuffer 的指定索引处。
     *
     * @param index [Int] 要写入字节的索引,必须在 0 到 limit-1 之间。
     * @param b [Byte] 要写入的字节值。
     * @return [ByteBuffer] 返回当前 ByteBuffer 实例,以支持方法链式调用。
     * @throws IndexOutOfBoundsException 如果索引超出范围。
     */
    @JsName("putByteByIndex")
    fun put(index: Int, b: Byte): ByteBuffer {
        // 检查索引是否在有效范围内
        if (index !in 0 ..< limit) {
            throw IndexOutOfBoundsException()
        }
        // 将字节写入指定索引处
        array[index] = b
        return this
    }

    /**
     * 将字节数组中的数据写入 ByteBuffer。
     *
     * @param src [ByteArray] 源字节数组。
     * @param offset [Int] 源字节数组的起始偏移量,默认为 0。
     * @param length [Int] 要写入的字节数,默认为源字节数组的长度。
     * @return [ByteBuffer] 返回当前 ByteBuffer 实例,以支持方法链式调用。
     * @throws BufferOverflowException 如果 ByteBuffer 中没有足够的空间写入字节。
     */
    @JsName("putBytesByIndex")
    fun put(src: ByteArray, offset: Int = 0, length: Int = src.size): ByteBuffer {
        // 检查是否有足够的空间写入字节
        if (remaining() < length) {
            throw BufferOverflowException()
        }
        // 遍历源字节数组,将指定范围内的字节写入 ByteBuffer
        src.forEachIndexed { index, b ->
            if (index >= offset && index < offset + length) {
                put(b)
            }
        }
        return this
    }

    /**
     * 返回 ByteBuffer 的底层字节数组的副本。
     *
     * @return [ByteArray] ByteBuffer 的底层字节数组的副本。
     */
    fun array(): ByteArray = array.copyOf()

    /**
     * 从 ByteBuffer 中读取一个整数值。
     *
     * @return [Int] 读取的整数值。
     * @throws BufferUnderflowException 如果 ByteBuffer 中没有足够的可读字节。
     */
    fun getInt(): Int {
        // 检查是否有足够的可读字节
        if (remaining() < 4) {
            throw BufferUnderflowException()
        }
        // 将 ByteBuffer 中的 4 个字节读取到字节数组中
        val bytes = getBytes(4)
        // 根据字节序将字节数组转换为整数值
        return if (bigEndian) bytes.toInt() else bytes.let { it.reverse(); it.toInt() }
    }

    /**
     * 将一个整数值写入 ByteBuffer。
     *
     * @param value [Int] 要写入的整数值。
     * @return [ByteBuffer] 返回当前 ByteBuffer 实例,以支持方法链式调用。
     * @throws BufferOverflowException 如果 ByteBuffer 中没有足够的空间写入整数值。
     */
    fun putInt(value: Int): ByteBuffer {
        // 检查是否有足够的空间写入整数值
        if (remaining() < 4) {
            throw BufferOverflowException()
        }
        val bytes = value.toByteArray(4)
        // 根据字节序将整数值转换为字节数组
        if (bigEndian.not()) bytes.reverse()
        // 将字节数组写入 ByteBuffer
        put(bytes)
        return this
    }

    /**
     * 从 ByteBuffer 的指定索引处读取一个整数值。
     *
     * @param index [Int] 要读取整数值的索引,必须在 0 到 limit-4 之间。
     * @return [Int] 读取的整数值。
     * @throws IndexOutOfBoundsException 如果索引超出范围。
     */
    @JsName("getIntByIndex")
    fun getInt(index: Int): Int {
        // 检查索引是否在有效范围内
        if (index !in 0 .. limit - 4) {
            throw IndexOutOfBoundsException()
        }
        // 保存当前位置
        val originalPosition = position
        // 将位置设置为指定索引
        position = index
        // 读取整数值
        val value = getInt()
        // 恢复原始位置
        position = originalPosition
        return value
    }

    /**
     * 将一个整数值写入 ByteBuffer 的指定索引处。
     *
     * @param index [Int] 要写入整数值的索引,必须在 0 到 limit-4 之间。
     * @param value [Int] 要写入的整数值。
     * @return [ByteBuffer] 返回当前 ByteBuffer 实例,以支持方法链式调用。
     * @throws IndexOutOfBoundsException 如果索引超出范围。
     */
    @JsName("putIntByIndex")
    fun putInt(index: Int, value: Int): ByteBuffer {
        // 检查索引是否在有效范围内
        if (index !in 0 .. limit - 4) {
            throw IndexOutOfBoundsException()
        }
        // 保存当前位置
        val originalPosition = position
        // 将位置设置为指定索引
        position = index
        // 写入整数值
        putInt(value)
        // 恢复原始位置
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