package kz.kolesateam.confapp

import androidx.multidex.MultiDexApplication
import kz.kolesateam.confapp.allevents.di.allEventsScreenModule
import kz.kolesateam.confapp.di.applicationModule
import kz.kolesateam.confapp.di.eventScreenModule
import kz.kolesateam.confapp.di.userNameModule
import kz.kolesateam.confapp.favorite_events.di.favoriteEventsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ConfAppApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ConfAppApplication)
            modules(
                favoriteEventsModule,
                eventScreenModule,
                userNameModule,
                applicationModule,
                allEventsScreenModule,
            )
        }
    }
}