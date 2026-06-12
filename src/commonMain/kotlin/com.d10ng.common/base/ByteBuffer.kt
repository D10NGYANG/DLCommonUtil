@file:JsExport
package com.d10ng.common.base

import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * 字节序。
 */
enum class ByteOrder {
    /** 高位字节在前。 */
    BIG_ENDIAN,

    /** 低位字节在前。 */
    LITTLE_ENDIAN;

    companion object {
        /**
         * 返回本库支持目标的约定本地字节序。
         *
         * 当前 Kotlin/Native 与 JavaScript 支持目标均按小端序处理。
         *
         * @return [LITTLE_ENDIAN]
         */
        fun nativeOrder(): ByteOrder = LITTLE_ENDIAN
    }
}

/**
 * 提供带位置、限制和字节序的跨平台字节缓冲区。
 *
 * 实例不是线程安全的。相对读写会推进 [position]，绝对索引读写不会改变位置。
 *
 * @param capacity 缓冲区容量，必须为非负数
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
    companion object {
        /**
         * 创建一个指定容量的 ByteBuffer 实例。
         *
         * @param capacity 缓冲区容量，必须为非负数
         * @return 新的空缓冲区
         * @throws IllegalArgumentException 当 [capacity] 为负数时
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
         * 该实现会复制 [array]，后续修改不会相互影响。
         *
         * @param array 要复制到缓冲区的字节数组
         * @return 位置为 `0`、限制为数组长度的新缓冲区
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
     * 将位置重置为 `0`。
     *
     * @deprecated 使用 [rewind]。
     */
    @Deprecated("Use rewind() instead.", ReplaceWith("rewind()"))
    fun reset() {
        rewind()
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
     * 从当前位置读取一个字节。
     *
     * @deprecated 使用 [get]。
     */
    @Deprecated("Use get() instead.", ReplaceWith("get()"))
    fun getByte(): Byte = get()

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
     * @param length 要读取的字节数，默认覆盖 [offset] 到数组末尾
     * @return [ByteBuffer] 返回当前 ByteBuffer 实例,以支持方法链式调用。
     * @throws BufferUnderflowException 如果 ByteBuffer 中没有足够的可读字节。
     * @throws IndexOutOfBoundsException 当 [offset] 和 [length] 不构成目标数组的有效范围时
     */
    @JsName("getBytesByIndex")
    operator fun get(
        dst: ByteArray,
        offset: Int = 0,
        length: Int = dst.size - offset,
    ): ByteBuffer {
        checkArrayRange(dst.size, offset, length)
        if (remaining() < length) {
            throw BufferUnderflowException()
        }
        array.copyInto(dst, destinationOffset = offset, startIndex = position, endIndex = position + length)
        position += length
        return this
    }

    /**
     * 从 ByteBuffer 中读取指定数量的字节,并返回一个新的字节数组。
     *
     * @param size [Int] 要读取的字节数。
     * @return [ByteArray] 包含读取的字节的新字节数组。
     * @throws BufferUnderflowException 如果 ByteBuffer 中没有足够的可读字节。
     * @throws IllegalArgumentException 当 [size] 为负数时
     */
    fun getBytes(size: Int): ByteArray {
        require(size >= 0) { "size must be non-negative, but was $size" }
        if (remaining() < size) {
            throw BufferUnderflowException()
        }
        return ByteArray(size).also { get(it) }
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
     * 在当前位置写入一个字节。
     *
     * @deprecated 使用 [put]。
     */
    @Deprecated("Use put() instead.", ReplaceWith("put(b)"))
    fun setByte(b: Byte) = put(b)

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
     * @return [ByteBuffer] 返回当前 ByteBuffer 实例,以支持方法链式调用。
     * @throws BufferOverflowException 如果 ByteBuffer 中没有足够的空间写入字节。
     */
    @JsName("putBytes")
    fun put(src: ByteArray) = put(src, 0, src.size)

    /**
     * 将字节数组中的数据写入 ByteBuffer。
     *
     * @param src [ByteArray] 源字节数组。
     * @param offset [Int] 源字节数组的起始偏移量,默认为 0。
     * @param length 要写入的字节数，默认覆盖 [offset] 到数组末尾
     * @return [ByteBuffer] 返回当前 ByteBuffer 实例,以支持方法链式调用。
     * @throws BufferOverflowException 如果 ByteBuffer 中没有足够的空间写入字节。
     * @throws IndexOutOfBoundsException 当 [offset] 和 [length] 不构成源数组的有效范围时
     */
    @JsName("putBytesByIndex")
    fun put(
        src: ByteArray,
        offset: Int = 0,
        length: Int = src.size - offset,
    ): ByteBuffer {
        checkArrayRange(src.size, offset, length)
        if (remaining() < length) {
            throw BufferOverflowException()
        }
        src.copyInto(array, destinationOffset = position, startIndex = offset, endIndex = offset + length)
        position += length
        return this
    }

    /**
     * 在当前位置写入整个字节数组。
     *
     * @deprecated 使用 [put]。
     */
    @Deprecated("Use put(value) instead.", ReplaceWith("put(value)"))
    fun setBytes(value: ByteArray) {
        put(value)
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
        ensureReadable(Int.SIZE_BYTES)
        return readLong(position, Int.SIZE_BYTES).also { position += Int.SIZE_BYTES }.toInt()
    }

    /**
     * 将一个整数值写入 ByteBuffer。
     *
     * @param value [Int] 要写入的整数值。
     * @return [ByteBuffer] 返回当前 ByteBuffer 实例,以支持方法链式调用。
     * @throws BufferOverflowException 如果 ByteBuffer 中没有足够的空间写入整数值。
     */
    fun putInt(value: Int): ByteBuffer {
        ensureWritable(Int.SIZE_BYTES)
        writeLong(position, value.toLong(), Int.SIZE_BYTES)
        position += Int.SIZE_BYTES
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
        checkAbsoluteRange(index, Int.SIZE_BYTES)
        return readLong(index, Int.SIZE_BYTES).toInt()
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
        checkAbsoluteRange(index, Int.SIZE_BYTES)
        writeLong(index, value.toLong(), Int.SIZE_BYTES)
        return this
    }

    /**
     * 从 ByteBuffer 中读取一个短整数值。
     *
     * @return [Short] 读取的短整数值。
     * @throws BufferUnderflowException 如果 ByteBuffer 中没有足够的可读字节。
     */
    fun getShort(): Short {
        ensureReadable(Short.SIZE_BYTES)
        return readLong(position, Short.SIZE_BYTES)
            .also { position += Short.SIZE_BYTES }
            .toShort()
    }

    /**
     * 将一个短整数值写入 ByteBuffer。
     *
     * @param value [Short] 要写入的短整数值。
     * @return [ByteBuffer] 返回当前 ByteBuffer 实例,以支持方法链式调用。
     * @throws BufferOverflowException 如果 ByteBuffer 中没有足够的空间写入短整数值。
     */
    fun putShort(value: Short): ByteBuffer {
        ensureWritable(Short.SIZE_BYTES)
        writeLong(position, value.toLong(), Short.SIZE_BYTES)
        position += Short.SIZE_BYTES
        return this
    }

    /**
     * 从 ByteBuffer 的指定索引处读取一个短整数值。
     *
     * @param index [Int] 要读取短整数值的索引,必须在 0 到 limit-2 之间。
     * @return [Short] 读取的短整数值。
     * @throws IndexOutOfBoundsException 如果索引超出范围。
     */
    @JsName("getShortByIndex")
    fun getShort(index: Int): Short {
        checkAbsoluteRange(index, Short.SIZE_BYTES)
        return readLong(index, Short.SIZE_BYTES).toShort()
    }

    /**
     * 将一个短整数值写入 ByteBuffer 的指定索引处。
     *
     * @param index [Int] 要写入短整数值的索引,必须在 0 到 limit-2 之间。
     * @param value [Short] 要写入的短整数值。
     * @return [ByteBuffer] 返回当前 ByteBuffer 实例,以支持方法链式调用。
     * @throws IndexOutOfBoundsException 如果索引超出范围。
     */
    @JsName("putShortByIndex")
    fun putShort(index: Int, value: Short): ByteBuffer {
        checkAbsoluteRange(index, Short.SIZE_BYTES)
        writeLong(index, value.toLong(), Short.SIZE_BYTES)
        return this
    }

    /**
     * 从 ByteBuffer 中读取一个浮点数值。
     *
     * @return [Float] 读取的浮点数值。
     * @throws BufferUnderflowException 如果 ByteBuffer 中没有足够的可读字节。
     */
    fun getFloat(): Float {
        return Float.fromBits(getInt())
    }

    /**
     * 将一个浮点数值写入 ByteBuffer。
     *
     * @param value [Float] 要写入的浮点数值。
     * @return [ByteBuffer] 返回当前 ByteBuffer 实例,以支持方法链式调用。
     * @throws BufferOverflowException 如果 ByteBuffer 中没有足够的空间写入浮点数值。
     */
    fun putFloat(value: Float): ByteBuffer {
        return putInt(value.toBits())
    }

    /**
     * 从 ByteBuffer 的指定索引处读取一个浮点数值。
     *
     * @param index [Int] 要读取浮点数值的索引,必须在 0 到 limit-4 之间。
     * @return [Float] 读取的浮点数值。
     * @throws IndexOutOfBoundsException 如果索引超出范围。
     */
    @JsName("getFloatByIndex")
    fun getFloat(index: Int): Float {
        return Float.fromBits(getInt(index))
    }

    /**
     * 将一个浮点数值写入 ByteBuffer 的指定索引处。
     *
     * @param index [Int] 要写入浮点数值的索引,必须在 0 到 limit-4 之间。
     * @param value [Float] 要写入的浮点数值。
     * @return [ByteBuffer] 返回当前 ByteBuffer 实例,以支持方法链式调用。
     * @throws IndexOutOfBoundsException 如果索引超出范围。
     */
    @JsName("putFloatByIndex")
    fun putFloat(index: Int, value: Float): ByteBuffer {
        return putInt(index, value.toBits())
    }

    /**
     * 从 ByteBuffer 中读取一个双精度浮点数值。
     *
     * @return [Double] 读取的双精度浮点数值。
     * @throws BufferUnderflowException 如果 ByteBuffer 中没有足够的可读字节。
     */
    fun getDouble(): Double {
        ensureReadable(Long.SIZE_BYTES)
        return Double.fromBits(
            readLong(position, Long.SIZE_BYTES).also { position += Long.SIZE_BYTES },
        )
    }

    /**
     * 将一个双精度浮点数值写入 ByteBuffer。
     *
     * @param value [Double] 要写入的双精度浮点数值。
     * @return [ByteBuffer] 返回当前 ByteBuffer 实例,以支持方法链式调用。
     * @throws BufferOverflowException 如果 ByteBuffer 中没有足够的空间写入双精度浮点数值。
     */
    fun putDouble(value: Double): ByteBuffer {
        ensureWritable(Long.SIZE_BYTES)
        writeLong(position, value.toBits(), Long.SIZE_BYTES)
        position += Long.SIZE_BYTES
        return this
    }

    /**
     * 从 ByteBuffer 的指定索引处读取一个双精度浮点数值。
     *
     * @param index [Int] 要读取双精度浮点数值的索引,必须在 0 到 limit-8 之间。
     * @return [Double] 读取的双精度浮点数值。
     * @throws IndexOutOfBoundsException 如果索引超出范围。
     */
    @JsName("getDoubleByIndex")
    fun getDouble(index: Int): Double {
        checkAbsoluteRange(index, Long.SIZE_BYTES)
        return Double.fromBits(readLong(index, Long.SIZE_BYTES))
    }

    /**
     * 将一个双精度浮点数值写入 ByteBuffer 的指定索引处。
     *
     * @param index [Int] 要写入双精度浮点数值的索引,必须在 0 到 limit-8 之间。
     * @param value [Double] 要写入的双精度浮点数值。
     * @return [ByteBuffer] 返回当前 ByteBuffer 实例,以支持方法链式调用。
     * @throws IndexOutOfBoundsException 如果索引超出范围。
     */
    @JsName("putDoubleByIndex")
    fun putDouble(index: Int, value: Double): ByteBuffer {
        checkAbsoluteRange(index, Long.SIZE_BYTES)
        writeLong(index, value.toBits(), Long.SIZE_BYTES)
        return this
    }

    /**
     * 压缩 ByteBuffer,将当前位置到极限位置之间的数据复制到缓冲区的开头,并将位置设置为剩余字节的数量,极限设置为容量。
     *
     * 此方法通常在读取缓冲区的数据后调用,以便下一次写入操作可以重用缓冲区的空间。
     *
     * @return [ByteBuffer] 返回当前 ByteBuffer 实例,以支持方法链式调用。
     */
    fun compact(): ByteBuffer {
        // 获取剩余字节数
        val remainingBytes = remaining()
        // 将当前位置到极限位置之间的数据复制到缓冲区的开头
        array.copyInto(array, 0, position, limit)
        // 将位置设置为剩余字节的数量
        position(remainingBytes)
        // 将极限设置为容量
        limit(capacity)
        return this
    }

    /**
     * 获取 ByteBuffer 中从当前位置到极限位置之间的剩余字节数据,并将位置设置为极限位置。
     *
     * 此方法通常在需要读取缓冲区中剩余的所有数据时调用。
     *
     * @return [ByteArray] 包含剩余字节数据的字节数组。
     */
    fun getRemainingBytes(): ByteArray {
        // 复制当前位置到极限位置之间的数据到新的字节数组
        val data = array.copyOfRange(position, limit)
        // 将位置设置为极限位置
        position = limit
        return data
    }

    private fun checkArrayRange(arraySize: Int, offset: Int, length: Int) {
        if (offset < 0 || length < 0 || offset > arraySize - length) {
            throw IndexOutOfBoundsException(
                "offset=$offset, length=$length, arraySize=$arraySize",
            )
        }
    }

    private fun ensureReadable(byteCount: Int) {
        if (remaining() < byteCount) throw BufferUnderflowException()
    }

    private fun ensureWritable(byteCount: Int) {
        if (remaining() < byteCount) throw BufferOverflowException()
    }

    private fun checkAbsoluteRange(index: Int, byteCount: Int) {
        if (index < 0 || index > limit - byteCount) {
            throw IndexOutOfBoundsException(
                "index=$index, byteCount=$byteCount, limit=$limit",
            )
        }
    }

    private fun readLong(index: Int, byteCount: Int): Long {
        var result = 0L
        for (offset in 0 until byteCount) {
            val sourceIndex = if (bigEndian) index + offset else index + byteCount - 1 - offset
            result = (result shl Byte.SIZE_BITS) or (array[sourceIndex].toLong() and 0xffL)
        }
        return result
    }

    private fun writeLong(index: Int, value: Long, byteCount: Int) {
        for (offset in 0 until byteCount) {
            val shift = if (bigEndian) {
                (byteCount - 1 - offset) * Byte.SIZE_BITS
            } else {
                offset * Byte.SIZE_BITS
            }
            array[index + offset] = (value ushr shift).toByte()
        }
    }
}

/**
 * 表示相对写入超过缓冲区限制。
 */
class BufferOverflowException : Exception("ByteBuffer overflow")

/**
 * 表示相对读取超过缓冲区限制。
 */
class BufferUnderflowException : Exception("ByteBuffer underflow")
