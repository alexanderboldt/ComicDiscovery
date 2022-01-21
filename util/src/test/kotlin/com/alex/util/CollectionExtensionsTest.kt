package com.alex.util

import com.google.common.truth.Truth.*
import org.junit.Test

class CollectionExtensionsTest {

    @Test
    fun `it leaves an empty list empty`() {
        val collection = mutableListOf<Int>()
        collection.clearAndAdd(emptyList())

        assertThat(collection).isEmpty()
    }

    @Test
    fun `it clears an empty list and adds numbers`() {
        val collection = mutableListOf<Int>()
        collection.clearAndAdd(listOf(9, 8, 7))

        assertThat(collection).isNotEmpty()
        assertThat(collection).isEqualTo(listOf(9, 8, 7))
    }

    @Test
    fun `it clears the list and leaves it empty`() {
        val collection = mutableListOf(1, 2, 3, 4, 5)
        collection.clearAndAdd(emptyList())

        assertThat(collection).isEmpty()
    }

    @Test
    fun `it clears the list and adds new numbers`() {
        val collection = mutableListOf(1, 2, 3, 4, 5)
        collection.clearAndAdd(listOf(9, 8, 7))

        assertThat(collection).isNotEmpty()
        assertThat(collection).isEqualTo(listOf(9, 8, 7))
    }
}