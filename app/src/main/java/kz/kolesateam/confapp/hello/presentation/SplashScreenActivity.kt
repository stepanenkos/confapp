package kz.kolesateam.confapp.hello.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivity(HelloRouter().createIntent(this))
        finish()
    }
}