package sk.vivodik.compose.events.autoClearEvent

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.mutableStateOf
import sk.vivodik.compose.events.Event
import sk.vivodik.compose.events.EventConsumer
import sk.vivodik.compose.events.Notified
import sk.vivodik.compose.events.Notify

private class AutoClearEventImpl<T>(defaultValue: Event<T> = Notified) : AutoClearEvent<T> {
  // TODO: Try use StateFlow and compare performance.
  private val eventState: MutableState<Event<T>> = mutableStateOf(defaultValue)

  @NonRestartableComposable
  @Composable
  override fun Consume(action: (T) -> Unit) {
    EventConsumer(
      event = eventState.value,
      onClear = { eventState.value = Notified },
      action = action,
    )
  }

}

fun <T>notified(): AutoClearEvent<T> = AutoClearEventImpl()
fun <T>notify(value: T): AutoClearEvent<T> = AutoClearEventImpl(defaultValue = Notify(data = value))

@NonRestartableComposable
@Composable
fun <T>SingleEventConsumer(
  event: AutoClearEvent<T>,
  action: (T) -> Unit,
) = event.Consume(action = action)