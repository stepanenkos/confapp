package kz.kolesateam.confapp.hello.presentation


import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.JetpackDataStore
import kz.kolesateam.confapp.events.presentation.UpcomingEventsRouter
import kz.kolesateam.confapp.presentation.common.AbstractTextWatcher

const val SHARED_PREFERENCES_NAME_KEY = "name"
const val USER_NAME = "name"

class HelloActivity : AppCompatActivity() {
    private lateinit var nameEditText: EditText
    private lateinit var continueButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hello)
        initViews()
    }

    private fun initViews() {
        nameEditText = findViewById(R.id.activity_hello_edit_text_your_name)
        continueButton = findViewById(R.id.activity_hello_continue_button)
        nameEditText.addTextChangedListener(object : AbstractTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                if(s.toString().isNotBlank()) {
                    nameEditText.setBackgroundResource(R.drawable.bg_edit_text_with_text)
                } else {
                    nameEditText.setBackgroundResource(R.drawable.bg_edit_text_empty)
                }
                continueButton.isEnabled = s.toString().isNotBlank()
            }
        })

        continueButton.setOnClickListener {
            saveUserName(nameEditText.text.toString().trim())
            val intent = UpcomingEventsRouter().createIntent(this)
            intent.putExtra(USER_NAME, nameEditText.text.toString().trim())
            startActivity(intent)
        }
    }

    private fun saveUserName(name: String) {
        val sharedPref = getSharedPreferences(
            SHARED_PREFERENCES_NAME_KEY, Context.MODE_PRIVATE
        )
        with(sharedPref.edit()) {
            putString(USER_NAME, name)
            apply()
        }
    }
}