package com.typer.typerush

import android.app.Application
import com.typer.typerush.core.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TypeRush: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TypeRush)
            androidLogger()

            modules(
                appModule
            )
        }
    }
}