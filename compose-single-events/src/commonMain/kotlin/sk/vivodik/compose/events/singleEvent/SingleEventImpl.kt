package sk.vivodik.compose.events.singleEvent

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import sk.vivodik.compose.events.Event
import sk.vivodik.compose.events.Notified
import sk.vivodik.compose.events.Notify

private class SingleEventImpl<T> : SingleEvent<T> {

  private val _model = MutableStateFlow<Event<T>>(Notified)
  private val model: StateFlow<Event<T>> = _model

  private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())

  init {
    checkCollectors()
  }

  private fun checkCollectors() {
    scope.launch {
      _model
        .subscriptionCount
        .map { collectors -> collectors > 1 }
        .distinctUntilChanged()
        .collect { tooManyCollectors ->
          if (tooManyCollectors) {
            error("Too many single event consumers. Single even can have only one consumer! Are you sure you use Consume methode only once?")
          }
        }
    }
  }

  override fun notify(event: T) = _model.update { Notify(data = event) }
  override fun clear() = _model.update { Notified }
  private fun getAndClear(): Event<T> = _model.getAndUpdate { Notified }

  @NonRestartableComposable
  @Composable
  override fun Consume(action: (T) -> Unit) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val actualEventState by model.collectAsStateWithLifecycle()
    val currentAction by rememberUpdatedState(action)

    LaunchedEffect(key1 = actualEventState, key2 = lifecycleOwner.lifecycle) {
      // Trigger only on Notify type.
      val actualEvent = getAndClear()
      if (actualEvent is Notify) {
        currentAction(actualEvent.data)
      }
    }
  }
}

fun <T>SingleEvent() : SingleEvent<T> = SingleEventImpl()

@NonRestartableComposable
@Composable
fun <T>SingleEventConsumer(
  event: SingleEvent<T>,
  action: (T) -> Unit,
) = event.Consume(action = action)