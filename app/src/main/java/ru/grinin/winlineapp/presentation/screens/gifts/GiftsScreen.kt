package ru.grinin.winlineapp.presentation.screens.gifts

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import ru.grinin.winlineapp.R
import ru.grinin.winlineapp.presentation.viewmodel.GiftsViewModel
import ru.grinin.winlineapp.ui.theme.WinlineAppTheme


data class GiftCard(
    val id: Int,
    val isFlipped: Boolean = false
)

@Composable
fun GiftsScreen(
    viewModel: GiftsViewModel = koinViewModel<GiftsViewModel>(),
) {
    val cards by viewModel.cards.collectAsStateWithLifecycle()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    0.0f to Color(0xFF0F172A),
                    0.5f to Color(0xFF1E3A5F),
                    1.0f to Color(0xFF2D6A8F)
                )
            )
    ) {
        BoxWithConstraints(
            modifier = Modifier.fillMaxSize()
        ) {
            val horizontalPadding = 16.dp
            val spacing = 12.dp
            val columns = 3

            val availableWidth = maxWidth - (horizontalPadding * 2)
            val cardSize = (availableWidth - spacing * (columns - 1)) / columns

            LazyVerticalGrid(
                columns = GridCells.Fixed(columns),
                contentPadding = PaddingValues(horizontal = horizontalPadding, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(spacing),
                horizontalArrangement = Arrangement.spacedBy(spacing),
                modifier = Modifier.fillMaxSize()
            ) {
                items(cards, key = { it.id }) { card ->
                    val column = card.id % 3
                    val snowRes = when (column) {
                        0 -> R.drawable.snow_left
                        1 -> R.drawable.snow_center
                        else -> R.drawable.snow_right
                    }

                    val snowOffsetY = if (column == 1) -cardSize * 0.09f else -cardSize * 0.06f

                    GiftsItem(
                        modifier = Modifier.size(cardSize),
                        isFlipped = card.isFlipped,
                        onClick = { viewModel.toggleFlip(card.id) },
                        frontImageRes = R.drawable.gift_box,
                        backImageRes = R.drawable.freebet,
                        snowRes = snowRes,
                        snowOffsetY = snowOffsetY,
                    )
                }
            }
        }
    }
}

@Composable
fun GiftsItem(
    modifier: Modifier = Modifier,
    isFlipped: Boolean,
    onClick: () -> Unit,
    frontImageRes: Int,
    backImageRes: Int,
    snowRes: Int,
    snowOffsetY: Dp,
) {
    val rotation by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = tween(durationMillis = 2000, easing = FastOutSlowInEasing),
        label = "Flip"
    )

    Box(
        modifier = modifier
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 6f
            }
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(12.dp))
                .background(
                    Brush.verticalGradient(
                        0.0f to Color.White.copy(alpha = 0.25f),
                        1.0f to Color(0xFFB3D9FF).copy(alpha = 0.3f)
                    )
                )
        ) {
            if (rotation <= 90f) {
                Image(
                    painter = painterResource(id = frontImageRes),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize(0.85f)
                        .align(Alignment.Center)
                )
            }
            if (rotation > 90f) {
                Image(
                    painter = painterResource(id = backImageRes),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize(0.85f)
                        .align(Alignment.Center)
                )
            }
        }

        Image(
            painter = painterResource(id = snowRes),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = snowOffsetY)
        )
    }
}

@Preview
@Composable
fun GiftsScreenPreview() {
    WinlineAppTheme {
        GiftsScreen()
    }
}

@Preview
@Composable
fun GiftItemPreview() {
    WinlineAppTheme {
        GiftsItem(
            isFlipped = false,
            onClick = {},
            frontImageRes = R.drawable.gift_box,
            backImageRes = R.drawable.freebet,
            snowRes = R.drawable.snow_left,
            snowOffsetY = (-25).dp
        )
    }
}