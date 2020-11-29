package kz.kolesateam.confapp.events.data.models

import kz.kolesateam.confapp.events.presentation.models.SpeakerData

private const val DEFAULT_SPEAKER_NAME = "Имя Спикера не указано"
private const val DEFAULT_JOB_SPEAKER = "Работа Спикера не указана"

class SpeakerApiDataMapper: Mapper<SpeakerApiData, SpeakerData> {

    override fun map(data: SpeakerApiData?): SpeakerData {
        return SpeakerData(
            fullName = data?.fullName ?: DEFAULT_SPEAKER_NAME,
            job = data?.job ?: DEFAULT_JOB_SPEAKER
        )
    }
}