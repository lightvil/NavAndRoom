package com.cydinfo.roomandnav.models

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.TypeConverter
import com.cydinfo.utils.format
import java.sql.Timestamp
import java.util.*

import com.cydinfo.utils.readTimestamp
import com.cydinfo.utils.writeTimestamp
import java.text.SimpleDateFormat

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

    constructor(messageType : MESSAGE_TYPE, message : String) : this() {
        this.callLogId = -1
        this.messageType = messageType
        this.createdAt = Timestamp(Calendar.getInstance().timeInMillis)
        this.message = message
    }

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

    override fun toString(): String {
        return StringBuilder()
            .append("{")
            .append("callLogId:").append(callLogId)
            .append(" ,createdAt:").append("\"" + createdAt.format() + "\"")
            .append("messageType:").append(if (messageType == null) "null" else  messageType!!.name)
            .append(" ,message:").append(message)
            .append("}").toString()
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

        fun receivedMessage(message : String, createdAtOrNull : Timestamp?) : CallLogMessage {
            var receivedMessage = CallLogMessage()

            receivedMessage.callLogId = -1
            receivedMessage.messageType = MESSAGE_TYPE.RECEIVED
            //sentMessage.createdAt = if (startedAtOrNull != null) startedAtOrNull else Timestamp(Calendar.getInstance().timeInMillis)
            receivedMessage.createdAt = createdAtOrNull ?: Timestamp(Calendar.getInstance().timeInMillis)
            receivedMessage.message = message

            return receivedMessage
        }

        fun receivedMessage(callLog : CallLog?, message : String, createdAtOrNull : Timestamp?) : CallLogMessage {
            var receivedMessage = CallLogMessage()

            receivedMessage.callLogId = callLog?.id ?: CallLog.NEW_ID
            receivedMessage.messageType = MESSAGE_TYPE.RECEIVED
            //sentMessage.createdAt = if (startedAtOrNull != null) startedAtOrNull else Timestamp(Calendar.getInstance().timeInMillis)
            receivedMessage.createdAt = createdAtOrNull ?: Timestamp(Calendar.getInstance().timeInMillis)
            receivedMessage.message = message

            return receivedMessage
        }

        fun sentMessage(message : String, createdAtOrNull : Timestamp?) : CallLogMessage {
            var sentMessage = CallLogMessage()

            sentMessage.callLogId = -1
            sentMessage.messageType = MESSAGE_TYPE.SENT
            //sentMessage.createdAt = if (startedAtOrNull != null) startedAtOrNull else Timestamp(Calendar.getInstance().timeInMillis)
            sentMessage.createdAt = createdAtOrNull ?: Timestamp(Calendar.getInstance().timeInMillis)
            sentMessage.message = message

            return sentMessage
        }

        fun sentMessage(callLog : CallLog?, message : String, createdAtOrNull : Timestamp?) : CallLogMessage {
            var sentMessage = CallLogMessage()

            //sentMessage.callLogId = if (callLog == null) CallLog.NEW_ID else callLog.id
            sentMessage.callLogId = callLog?.id ?: CallLog.NEW_ID
            sentMessage.messageType = MESSAGE_TYPE.SENT
            //sentMessage.createdAt = if (startedAtOrNull != null) startedAtOrNull else Timestamp(Calendar.getInstance().timeInMillis)
            sentMessage.createdAt = createdAtOrNull ?: Timestamp(Calendar.getInstance().timeInMillis)
            sentMessage.message = message

            return sentMessage
        }

    }
}