package kz.kolesateam.confapp.hello.presentation

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kz.kolesateam.confapp.R

class TestHelloActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_hello)
        val helloTextView: TextView = findViewById(R.id.textViewForName)
        val sharedPref = getSharedPreferences(SHARED_PREFERENCES_NAME_KEY, Context.MODE_PRIVATE)
        val name = sharedPref.getString(SHARED_PREFERENCES_NAME_KEY, "Пользователь")
        helloTextView.text = name
    }
}