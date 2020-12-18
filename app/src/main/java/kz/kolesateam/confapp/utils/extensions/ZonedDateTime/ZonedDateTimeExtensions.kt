package kz.kolesateam.confapp.utils.extensions.ZonedDateTime

import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter

fun ZonedDateTime.getEventFormattedDateTime(pattern: String): String {
    return this.format(DateTimeFormatter.ofPattern(pattern))
}