package sk.vivodik.compose.events

import androidx.compose.runtime.Immutable

@Immutable
interface Event<out T>

@Immutable
data class Notify<T>(val data: T) : Event<T>

@Immutable
data object Notified : Event<Nothing>