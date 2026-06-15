package ru.grinin.winlineapp.presentation.models

data class GiftCard(
    val id: Int,
    val isFlipped: Boolean = false,
    val title: String = "BYN"
)