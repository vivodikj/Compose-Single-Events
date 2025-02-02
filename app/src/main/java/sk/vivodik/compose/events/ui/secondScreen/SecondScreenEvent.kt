package sk.vivodik.compose.events.ui.secondScreen

sealed interface SecondScreenEvent {
  data object ShowSuccess : SecondScreenEvent
  data object NavigateBack : SecondScreenEvent
}