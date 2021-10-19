package com.cydinfo.utils

import android.os.Parcel
import java.sql.Timestamp


fun Parcel.writeTimestamp(timestamp: Timestamp) {
    writeLong(timestamp.time)
}

fun Parcel.readTimestamp(): Timestamp {
    val timeMillis = readLong()
    return Timestamp(timeMillis)
}

fun Parcel.writeTimestampNullable(timestamp: Timestamp?) {
    writeLong(timestamp?.time ?: -1)
}

fun Parcel.readTimestampNullable(): Timestamp? {
    val timeMillis = readLong()
    return if (timeMillis != -1L) Timestamp(timeMillis) else null
}
