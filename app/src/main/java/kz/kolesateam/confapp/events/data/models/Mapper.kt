package kz.kolesateam.confapp.events.data.models

interface Mapper<SRC, DST> {
    fun map(data: SRC?): DST
}