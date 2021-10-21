package com.cydinfo.roomandnav.models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp
import java.util.Calendar

import com.cydinfo.utils.readTimestamp
import com.cydinfo.utils.readTimestampNullable
import com.cydinfo.utils.writeTimestamp
import com.cydinfo.utils.writeTimestampNullable
import com.cydinfo.utils.format

import java.text.SimpleDateFormat

@Entity(tableName = "CALL_LOGS")
class CallLog() : Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    var id : Long = NEW_ID

    @ColumnInfo(name = "NAME")
    var name: String? = null

    @ColumnInfo(name = "CONTACT_ID")
    var contactId: String? = null

    @ColumnInfo(name = "MISSED")
    var missed: Boolean = false

    @ColumnInfo(name = "MESSAGE_COUNT")
    var messageCount = 0

    @ColumnInfo(name = "STARTED_AT")
    var startedAt: Timestamp = Timestamp(Calendar.getInstance().timeInMillis)

    @ColumnInfo(name = "ENDED_AT")
    var endedAt: Timestamp? = null

//    constructor(name : String, contactId : String?, missed : Boolean) : this() {
//        id = -1
//        this.name = name
//        this.contactId = contactId
//        this.missed = missed
//        this.messageCount = 0
//        this.startedAt = Timestamp(Calendar.getInstance().timeInMillis)
//        this.endedAt = if (missed) this.startedAt else null
//    }

    override fun toString(): String {
        val sd = SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS")
        return StringBuilder()
            .append("{")
            .append("id:").append(id)
            .append(" ,name:").append(if (name == null) "null" else "\"$name\"")
            .append(" ,contactId:").append(if (contactId == null) "null" else "\"$contactId\"")
            .append(" ,missed:").append(missed)
            .append(" ,messageCount:").append(messageCount)
            .append(" ,startedAt:").append("\"" + startedAt.format() + "\"")
            .append(" ,endedAt:").append(if (endedAt != null) "\"" + endedAt!!.format() + "\"" else "null")
            .append("}").toString()
    }

    constructor(parcel: Parcel) : this() {
        id = parcel.readLong();
        name = parcel.readString()
        contactId = parcel.readString()
        missed = parcel.readByte() != 0.toByte()
        messageCount = parcel.readInt()
        startedAt = parcel.readTimestamp()
        endedAt = parcel.readTimestampNullable()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(name)
        parcel.writeString(contactId)
        parcel.writeByte(if (missed) 1 else 0)
        parcel.writeInt(messageCount)
        parcel.writeTimestamp(startedAt)
        parcel.writeTimestampNullable(endedAt)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CallLog> {
        const val NEW_ID : Long = 0

        override fun createFromParcel(parcel: Parcel): CallLog {
            return CallLog(parcel)
        }

        override fun newArray(size: Int): Array<CallLog?> {
            return arrayOfNulls(size)
        }

        fun missedCall(name : String, contactId : String?, startedAtOrNull : Timestamp?) : CallLog {
            var newCallLog = CallLog()

            newCallLog.id = NEW_ID
            newCallLog.name = name
            newCallLog.contactId = contactId
            newCallLog.missed = true
            newCallLog.messageCount = 0
            (startedAtOrNull?: Timestamp(Calendar.getInstance().timeInMillis)).also { newCallLog.startedAt = it }
            newCallLog.endedAt = newCallLog.startedAt

            return newCallLog
        }

        fun newCall(name : String, contactId : String?, missed : Boolean, messageCount : Int, startedAt : Timestamp) : CallLog {
            var newCallLog = CallLog()
            newCallLog.id = NEW_ID
            newCallLog.name = name
            newCallLog.contactId = contactId
            newCallLog.missed = missed
            newCallLog.messageCount = messageCount
            newCallLog.startedAt = startedAt
            newCallLog.endedAt = Timestamp(Calendar.getInstance().timeInMillis)

            return newCallLog
        }


    }
}