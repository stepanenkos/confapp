package kz.kolesateam.confapp.hello.presentation

import android.content.Context
import android.content.Intent

class HelloRouter {

    fun createIntent(
        context: Context
    ) : Intent = Intent(context, HelloActivity::class.java)

}