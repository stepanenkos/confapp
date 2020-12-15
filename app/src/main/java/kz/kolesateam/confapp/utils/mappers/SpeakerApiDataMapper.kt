package kz.kolesateam.confapp.utils.mappers

import kz.kolesateam.confapp.upcomingevents.data.models.SpeakerApiData
import kz.kolesateam.confapp.models.SpeakerData

private const val DEFAULT_SPEAKER_ID = 0
private const val DEFAULT_SPEAKER_NAME = ""
private const val DEFAULT_JOB_SPEAKER = ""
private const val DEFAULT_SPEAKER_PHOTO_URL = ""

class SpeakerApiDataMapper: Mapper<SpeakerApiData, SpeakerData> {

    override fun map(data: SpeakerApiData?): SpeakerData {
        return SpeakerData(
            id = data?.id ?: DEFAULT_SPEAKER_ID,
            fullName = data?.fullName ?: DEFAULT_SPEAKER_NAME,
            job = data?.job ?: DEFAULT_JOB_SPEAKER,
            photoUrl = data?.photoUrl ?: DEFAULT_SPEAKER_PHOTO_URL
        )
    }
}