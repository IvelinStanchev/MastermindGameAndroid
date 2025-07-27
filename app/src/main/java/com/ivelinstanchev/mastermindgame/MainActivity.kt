package com.ivelinstanchev.mastermindgame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import com.ivelinstanchev.mastermindgame.ui.theme.MastermindGameTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MastermindGameTheme {
                Surface {
                    MastermindScreen()
                }
            }
        }
    }
}