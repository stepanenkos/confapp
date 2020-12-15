package kz.kolesateam.confapp.utils.mappers
import android.net.ParseException
import kz.kolesateam.confapp.upcomingevents.data.models.EventApiData
import kz.kolesateam.confapp.upcomingevents.data.models.SpeakerApiData
import kz.kolesateam.confapp.models.EventData
import kz.kolesateam.confapp.models.SpeakerData
import java.text.SimpleDateFormat
import java.util.*

private const val DEFAULT_EVENT_TITLE = ""
private const val DEFAULT_EVENT_DESCRIPTION = ""
private const val DEFAULT_EVENT_ID = 0
private const val DEFAULT_START_TIME_TEXT = ""
private const val DEFAULT_END_TIME_TEXT = ""
private const val DEFAULT_PLACE_TEXT = ""
private const val TIME_FORMAT = "HH:mm"

class EventApiDataMapper : Mapper<List<EventApiData>?, List<EventData>> {
    private val speakerMapper: Mapper<SpeakerApiData, SpeakerData> = SpeakerApiDataMapper()

    override fun map(data: List<EventApiData>?): List<EventData> {
        data ?: return emptyList()

        return data.map {
            val startTime = getTimeByFormat(it.startTime ?: DEFAULT_START_TIME_TEXT)
            val endTime = getTimeByFormat(it.endTime ?: DEFAULT_END_TIME_TEXT)
            startTime.time = Date().time
            startTime.hours = 22
            startTime.minutes = 43
            startTime.year = 2020 - 1900
            startTime.month = 11
            startTime.seconds = 0
            EventData(
                id = it.id ?: DEFAULT_EVENT_ID,
                startTime = startTime,
                endTime = endTime,
                title = it.title ?: DEFAULT_EVENT_TITLE,
                description = it.description ?: DEFAULT_EVENT_DESCRIPTION,
                place = it.place ?: DEFAULT_PLACE_TEXT,
                speaker = speakerMapper.map(it.speaker)
            )
        }
    }

    private fun getTimeByFormat(
        time: String
    ): Date = try {
        val simpleDateFormat = SimpleDateFormat(TIME_FORMAT, Locale.ROOT)

        simpleDateFormat.parse(time) ?: Date()
    } catch (e: ParseException) {
        Date()
    }
}