package com.jmabilon.myrecipeapp.core.prensentation.extension

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow

/**
 * Use for collecting Flow just ones in composable.
 **/
@Suppress("ComposableNaming")
@Composable
fun <T> Flow<T>.collectAsEvents(
    block: (T) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(this, lifecycleOwner.lifecycle) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            this@collectAsEvents.collect(block)
        }
    }
}
