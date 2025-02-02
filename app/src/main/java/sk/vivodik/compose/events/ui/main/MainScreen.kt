package sk.vivodik.compose.events.ui.main

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import sk.vivodik.compose.events.EventConsumer
import sk.vivodik.compose.events.autoClearEvent.SingleEventConsumer
import sk.vivodik.compose.events.singleEvent.SingleEvent
import sk.vivodik.compose.events.ui.core.LoadingDialog
import sk.vivodik.compose.events.ui.secondScreen.SecondScreen

class MainScreen : Screen {

  @Composable
  override fun Content() {

    val viewModel: MainScreenViewModel = viewModel()
    val model by viewModel.model.collectAsStateWithLifecycle()
    val navigator = LocalNavigator.current
    val context = LocalContext.current

    // TODO: maybe usable?
    val singleEventNavigator = remember { SingleEvent<Unit>() }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

      viewModel.singleEvent.Consume { eventText ->
        Toast.makeText(context, "Event text: $eventText", Toast.LENGTH_SHORT).show()
      }

//      SingleEventConsumer(viewModel.singleEvent) { eventText ->
//        Toast.makeText(context, "Event text: $eventText", Toast.LENGTH_SHORT).show()
//      }

      SingleEventConsumer(model.autoClearEvent) {
        navigator?.push(SecondScreen())
      }

      EventConsumer(
        event = model.infoEvent,
        onClear = viewModel::clearEvent
      ) { isSuccess ->
        if (isSuccess) {
          navigator?.push(SecondScreen())
        } else {
          Toast.makeText(context, "Something wrong, try it again.", Toast.LENGTH_SHORT).show()
        }
      }

      singleEventNavigator.Consume {
        navigator?.push(SecondScreen())
      }

      Column(
        modifier = Modifier
          .padding(innerPadding)
          .fillMaxSize()
          .padding(16.dp),
        verticalArrangement = Arrangement.Center,
      ) {
        Button(
          modifier = Modifier
            .fillMaxWidth(),
          onClick = viewModel::randomSuccess,
        ) {
          Text(text = "Go to next screen on random success")
        }

        Button(
          modifier = Modifier
            .fillMaxWidth(),
          onClick = viewModel::randomNumber,
        ) {
          Text("Show toast with random number.")
        }

        Button(
          modifier = Modifier
            .fillMaxWidth(),
          onClick = viewModel::navigateAfterLoading,
        ) {
          Text("Navigate after load")
        }
      }

    }

    if (model.isLoading) {
      LoadingDialog()
    }

  }

}