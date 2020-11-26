package kz.kolesateam.confapp

import android.app.Application
import kz.kolesateam.confapp.di.applicationModule
import kz.kolesateam.confapp.di.eventScreenModule
import kz.kolesateam.confapp.di.userNameModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class ConfAppApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ConfAppApplication)
            modules(
                eventScreenModule,
                userNameModule,
                applicationModule,
            )
        }
    }
}