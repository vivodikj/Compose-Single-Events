package sk.vivodik.compose.events.ui.main

import sk.vivodik.compose.events.Event
import sk.vivodik.compose.events.Notified
import sk.vivodik.compose.events.autoClearEvent.AutoClearEvent
import sk.vivodik.compose.events.autoClearEvent.notified

data class MainScreenModel(
  val isLoading: Boolean = false,
  val infoEvent: Event<Boolean> = Notified,
  val autoClearEvent: AutoClearEvent<Unit> = notified(),
)