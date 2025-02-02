package sk.vivodik.compose.events.autoClearEvent

import androidx.compose.runtime.Composable

interface AutoClearEvent<T> {

  @Composable
  fun Consume(action: (T) -> Unit)

}