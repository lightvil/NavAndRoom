package kr.pe.lightvil.models

import androidx.room.TypeConverter
import java.sql.Date
import java.sql.Timestamp

class EyePhoneTypeConverters {
    //
    // Timestamp <==> Long
    //
    @TypeConverter
    fun toTimestamp(value: Long?): Timestamp? {
        return if (value == null) null else Timestamp(value)
    }

    @TypeConverter
    fun toLong(value: Timestamp?): Long? {
        return value?.time
    }

    //
    // Date <==> Long
    //
    @TypeConverter
    fun toDate(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun toLong(value: Date?): Long? {
        return value?.time
    }

    //
    //  Boolean <==> Integer
    //
    @TypeConverter
    fun toBoolean(value: Int?): Boolean? {
        return if (value == null) null else if (value == 0) java.lang.Boolean.FALSE else java.lang.Boolean.TRUE
    }

    private val ZERO_INT   = Integer.valueOf(0)
    private val ONE_INT    = Integer.valueOf(1)
    private val ZERO_BYTE  = java.lang.Byte.valueOf(0.toByte())
    private val ONE_BYTE   = java.lang.Byte.valueOf(1.toByte())

    @TypeConverter
    fun toInteger(value: Boolean?): Int? {
        return if (value == null) null else if (value) ONE_INT else ZERO_INT
    }

    @TypeConverter
    fun toBoolean(value: Byte?): Boolean? {
        return if (value == null) null else if (value.toInt() == 0) java.lang.Boolean.FALSE else java.lang.Boolean.TRUE
    }

    @TypeConverter
    fun toByte(value: Boolean?): Byte? {
        return if (value == null) null else if (value) ONE_BYTE else ZERO_BYTE
    }
}