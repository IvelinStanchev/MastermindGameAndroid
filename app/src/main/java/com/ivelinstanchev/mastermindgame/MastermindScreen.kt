package com.ivelinstanchev.mastermindgame

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MastermindScreen(viewModel: MastermindViewModel = viewModel()) {
    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            state.guess.forEachIndexed { index, letter ->
                val color = state.results.getOrNull(index)?.color ?: BoxColor.DEFAULT
                val bgColor = when (color) {
                    BoxColor.GREEN -> Color.Green
                    BoxColor.ORANGE -> Color(0xFFFFA500)
                    BoxColor.RED -> Color.Red
                    BoxColor.DEFAULT -> Color.LightGray
                }

                OutlinedTextField(
                    value = if (letter == ' ') "" else letter.toString(),
                    onValueChange = { text ->
                        if (text.isNotEmpty()) viewModel.onLetterChange(index, text.last())
                    },
                    modifier = Modifier.weight(1f)
                        .background(bgColor),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Characters
                    )
                )
            }
        }

        Button(
            onClick = { viewModel.checkGuess() },
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        ) {
            Text(stringResource(R.string.check))
        }

        if (state.results.isNotEmpty()) {
            // Just for testing
            Text(
                text = stringResource(R.string.secret, state.secret),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}