package kz.kolesateam.confapp.events.data.models

import kz.kolesateam.confapp.events.presentation.models.EventData
import kz.kolesateam.confapp.events.presentation.models.SpeakerData

private const val DEFAULT_EVENT_NAME = "Название события не указано"
private const val DEFAULT_START_TIME_TEXT = "Время начала не указано"
private const val DEFAULT_END_TIME_TEXT = "Время завершения не указано"
private const val DEFAULT_PLACE_TEXT = "Место проведения не указано"

class EventApiDataMapper : Mapper<List<EventApiData>, List<EventData>> {
    private val speakerMapper: Mapper<SpeakerApiData, SpeakerData> = SpeakerApiDataMapper()
    private val listEvents: MutableList<EventData> = mutableListOf()

    override fun map(data: List<EventApiData>?): List<EventData> {
        listEvents.clear()
        for (index in data?.indices!!) {
            listEvents.add(EventData(
                startTime = data[index].startTime ?: DEFAULT_START_TIME_TEXT,
                endTime = data[index].endTime ?: DEFAULT_END_TIME_TEXT,
                title = data[index].title ?: DEFAULT_EVENT_NAME,
                place = data[index].place ?: DEFAULT_PLACE_TEXT,
                speaker = speakerMapper.map(data[index].speaker)
            ))
        }
        return listEvents.toList()
    }

}