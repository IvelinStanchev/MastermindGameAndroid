package com.ivelinstanchev.mastermindgame

import org.junit.Test

import org.junit.Assert.*

class GameLogicTest {

    @Test
    fun `generateSecret should return proper count of uppercase letters`() {
        val secret = GameLogic.generateSecret()

        assertEquals(GameLogic.GAME_LENGTH, secret.length)
        assertTrue(secret.all { it in 'A'..'Z' })
    }

    @Test
    fun `evaluateGuess all correct should be all GREEN`() {
        val secret = GameLogic.generateSecret()
        val guess = secret
        val results = GameLogic.evaluateGuess(secret, guess)

        assertEquals(GameLogic.GAME_LENGTH, results.size)
        assertTrue(results.all { it.color == BoxColor.GREEN })
    }

    @Test
    fun `evaluateGuess letter exists but wrong spot should be ORANGE`() {
        val secret = GameLogic.generateSecret()
        val guess = secret.reversed()
        val results = GameLogic.evaluateGuess(secret, guess)

        assertEquals(GameLogic.GAME_LENGTH, results.size)
        assertTrue(results.all { it.color == BoxColor.ORANGE })
    }

    @Test
    fun `evaluateGuess letter not in secret should be RED`() {
        val secret = List(GameLogic.GAME_LENGTH) { 'A' }.joinToString(separator = "")
        val guess = List(GameLogic.GAME_LENGTH) { 'B' }.joinToString(separator = "")
        val results = GameLogic.evaluateGuess(secret, guess)

        assertEquals(GameLogic.GAME_LENGTH, results.size)
        assertTrue(results.all { it.color == BoxColor.RED })
    }

    @Test(expected = IllegalArgumentException::class)
    fun `evaluateGuess throws if input is not the expected length`() {
        val secret = List(GameLogic.GAME_LENGTH) { 'A' }.joinToString(separator = "")
        val guess = List(GameLogic.GAME_LENGTH - 1) { 'B' }.joinToString(separator = "")
        GameLogic.evaluateGuess(secret, guess)
    }
}