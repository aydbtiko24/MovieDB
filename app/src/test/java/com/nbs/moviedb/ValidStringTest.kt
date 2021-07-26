package com.nbs.moviedb

import org.junit.Assert
import org.junit.Test

/**
 * Created by aydbtiko on 7/26/2021.
 *
 */
class ValidStringTest {

    @Test
    fun `input 1 test`() {
        val isValid = isValidString("aabbccddeefghi")
        Assert.assertFalse(isValid)
    }

    @Test
    fun `input 2 test`() {
        val isValid = isValidString("abcdefghhgfedecba")
        Assert.assertTrue(isValid)
    }

    @Test
    fun `input 3 test`() {
        val isValid = isValidString("abcbdcd")
        Assert.assertTrue(isValid)
    }

    @Test
    fun `input 4 test`() {
        val isValid = isValidString("abcjdekfghhgfedkecbaijk")
        Assert.assertFalse(isValid)
    }

    @Test
    fun `input 5 test`() {
        val isValid = isValidString("abcjdekfghhgfedkcbaij")
        Assert.assertTrue(isValid)
    }

    private fun isValidString(S: String): Boolean {
        val hash = HashMap<Char, Int>()
        var deletedCount = 0
        val validDeletedCount = 1
        val validCount = 2
        // store current letter count
        for (i: Int in S.indices) {
            val currentChar = S[i]
            val storedCount: Int = hash.getOrDefault(currentChar, 0)
            hash[currentChar] = storedCount + 1
        }
        // calculate deleted letter of entries
        for (entry in hash.entries) {
            val count = entry.value
            if (count == validCount) continue
            // increment deleted count
            if (count - validDeletedCount == validCount || count - validDeletedCount == 0) {
                deletedCount++
            }
        }
        return deletedCount <= validDeletedCount
    }
}
