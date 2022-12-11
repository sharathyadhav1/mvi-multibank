package com.multibank.mvi.di

import com.multibank.mvi.ui.base.mvi.SchedulersProvider
import com.multibank.mvi.ui.base.mvi.DefaultSchedulersProvider
import org.koin.dsl.module


val appModule = module {

    single<SchedulersProvider> { DefaultSchedulersProvider.instance }

}