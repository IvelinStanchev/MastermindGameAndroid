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

        assertEquals(4, state.secret.length)
        assertEquals(listOf(' ', ' ', ' ', ' '), state.guess)
        assertEquals(emptyList<LetterResult>(), state.results)
    }

    @Test
    fun `onLetterChange should update guess at correct index`() = runTest {
        val vm = MastermindViewModel()

        vm.onLetterChange(0, 'a')
        vm.onLetterChange(1, 'b')
        vm.onLetterChange(2, 'c')
        vm.onLetterChange(3, 'd')

        val updated = vm.state.first()
        assertEquals(listOf('A', 'B', 'C', 'D'), updated.guess)
    }

    @Test
    fun `checkGuess should produce results for valid guess`() = runTest {
        val vm = MastermindViewModel()

        val secret = "ABCD"
        val initial = vm.state.first()
        vm.onLetterChange(0, 'A')
        vm.onLetterChange(1, 'B')
        vm.onLetterChange(2, 'C')
        vm.onLetterChange(3, 'D')

        val manualState = initial.copy(secret = secret, guess = listOf('A', 'B', 'C', 'D'))
        val field = MastermindViewModel::class.java.getDeclaredField("_state")
        field.isAccessible = true
        @Suppress("UNCHECKED_CAST")
        val mutableState = field.get(vm) as kotlinx.coroutines.flow.MutableStateFlow<GameState>
        mutableState.value = manualState

        vm.checkGuess()
        val resultState = vm.state.first()

        assertEquals(4, resultState.results.size)
        assertEquals(BoxColor.GREEN, resultState.results[0].color)
        assertEquals(BoxColor.GREEN, resultState.results[1].color)
        assertEquals(BoxColor.GREEN, resultState.results[2].color)
        assertEquals(BoxColor.GREEN, resultState.results[3].color)
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