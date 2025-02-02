package sk.vivodik.compose.events.singleEvent

import androidx.compose.runtime.Composable

interface SingleEvent<T> {

  fun notify(event: T)
  fun clear()

  @Composable
  fun Consume(action: (T) -> Unit)

}