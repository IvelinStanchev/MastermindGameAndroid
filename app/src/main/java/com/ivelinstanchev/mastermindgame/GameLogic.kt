package com.ivelinstanchev.mastermindgame

enum class BoxColor {
    GREEN, ORANGE, RED, DEFAULT
}

data class LetterResult(
    val letter: Char,
    val color: BoxColor
)

object GameLogic {

    const val GAME_LENGTH = 4

    fun evaluateGuess(secret: String, guess: String): List<LetterResult> {
        require(secret.length == GAME_LENGTH && guess.length == GAME_LENGTH)

        val secretChars = secret.toCharArray()
        val guessChars = guess.toCharArray()
        val results = mutableListOf<LetterResult>()

        for (i in 0 until GAME_LENGTH) {
            when {
                guessChars[i] == secretChars[i] -> {
                    results.add(LetterResult(guessChars[i], BoxColor.GREEN))
                }

                secretChars.contains(guessChars[i]) -> {
                    results.add(LetterResult(guessChars[i], BoxColor.ORANGE))
                }

                else -> {
                    results.add(LetterResult(guessChars[i], BoxColor.RED))
                }
            }
        }

        return results
    }

    fun generateSecret(): String {
        return (1..GAME_LENGTH)
            .map { ('A'..'Z').random() }
            .joinToString("")
    }
}