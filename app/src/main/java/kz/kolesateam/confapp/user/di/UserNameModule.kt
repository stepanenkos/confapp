package kz.kolesateam.confapp.user.di

import kz.kolesateam.confapp.user.data.datasource.UserNameDataSource
import kz.kolesateam.confapp.user.data.datasource.UserNameSharedPrefsDataSource
import org.koin.core.module.Module
import org.koin.dsl.module

val userNameModule: Module = module {
    factory<UserNameDataSource> {
        UserNameSharedPrefsDataSource(
            sharedPreferences = get()
        )
    }
}