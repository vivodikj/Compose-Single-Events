package sk.vivodik.compose.events.ui.secondScreen

import sk.vivodik.compose.events.Event
import sk.vivodik.compose.events.Notified
import sk.vivodik.compose.events.autoClearEvent.AutoClearEvent
import sk.vivodik.compose.events.autoClearEvent.notified

data class SecondScreenModel(
  val isLoading: Boolean = false,
  val count: Int = 0,
  val backEvent: Event<Unit> = Notified,
  val autoClearEvent: AutoClearEvent<Int> = notified(),
)