package ru.grinin.winlineapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ru.grinin.winlineapp.presentation.screens.gifts.GiftCard

class GiftsViewModel : ViewModel() {

    private val _cards = MutableStateFlow(createInitialCards())
    val cards: StateFlow<List<GiftCard>> = _cards.asStateFlow()

    private fun createInitialCards(): List<GiftCard> {
        return (0..14).map { index -> GiftCard(id = index) }
    }

    fun toggleFlip(cardId: Int) {
        _cards.update { currentCards ->
            currentCards.map { card ->
                if (card.id == cardId) card.copy(isFlipped = !card.isFlipped) else card
            }
        }
    }
}