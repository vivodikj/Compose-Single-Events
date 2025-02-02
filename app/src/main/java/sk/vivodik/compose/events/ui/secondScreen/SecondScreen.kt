package sk.vivodik.compose.events.ui.secondScreen

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import sk.vivodik.compose.events.EventConsumer
import sk.vivodik.compose.events.singleEvent.SingleEventConsumer
import sk.vivodik.compose.events.ui.core.LoadingDialog

class SecondScreen : Screen {

  @Composable
  override fun Content() {

    val viewModel: SecondScreenViewModel = viewModel()
    val model by viewModel.model.collectAsStateWithLifecycle()
    val navigator = LocalNavigator.current
    val context = LocalContext.current


    EventConsumer(
      event = model.backEvent,
      onClear = viewModel::clearEvent
    ) {
      Toast.makeText(context, "Data loaded successfully.", Toast.LENGTH_SHORT).show()
    }

    SingleEventConsumer(viewModel.singleEvent) { event ->
      when(event) {
        SecondScreenEvent.NavigateBack -> navigator?.pop()
        SecondScreenEvent.ShowSuccess -> {
          Toast.makeText(context, "Data saved successfully. Navigate back in second.", Toast.LENGTH_SHORT).show()
        }
      }
    }

    model.autoClearEvent.Consume { count ->
      Toast.makeText(context, "Count while clicked: $count", Toast.LENGTH_SHORT).show()
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
      Column(
        modifier = Modifier
          .padding(innerPadding)
          .fillMaxSize()
          .padding(16.dp),
        verticalArrangement = Arrangement.Center,
      ) {
        Text(
          text = "Second Screen",
        )

        Text(
          text = "Count: ${model.count}",
        )

        Button(
          modifier = Modifier
            .fillMaxWidth(),
          onClick = viewModel::simulateSuccess,
        ) {
          Text(text = "Simulate success data load toast")
        }

        Button(
          modifier = Modifier
            .fillMaxWidth(),
          onClick = viewModel::simulateSuccessAndNavigate,
        ) {
          Text("Simulate success data save and navigate")
        }

        Button(
          modifier = Modifier
            .fillMaxWidth(),
          onClick = viewModel::getCountToast,
        ) {
          Text("Show toast with count")
        }
      }
    }

    if (model.isLoading) {
      LoadingDialog()
    }

  }

}