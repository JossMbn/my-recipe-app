package com.jmabilon.myrecipeapp

import android.app.Application
import com.jmabilon.myrecipeapp.di.initKoin
import org.koin.android.ext.koin.androidContext

class MyRecipeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        diSetup()
    }

    private fun diSetup() {
        initKoin {
            androidContext(this@MyRecipeApplication)
        }
    }
}
