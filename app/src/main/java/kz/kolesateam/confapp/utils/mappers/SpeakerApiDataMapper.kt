package kz.kolesateam.confapp.utils.mappers

import kz.kolesateam.confapp.upcomingevents.data.models.SpeakerApiData
import kz.kolesateam.confapp.models.SpeakerData

private const val DEFAULT_SPEAKER_ID = 0
private const val DEFAULT_SPEAKER_NAME = ""
private const val DEFAULT_JOB_SPEAKER = ""
private const val DEFAULT_SPEAKER_PHOTO_URL = ""

class SpeakerApiDataMapper {

    fun map(speakerApiData: SpeakerApiData?): SpeakerData {
        return SpeakerData(
            id = speakerApiData?.id ?: DEFAULT_SPEAKER_ID,
            fullName = speakerApiData?.fullName ?: DEFAULT_SPEAKER_NAME,
            job = speakerApiData?.job ?: DEFAULT_JOB_SPEAKER,
            photoUrl = speakerApiData?.photoUrl ?: DEFAULT_SPEAKER_PHOTO_URL
        )
    }

    fun map(speakerData: SpeakerData?): SpeakerApiData {
        return SpeakerApiData(
            id = speakerData?.id ?: DEFAULT_SPEAKER_ID,
            fullName = speakerData?.fullName ?: DEFAULT_SPEAKER_NAME,
            job = speakerData?.job ?: DEFAULT_JOB_SPEAKER,
            photoUrl = speakerData?.photoUrl ?: DEFAULT_SPEAKER_PHOTO_URL
        )
    }
}