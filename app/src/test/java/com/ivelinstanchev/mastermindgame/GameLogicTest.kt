package com.ivelinstanchev.mastermindgame

import org.junit.Test

import org.junit.Assert.*

class GameLogicTest {

    @Test
    fun `generateSecret should return 4 uppercase letters`() {
        val secret = GameLogic.generateSecret()

        assertEquals(4, secret.length)
        assertTrue(secret.all { it in 'A'..'Z' })
    }

    @Test
    fun `evaluateGuess all correct should be all GREEN`() {
        val secret = "ABCD"
        val guess = "ABCD"
        val results = GameLogic.evaluateGuess(secret, guess)

        assertEquals(4, results.size)
        assertTrue(results.all { it.color == BoxColor.GREEN })
    }

    @Test
    fun `evaluateGuess letter exists but wrong spot should be ORANGE`() {
        val secret = "ABCD"
        val guess = "DABC" // rotated
        val results = GameLogic.evaluateGuess(secret, guess)

        assertEquals(4, results.size)
        assertTrue(results.all { it.color == BoxColor.ORANGE })
    }

    @Test
    fun `evaluateGuess letter not in secret should be RED`() {
        val secret = "ABCD"
        val guess = "WXYZ"
        val results = GameLogic.evaluateGuess(secret, guess)

        assertEquals(4, results.size)
        assertTrue(results.all { it.color == BoxColor.RED })
    }

    @Test
    fun `evaluateGuess mixed positions should have mixed colors`() {
        val secret = "ABCD"
        val guess = "AEDF"
        val result = GameLogic.evaluateGuess(secret, guess)

        assertEquals(BoxColor.GREEN, result[0].color)  // A correct
        assertEquals(BoxColor.RED, result[1].color)    // E not in secret
        assertEquals(BoxColor.ORANGE, result[2].color) // D exists but wrong spot
        assertEquals(BoxColor.RED, result[3].color)    // F not in secret
    }

    @Test(expected = IllegalArgumentException::class)
    fun `evaluateGuess throws if input not length 4`() {
        GameLogic.evaluateGuess("ABCD", "ABC")
    }
}