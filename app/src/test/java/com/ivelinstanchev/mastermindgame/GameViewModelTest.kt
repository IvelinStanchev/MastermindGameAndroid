package com.ivelinstanchev.mastermindgame

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest

import org.junit.Test
import org.junit.Assert.*

@OptIn(ExperimentalCoroutinesApi::class)
class GameViewModelTest {

    @Test
    fun `initial state should have random secret and empty guess`() = runTest {
        val vm = MastermindViewModel()
        val state = vm.state.first()

        assertEquals(GameLogic.GAME_LENGTH, state.secret.length)
        assertEquals(listOf(' ', ' ', ' ', ' '), state.guess)
        assertEquals(emptyList<LetterResult>(), state.results)
    }

    @Test
    fun `onLetterChange should update guess at correct index`() = runTest {
        val vm = MastermindViewModel()

        val uppercased = mutableListOf<Char>()
        for (i in 0 until GameLogic.GAME_LENGTH) {
            vm.onLetterChange(i, 'a' + i)
            uppercased.add('A' + i)
        }

        val updated = vm.state.first()
        assertEquals(uppercased, updated.guess)
    }

    @Test
    fun `checkGuess should produce results for valid guess`() = runTest {
        val vm = MastermindViewModel()

        val secret = GameLogic.generateSecret()
        val initial = vm.state.first()
        secret.forEachIndexed { index, char ->
            vm.onLetterChange(index, char)
        }

        val manualState = initial.copy(secret = secret, guess = secret.toCharArray().toList())
        val field = MastermindViewModel::class.java.getDeclaredField("_state")
        field.isAccessible = true
        @Suppress("UNCHECKED_CAST")
        val mutableState = field.get(vm) as kotlinx.coroutines.flow.MutableStateFlow<GameState>
        mutableState.value = manualState

        vm.checkGuess()
        val resultState = vm.state.first()

        assertEquals(GameLogic.GAME_LENGTH, resultState.results.size)
        for (i in 0 until GameLogic.GAME_LENGTH) {
            assertEquals(BoxColor.GREEN, resultState.results[i].color)
        }
    }

    @Test
    fun `checkGuess should NOT change state if guess is incomplete`() = runTest {
        val vm = MastermindViewModel()
        vm.onLetterChange(0, 'A')
        vm.onLetterChange(1, 'B')

        val before = vm.state.first()
        vm.checkGuess()
        val after = vm.state.first()

        assertEquals(before.results, after.results)
    }
}