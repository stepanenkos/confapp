package kz.kolesateam.confapp.utils.mappers
import kz.kolesateam.confapp.events.data.models.EventApiData
import kz.kolesateam.confapp.events.data.models.SpeakerApiData
import kz.kolesateam.confapp.models.EventData
import kz.kolesateam.confapp.models.SpeakerData
import java.text.SimpleDateFormat
import java.util.*

private const val DEFAULT_EVENT_TITLE = "Название события не указано"
private const val DEFAULT_EVENT_DESCRIPTION = "Описание события не указано"
private const val DEFAULT_EVENT_ID = 0
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
                    id = data?.get(index)?.id ?: DEFAULT_EVENT_ID,
                    startTime = startTime,
                    endTime = endTime,
                    title = data?.get(index)?.title ?: DEFAULT_EVENT_TITLE,
                    description = data?.get(index)?.description ?: DEFAULT_EVENT_DESCRIPTION,
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
                id = data?.id ?: DEFAULT_EVENT_ID,
                startTime = startTime,
                endTime = endTime,
                title = data?.title ?: DEFAULT_EVENT_TITLE,
                description = data?.description ?: DEFAULT_EVENT_DESCRIPTION,
                place = data?.place ?: DEFAULT_PLACE_TEXT,
                speaker = speakerMapper.map(data?.speaker)
            )
        }
}