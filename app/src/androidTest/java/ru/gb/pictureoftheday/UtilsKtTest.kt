package ru.gb.pictureoftheday

import org.junit.Assert.*
import org.junit.Test

class UtilsKtTest {

    @Test
    fun isValidImageUrl_CorrectImageSimpleJpg_ReturnsTrue() {
        assertTrue(isValidImageUrl("http://sdfsdfds.com/test/test.jpg"))
        assertTrue(isValidImageUrl("https://sdfsdfds.com/test/test.jpg"))
    }

    @Test
    fun isValidImageUrl_CorrectImageSimplePng_ReturnsTrue() {
        assertTrue(isValidImageUrl("http://sdfsdfds.com/test/test.png"))
        assertTrue(isValidImageUrl("https://sdfsdfds.com/test/test.png"))
    }

    @Test
    fun isValidImageUrl_CorrectImageSimpleDomain_ReturnsTrue() {
        assertTrue(isValidImageUrl("http://sdfsdfds/test/test.png"))
    }

    @Test
    fun isValidImageUrl_NotCorrectImageNotExtention_ReturnsFalse() {
        assertFalse(isValidImageUrl("http://sdfsdfds.com/test/test"))
    }

    @Test
    fun isValidImageUrl_NotCorrectImageNotProtocol_ReturnsFalse() {
        assertFalse(isValidImageUrl("sdfsdfds.com/test/test.jpg"))
    }

    @Test
    fun isValidImageUrl_NotCorrectImageErrorInUrl_ReturnsFalse() {
        assertFalse(isValidImageUrl("http:/sdfsdfds.com/test/test.jpg"))
        assertFalse(isValidImageUrl("http//sdfsdfds.com/test/test.jpg"))
    }

    @Test
    fun isValidImageUrl_NotCorrectEmptyUrl_ReturnsFalse() {
        assertFalse(isValidImageUrl(""))
    }

    @Test
    fun isValidImageUrl_NotCorrectNullUrl_ReturnsFalse() {
        assertFalse(isValidImageUrl(null))
    }

    @Test
    fun findFistNumber_isCorrect() {
        val actual = findFistNumber("Test12QWER")
        val expected = "12"

        assertEquals(expected, actual)
    }

    @Test
    fun findFistNumber_isNotCorrect() {
        val actual = findFistNumber("Test12QWER")
        val expected = "123"

        assertNotEquals(expected, actual)
    }

    @Test
    fun findFistNumber_isCorrectNotNull() {
        val actual = findFistNumber("Test12QWER")
        val expected = "12"

        assertNotNull(expected, actual)
    }

    @Test
    fun findAllNumber_isCorrect() {
        val actual = findAllNumber("Test12QWER345")
        val expected: Array<String> = listOf("12", "345").toTypedArray()

        assertArrayEquals(expected, actual)
    }
}