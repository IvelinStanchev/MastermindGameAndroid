package com.ivelinstanchev.mastermindgame

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class GameState(
    val secret: String = "",
    val guess: List<Char> = List(GameLogic.GAME_LENGTH) { ' ' },
    val results: List<LetterResult> = emptyList()
)

class MastermindViewModel : ViewModel() {

    private val _state = MutableStateFlow(GameState(secret = GameLogic.generateSecret()))
    val state: StateFlow<GameState> = _state.asStateFlow()

    fun onLetterChange(index: Int, char: Char) {
        val newGuess = _state.value.guess.toMutableList()
        newGuess[index] = char.uppercaseChar()
        _state.value = _state.value.copy(guess = newGuess)
    }

    fun checkGuess() {
        val guessString = _state.value.guess.joinToString("").replace(" ", "")
        if (guessString.length == GameLogic.GAME_LENGTH) {
            val results = GameLogic.evaluateGuess(_state.value.secret, guessString)
            _state.value = _state.value.copy(results = results)
        }
    }
}