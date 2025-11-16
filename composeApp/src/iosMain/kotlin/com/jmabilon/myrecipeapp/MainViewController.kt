package com.jmabilon.myrecipeapp

import androidx.compose.ui.window.ComposeUIViewController
import com.jmabilon.myrecipeapp.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = { initKoin() }
) { App() }
