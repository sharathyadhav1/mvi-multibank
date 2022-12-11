package com.multibank.mvi.di

import androidx.room.Room
import com.multibank.mvi.data.room.AppDatabase
import org.koin.dsl.module


val roomModule = module {

    single {
        Room.databaseBuilder(get(), AppDatabase::class.java, "app-database")
            .fallbackToDestructiveMigration()
            .build()
    }

    factory { get<AppDatabase>().taskDao() }
}