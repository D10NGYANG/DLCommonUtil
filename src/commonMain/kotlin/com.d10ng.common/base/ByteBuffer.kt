@file:JsExport
package com.d10ng.common.base

import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * 字节数据处理
 */
class ByteBuffer(
    private val data: ByteArray
) {
    companion object {
        /**
         * 通过字节数组创建ByteBuffer
         * @param data ByteArray
         * @return ByteBuffer
         */
        fun wrap(data: ByteArray): ByteBuffer {
            return ByteBuffer(data)
        }

        /**
         * 创建指定大小的ByteBuffer
         * @param capacity Int
         * @return ByteBuffer
         */
        fun allocate(capacity: Int): ByteBuffer {
            return ByteBuffer(ByteArray(capacity))
        }
    }

    // 指针位置
    private var position = 0
    // 限制位置
    private var limit = data.size
    // 容量
    private var capacity = data.size

    /**
     * 获取当前指针位置
     * @return Int
     */
    fun position(): Int {
        return position
    }

    /**
     * 设置当前指针位置
     * @param newPosition Int
     */
    @JsName("setPosition")
    fun position(newPosition: Int) {
        if (newPosition < 0 || newPosition > limit) {
            throw IllegalArgumentException("newPosition: $newPosition")
        }
        position = newPosition
    }

    /**
     * 获取限制位置
     * @return Int
     */
    fun limit(): Int {
        return limit
    }

    /**
     * 设置限制位置
     * @param newLimit Int
     */
    @JsName("setLimit")
    fun limit(newLimit: Int) {
        if (newLimit < 0 || newLimit > capacity) {
            throw IllegalArgumentException("newLimit: $newLimit")
        }
        limit = newLimit
    }

    /**
     * 获取容量
     * @return Int
     */
    fun capacity(): Int {
        return capacity
    }

    /**
     * 获取剩余可读字节数
     * @return Int
     */
    fun remaining(): Int {
        return limit - position
    }

    /**
     * 是否还有剩余可读字节
     * @return Boolean
     */
    fun hasRemaining(): Boolean {
        return position < limit
    }

    /**
     * 读取特定位置的字节
     * @param index Int
     * @return Byte
     */
    operator fun get(index: Int): Byte {
        if (index < 0 || index >= limit) {
            throw IndexOutOfBoundsException("index: $index, limit: $limit")
        }
        return data[index]
    }

    /**
     * 读取一个字节
     * @return Byte
     */
    fun getByte(): Byte {
        if (position >= limit) {
            throw IndexOutOfBoundsException("position: $position, limit: $limit")
        }
        return data[position++]
    }

    /**
     * 读取指定长度的字节
     * @param size Int
     * @return ByteArray
     */
    fun getBytes(size: Int): ByteArray {
        if (position + size > limit) {
            throw IndexOutOfBoundsException("position: $position, size: $size, limit: $limit")
        }
        val result = data.copyOfRange(position, position + size)
        position += size
        return result
    }

    /**
     * 读取剩余的字节
     * @return ByteArray
     */
    fun getRemainingBytes(): ByteArray {
        return getBytes(remaining())
    }

    /**
     * 设置特定位置的字节
     * @param index Int
     * @param value Byte
     */
    operator fun set(index: Int, value: Byte) {
        if (index < 0 || index >= limit) {
            throw IndexOutOfBoundsException("index: $index, limit: $limit")
        }
        data[index] = value
    }

    /**
     * 写入一个字节
     * @param value Byte
     */
    fun setByte(value: Byte) {
        if (position >= limit) {
            throw IndexOutOfBoundsException("position: $position, limit: $limit")
        }
        data[position++] = value
    }

    /**
     * 写入指定长度的字节
     * @param value ByteArray
     */
    fun setBytes(value: ByteArray) {
        if (position + value.size > limit) {
            throw IndexOutOfBoundsException("position: $position, size: ${value.size}, limit: $limit")
        }
        value.copyInto(data, position)
        position += value.size
    }

    /**
     * 重置指针位置为0
     */
    fun reset() {
        position = 0
    }
}