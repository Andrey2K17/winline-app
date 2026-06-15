package ru.grinin.winlineapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.grinin.winlineapp.presentation.models.GiftCard
import ru.grinin.winlineapp.utils.TimerManager

class GiftsViewModel(
    private val timerManager: TimerManager,
) : ViewModel() {

    private val _cards = MutableStateFlow(createInitialCards())
    val cards: StateFlow<ImmutableList<GiftCard>> = _cards.asStateFlow()

    private val _timerSeconds = MutableStateFlow<Int?>(null)
    val timerSeconds = _timerSeconds.asStateFlow()

    private fun createInitialCards(): PersistentList<GiftCard> {
        return (0..14).map { index -> GiftCard(id = index) }.toPersistentList()
    }

    init {
        viewModelScope.launch {
            timerManager.remainingSeconds.collect { seconds ->
                _timerSeconds.value = seconds

                if (seconds == 0) {
                    flipAllCards()
                }
            }
        }
    }

    fun toggleFlip(cardId: Int) {
        _cards.update { currentCards ->
            currentCards.map { card ->
                if (card.id == cardId) card.copy(isFlipped = !card.isFlipped) else card
            }.toPersistentList()
        }
    }

    private fun flipAllCards() {
        _cards.update { currentCards ->
            currentCards.map { it.copy(isFlipped = false) }.toPersistentList()
        }
    }
}