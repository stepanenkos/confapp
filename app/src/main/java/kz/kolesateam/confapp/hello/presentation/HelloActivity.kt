package kz.kolesateam.confapp.hello.presentation


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import kz.kolesateam.confapp.R

private const val TAG = "HelloActivity"
const val EXTRA_NAME_KEY = "NAME"
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
                    buttonControl(true, resources.getColor(R.color.hello_activity_color_button_continue_enabled))
                } else {
                    buttonControl(false, resources.getColor(R.color.hello_activity_color_button_continue_disabled))
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

    }
    private fun buttonControl(isEnabled: Boolean, backgroundColor: Int) {
        buttonContinue.isEnabled = isEnabled
        buttonContinue.setBackgroundColor(backgroundColor)
    }

}