package com.ivelinstanchev.mastermindgame

enum class BoxColor {
    GREEN, ORANGE, RED, DEFAULT
}

data class LetterResult(
    val letter: Char,
    val color: BoxColor
)

object GameLogic {

    fun evaluateGuess(secret: String, guess: String): List<LetterResult> {
        require(secret.length == 4 && guess.length == 4)

        val secretChars = secret.toCharArray()
        val guessChars = guess.toCharArray()
        val results = mutableListOf<LetterResult>()

        for (i in 0..3) {
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
        return (1..4)
            .map { ('A'..'Z').random() }
            .joinToString("")
    }
}