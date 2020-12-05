package kz.kolesateam.confapp.events.data.models
import kz.kolesateam.confapp.events.presentation.models.EventData
import kz.kolesateam.confapp.events.presentation.models.SpeakerData
import java.text.SimpleDateFormat
import java.util.*

private const val DEFAULT_EVENT_NAME = "Название события не указано"
private const val DEFAULT_START_TIME_TEXT = "00:00"
private const val DEFAULT_END_TIME_TEXT = "Время завершения не указано"
private const val DEFAULT_PLACE_TEXT = "Место проведения не указано"

class EventApiDataMapper : Mapper<List<EventApiData>, List<EventData>> {
    private val speakerMapper: Mapper<SpeakerApiData, SpeakerData> = SpeakerApiDataMapper()
    private val eventsList: MutableList<EventData> = mutableListOf()

    override fun map(data: List<EventApiData>?): List<EventData> {
        eventsList.clear()

            for (index in data?.indices!!) {
                val simpleDateFormat = SimpleDateFormat("HH:mm", Locale.ROOT)
                val startTime = simpleDateFormat.parse(data[index].startTime ?: DEFAULT_START_TIME_TEXT)
                val endTime = simpleDateFormat.parse(data[index].endTime ?: DEFAULT_END_TIME_TEXT)
                eventsList.add(EventData(
                    startTime = startTime,
                    endTime = endTime,
                    title = data?.get(index)?.title ?: DEFAULT_EVENT_NAME,
                    place = data?.get(index)?.place ?: DEFAULT_PLACE_TEXT,
                    speaker = speakerMapper.map(data?.get(index)?.speaker)
                ))
            }

        return eventsList.toList()
    }

    fun map(data: EventApiData?): EventData {
        val simpleDateFormat = SimpleDateFormat("hh:mm", Locale.ROOT)
        val startTime = simpleDateFormat.parse(data?.startTime ?: DEFAULT_START_TIME_TEXT)
        val endTime = simpleDateFormat.parse(data?.endTime ?: DEFAULT_END_TIME_TEXT)
            return EventData(
                startTime = startTime,
                endTime = endTime,
                title = data?.title ?: DEFAULT_EVENT_NAME,
                place = data?.place ?: DEFAULT_PLACE_TEXT,
                speaker = speakerMapper.map(data?.speaker)
            )
        }
}