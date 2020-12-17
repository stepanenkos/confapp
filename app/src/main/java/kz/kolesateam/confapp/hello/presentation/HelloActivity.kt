package kz.kolesateam.confapp.hello.presentation


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.datasource.UserNameDataSource
import kz.kolesateam.confapp.events.presentation.UpcomingEventsRouter
import kz.kolesateam.confapp.presentation.common.AbstractTextWatcher
import org.koin.android.ext.android.inject

private const val USER_NAME = "user_name"

class HelloActivity : AppCompatActivity() {
    private val userNameDataSource: UserNameDataSource by inject()

    private lateinit var nameEditText: EditText
    private lateinit var continueButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_ConfApp)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hello)
        initViews()
        if (userNameDataSource.isSavedUserName()) {
            startActivity(UpcomingEventsRouter().createIntent(this))
        }
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
            startActivity(intent)
        }
    }

    private fun saveUserName(userName: String) {
        userNameDataSource.saveUserName(userName)
    }
}