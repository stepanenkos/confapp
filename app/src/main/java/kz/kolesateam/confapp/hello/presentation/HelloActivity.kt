package kz.kolesateam.confapp.hello.presentation


import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import kz.kolesateam.confapp.R

class HelloActivity : AppCompatActivity() {
    private lateinit var editTextEnterYourName: EditText
    private lateinit var buttonContinue: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hello)

        editTextEnterYourName = findViewById(R.id.editTextEnterYourName)
        buttonContinue = findViewById(R.id.buttonContinue)

        editTextEnterYourName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrBlank() && !s.isNullOrEmpty()) {
                    buttonControl(
                        true,
                        resources.getColor(R.color.hello_activity_color_button_continue_enabled)
                    )
                } else {
                    buttonControl(
                        false,
                        resources.getColor(R.color.hello_activity_color_button_continue_disabled)
                    )
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
        buttonContinue.setOnClickListener {
            val name = editTextEnterYourName.text.toString().trim()
            val sharedPref = getSharedPreferences(
                getString(R.string.shared_preferences_name_key), Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                putString(getString(R.string.shared_preferences_name_key), name)
                apply()
            }
            val intent = Intent(this, TestHelloActivity::class.java)
            startActivity(intent)
        }
    }

    private fun buttonControl(isEnabled: Boolean, backgroundColor: Int) {
        buttonContinue.isEnabled = isEnabled
        buttonContinue.setBackgroundColor(backgroundColor)
    }

}