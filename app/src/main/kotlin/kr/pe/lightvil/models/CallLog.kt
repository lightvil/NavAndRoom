package kr.pe.lightvil.models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp
import java.util.*

@Entity(tableName = "CALL_LOG")
class CallLog() : Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    var id : Long? = null;

    @ColumnInfo(name = "NAME")
    var name: String? = null

    @ColumnInfo(name = "CONTACT_ID")
    var contactId: String? = null

    @ColumnInfo(name = "MISSED")
    var missed: Boolean = false;

    @ColumnInfo(name = "MESSAGE_COUNT")
    var messageCount = 0

    @ColumnInfo(name = "STARTED_AT")
    var startedAt: Timestamp = Timestamp(Calendar.getInstance().timeInMillis);

    @ColumnInfo(name = "ENDED_AT")
    var endedAt: Timestamp? = null

    constructor(parcel: Parcel) : this() {
        name = parcel.readString()
        contactId = parcel.readString()
        missed = parcel.readByte() != 0.toByte()
        messageCount = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(contactId)
        parcel.writeByte(if (missed) 1 else 0)
        parcel.writeInt(messageCount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CallLog> {
        override fun createFromParcel(parcel: Parcel): CallLog {
            return CallLog(parcel)
        }

        override fun newArray(size: Int): Array<CallLog?> {
            return arrayOfNulls(size)
        }
    }
}