package kr.pe.lightvil.models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.TypeConverter
import java.sql.Timestamp
import java.util.*

@Entity(tableName = "MESSAGES", primaryKeys = ["CALL_LOG_ID", "SEQ"])
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
                    for (mt in MESSAGE_TYPE.values()) {
                        if (mt.code == value) return mt
                    }
                }
                return null
            }
        }
    }

    @ColumnInfo(name = "CALL_LOG_ID")
    var callLogId: Long? = null

    @ColumnInfo(name = "SEQ")
    var seq: Int? = null

    @ColumnInfo(name = "MESSAGE_TYPE")
    var messageType: MESSAGE_TYPE? = null

    @ColumnInfo(name = "CREATED_AT")
    var createdAt: Timestamp? = null

    @ColumnInfo(name = "MESSAGE")
    var message: String? = null

    constructor(callLodid : Long, seq : Int, messageType : MESSAGE_TYPE, message : String) : this() {
        this.callLogId = callLogId
        this.messageType = messageType
        this.seq = seq
        this.createdAt = Timestamp(Calendar.getInstance().timeInMillis)
    }

    constructor(parcel: Parcel) : this() {
        callLogId = parcel.readValue(Long::class.java.classLoader) as? Long
        seq = parcel.readValue(Int::class.java.classLoader) as? Int
        message = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(callLogId)
        parcel.writeValue(seq)
        parcel.writeString(message)
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