package kz.kolesateam.confapp.hello.presentation


import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.presentation.common.AbstractTextWatcher

const val SHARED_PREFERENCES_NAME_KEY = "name"

class HelloActivity : AppCompatActivity() {
    private lateinit var editTextEnterYourName: EditText
    private lateinit var buttonContinue: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hello)
        initViews()
    }

    private fun initViews() {
        editTextEnterYourName = findViewById(R.id.editTextEnterYourName)
        buttonContinue = findViewById(R.id.buttonContinue)

        editTextEnterYourName.addTextChangedListener(object : AbstractTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                buttonContinue.isEnabled = s.toString().isNotBlank()
            }
        })

        buttonContinue.setOnClickListener {
            saveUserName(editTextEnterYourName.text.toString().trim())
            startActivity(HelloRouter().createIntent(this))
        }
    }

    private fun saveUserName(name: String) {
        val sharedPref = getSharedPreferences(
            SHARED_PREFERENCES_NAME_KEY, Context.MODE_PRIVATE
        )
        with(sharedPref.edit()) {
            putString(SHARED_PREFERENCES_NAME_KEY, name)
            apply()
        }
    }
}