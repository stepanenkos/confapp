package kz.kolesateam.confapp

import androidx.multidex.MultiDexApplication
import com.jakewharton.threetenabp.AndroidThreeTen
import kz.kolesateam.confapp.allevents.di.allEventsScreenModule
import kz.kolesateam.confapp.di.applicationModule
import kz.kolesateam.confapp.upcomingevents.di.eventScreenModule
import kz.kolesateam.confapp.di.userNameModule
import kz.kolesateam.confapp.eventdetails.di.eventDetailsModule
import kz.kolesateam.confapp.favoriteevents.di.favoriteEventsModule
import kz.kolesateam.confapp.notifications.NotificationHelper
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ConfAppApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        NotificationHelper.init(this)
        AndroidThreeTen.init(this)
        startKoin {
            androidContext(this@ConfAppApplication)
            modules(
                favoriteEventsModule,
                eventScreenModule,
                userNameModule,
                applicationModule,
                allEventsScreenModule,
                eventDetailsModule,
            )
        }
    }
}