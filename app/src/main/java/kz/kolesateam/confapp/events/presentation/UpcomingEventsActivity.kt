package kz.kolesateam.confapp.events.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.events.data.ApiClient
import kz.kolesateam.confapp.events.data.ApiClientSingleton
import kz.kolesateam.confapp.events.data.JetpackDataStore
import kz.kolesateam.confapp.events.data.models.BranchApiData
import kz.kolesateam.confapp.events.data.models.FormattingString
import kz.kolesateam.confapp.events.data.models.UpcomingEventsRepository
import retrofit2.converter.jackson.JacksonConverterFactory
import kz.kolesateam.confapp.extensions.gone
import kz.kolesateam.confapp.extensions.show

class UpcomingEventsActivity : AppCompatActivity() {

    private lateinit var syncLoadingButton: Button
    private lateinit var asyncLoadingButton: Button
    private lateinit var upcomingEventsTextView: TextView
    private lateinit var upcomingEventsProgressBar: ProgressBar
    private val apiClient: ApiClient = ApiClientSingleton.getApiClient(
        "http://37.143.8.68:2020",
        JacksonConverterFactory.create(),
        ApiClient::class.java
    )
    private lateinit var jetpackDataStore: JetpackDataStore
    private lateinit var branchApiData: List<BranchApiData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upcoming_events)
        initViews()

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        saveData()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        restoreData()
        super.onRestoreInstanceState(savedInstanceState)
    }

    private fun initViews() {
        syncLoadingButton = findViewById(R.id.activity_upcoming_events_btn_sync_loading)
        asyncLoadingButton = findViewById(R.id.activity_upcoming_events_btn_async_loading)
        upcomingEventsTextView = findViewById(R.id.activity_upcoming_events_text_view)
        upcomingEventsProgressBar = findViewById(R.id.activity_upcoming_events_progress_bar)

        jetpackDataStore = JetpackDataStore(this)

        syncLoadingButton.setOnClickListener {
            upcomingEventsProgressBar.show()
            loadSyncData()
        }

        asyncLoadingButton.setOnClickListener {
            upcomingEventsProgressBar.show()
            loadAsyncData()
        }
    }

private fun loadSyncData() {
    GlobalScope.launch(Dispatchers.IO) {
        runCatching {
            branchApiData = UpcomingEventsRepository().loadUpcomingEventsSync()
            launch(Dispatchers.Main) {
                setViewAttributeOnResponse(
                    branchApiData,
                    R.color.activity_upcoming_events_sync_text_view
                )
            }
        }.onFailure { t ->
            GlobalScope.launch(Dispatchers.Main) {
                setViewAttributeOnFailure(t.localizedMessage!!)
            }
        }
    }
}
    private fun loadAsyncData() {
        UpcomingEventsRepository().getUpcomingEventsAsync({
            branchApiData = it
            setViewAttributeOnResponse(branchApiData, R.color.activity_upcoming_events_async_text_view)
        },
            {
                setViewAttributeOnFailure(it)
            })

    }

    private fun setViewAttributeOnResponse(list: List<BranchApiData>, textColor: Int) {
            upcomingEventsProgressBar.gone()
            upcomingEventsTextView.setTextColor(
                ContextCompat.getColor(this, textColor)
            )
            upcomingEventsTextView.text = FormattingString.formatString(list)
    }

    private fun setViewAttributeOnFailure(throwable: String) {
        upcomingEventsProgressBar.gone()
        upcomingEventsTextView.setTextColor(
            ContextCompat.getColor(this, R.color.activity_upcoming_events_error_text_view)
        )
        upcomingEventsTextView.text = throwable
    }

    private fun saveData() {
        GlobalScope.launch(Dispatchers.IO) {
            jetpackDataStore.saveData(
                upcomingEventsTextView.text.toString(),
                upcomingEventsTextView.currentTextColor
            )
        }
    }

    private fun restoreData() {
        jetpackDataStore.downloadedDataFlow.asLiveData(Dispatchers.Main).observe(this, {
            upcomingEventsTextView.text = it.toString()
        })
        jetpackDataStore.colorTextFlow.asLiveData(Dispatchers.Main).observe(this, {
            upcomingEventsTextView.setTextColor(it)

        })
    }
}