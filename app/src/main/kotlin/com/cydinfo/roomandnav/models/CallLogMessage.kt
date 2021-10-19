package com.cydinfo.roomandnav.models

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.TypeConverter
import java.sql.Timestamp
import java.util.*

import com.cydinfo.utils.readTimestamp
import com.cydinfo.utils.writeTimestamp

@Entity(tableName = "MESSAGES", primaryKeys = ["CALL_LOG_ID", "CREATED_AT"] )
class CallLogMessage() : Parcelable {
    enum class MESSAGE_TYPE(val code: Int) {
        RECEIVED(0), SENT(1), SYSTEM(2);

        object MESSAGE_TYPE_CONVERTER {
            @TypeConverter
            fun toInteger(value: MESSAGE_TYPE?): Int? {
                return value?.code
            }

            @TypeConverter
            fun toMESSAGE_TYPE(value: Int?): MESSAGE_TYPE? {
                if (value == null) return null else {
                    for (mt in values()) {
                        if (mt.code == value) return mt
                    }
                }
                return null
            }
        }
    }

    @NonNull
    @ColumnInfo(name = "CALL_LOG_ID")
    var callLogId: Long = -1

//    @NonNull
    @ColumnInfo(name = "CREATED_AT")
    var createdAt: Timestamp = Timestamp(Calendar.getInstance().timeInMillis)

    @ColumnInfo(name = "MESSAGE_TYPE")
    var messageType: MESSAGE_TYPE? = null

    @ColumnInfo(name = "MESSAGE")
    var message: String? = null


    constructor(callLogId : Long, messageType : MESSAGE_TYPE, message : String) : this() {
        this.callLogId = callLogId
        this.messageType = messageType
        this.createdAt = Timestamp(Calendar.getInstance().timeInMillis)
        this.message = message
    }

    constructor(parcel: Parcel) : this() {
        callLogId = parcel.readValue(Long::class.java.classLoader) as Long
        createdAt = parcel.readTimestamp()
        val messageTypeCode = parcel.readInt()
        messageType = if (messageTypeCode == -1) null else MESSAGE_TYPE.MESSAGE_TYPE_CONVERTER.toMESSAGE_TYPE(messageTypeCode)
        message = parcel.readValue(String::class.java.classLoader) as? String
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(callLogId)
        parcel.writeTimestamp(createdAt)
        parcel.writeInt(messageType?.code ?: -1)
        parcel.writeValue(message)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CallLogMessage> {
        override fun createFromParcel(parcel: Parcel): CallLogMessage {
            return CallLogMessage(parcel)
        }

        override fun newArray(size: Int): Array<CallLogMessage?> {
            return arrayOfNulls(size)
        }
    }
}