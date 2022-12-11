package com.multibank.mvi

import android.app.Application
import com.multibank.mvi.di.appModule
import com.multibank.mvi.di.repositoriesModule
import com.multibank.mvi.di.roomModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin


class MviApp: Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this

        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@MviApp)
            modules(
                listOf(
                    appModule,
                    roomModule,
                    repositoriesModule
                )
            )
        }
    }

    companion object {

        @JvmStatic
        lateinit var instance: MviApp
            private set
    }
}