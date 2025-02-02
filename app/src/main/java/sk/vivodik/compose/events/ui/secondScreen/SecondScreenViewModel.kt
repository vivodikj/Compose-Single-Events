package sk.vivodik.compose.events.ui.secondScreen

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

class SecondScreenViewModel : ViewModel() {

  private val _model = MutableStateFlow(SecondScreenModel())
  val model = _model.asStateFlow()

  val singleEvent = SingleEvent<SecondScreenEvent>()

  init {
    runCounter()
  }

  private fun runCounter() {
    viewModelScope.launch {
      while (true) {
        delay(500)
        _model.update { model ->
          model.copy(
            count = model.count + 1,
          )
        }
      }
    }
  }

  fun simulateSuccess() {
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
          backEvent = Notify(Unit),
        )
      }
    }
  }

  fun simulateSuccessAndNavigate() {
    viewModelScope.launch {
      _model.update { model ->
        model.copy(
          isLoading = true,
        )
      }

      delay(2000)

      singleEvent.notify(SecondScreenEvent.ShowSuccess)

      delay(1000)

      singleEvent.notify(SecondScreenEvent.NavigateBack)
    }
  }

  fun getCountToast() {
    _model.update { model ->
      model.copy(
        autoClearEvent = notify(model.count),
      )
    }
  }

  fun clearEvent() {
    _model.update { model ->
      model.copy(
        backEvent = Notified,
      )
    }
  }

}