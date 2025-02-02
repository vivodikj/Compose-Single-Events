package sk.vivodik.compose.events

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle

@NonRestartableComposable
@Composable
fun <T>EventConsumer(
  event: Event<T>,
  onClear: () -> Unit,
  action: (T) -> Unit,
) {
  val lifecycleOwner = LocalLifecycleOwner.current
  val currentAction by rememberUpdatedState(action)

  LaunchedEffect(key1 = event, key2 = lifecycleOwner.lifecycle) {
    lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
      // Trigger only on Notify type. We need firstly clean event and then trigger action.
      if (event is Notify) {
        onClear()
        currentAction(event.data)
      }
    }
  }
}