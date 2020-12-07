package kz.kolesateam.confapp.utils.mappers

interface Mapper<SRC, DST> {
    fun map(data: SRC?): DST
}