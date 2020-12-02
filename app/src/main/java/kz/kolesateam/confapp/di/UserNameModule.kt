package kz.kolesateam.confapp.di

import kz.kolesateam.confapp.events.data.datasource.UserNameDataSource
import kz.kolesateam.confapp.events.data.datasource.UserNameSharedPrefsDataSource
import org.koin.core.module.Module
import org.koin.dsl.module


val userNameModule: Module = module {

    factory {
        UserNameSharedPrefsDataSource(
            sharedPreferences = get()
        ) as UserNameDataSource
    }

}