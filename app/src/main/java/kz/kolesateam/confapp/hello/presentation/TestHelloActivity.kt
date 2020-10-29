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
        val textView: TextView = findViewById(R.id.textViewForName)
        val sharedPref = getSharedPreferences(getString(R.string.shared_preferences_name_key), Context.MODE_PRIVATE)
        val name = sharedPref.getString(getString(R.string.shared_preferences_name_key), "Пользователь")
        textView.text = name
    }
}