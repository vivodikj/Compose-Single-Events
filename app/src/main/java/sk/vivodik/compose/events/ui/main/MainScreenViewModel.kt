package sk.vivodik.compose.events.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import sk.vivodik.compose.events.Notified
import sk.vivodik.compose.events.Notify
import sk.vivodik.compose.events.autoClearEvent.notify
import sk.vivodik.compose.events.singleEvent.SingleEvent
import kotlin.random.Random

class MainScreenViewModel : ViewModel() {

  private val _model = MutableStateFlow(MainScreenModel())
  val model = _model.asStateFlow()

  val singleEvent = SingleEvent<String>()

  fun randomSuccess() {
    viewModelScope.launch {
      _model.update { model ->
        model.copy(
          isLoading = true,
        )
      }

      delay(1000)

      _model.update { model ->
        model.copy(
          isLoading = false,
          infoEvent = Notify(Random.nextBoolean()),
        )
      }
    }
  }

  fun randomNumber() {
    viewModelScope.launch {
      _model.update { model ->
        model.copy(
          isLoading = true,
        )
      }

      delay(500)

      singleEvent.notify("Generated - ${Random.nextInt()}")
      _model.update { model ->
        model.copy(
          isLoading = false,
        )
      }
    }
  }

  fun navigateAfterLoading() {
    viewModelScope.launch {
      _model.update { model ->
        model.copy(
          isLoading = true,
        )
      }

      delay(1000)

      _model.update { model ->
        model.copy(
          isLoading = false,
          autoClearEvent = notify(Unit),
        )
      }
    }
  }

  fun clearEvent() {
    _model.update { model ->
      model.copy(
        infoEvent = Notified,
      )
    }
  }

}