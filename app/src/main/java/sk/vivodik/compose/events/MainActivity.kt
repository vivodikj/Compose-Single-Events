package sk.vivodik.compose.events

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import sk.vivodik.compose.events.ui.main.MainScreen
import sk.vivodik.compose.events.ui.theme.EventsSampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EventsSampleTheme {
                Navigator(MainScreen()) { navigator ->
                    SlideTransition(navigator = navigator)
                }
            }
        }
    }
}