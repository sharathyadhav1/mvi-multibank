package com.multibank.mvi.di

import com.multibank.mvi.data.repositories.TaskRepository
import com.multibank.mvi.data.repositories.TaskRepositoryImpl
import org.koin.dsl.module


val repositoriesModule = module {

    factory<TaskRepository> { TaskRepositoryImpl(get()) }
}