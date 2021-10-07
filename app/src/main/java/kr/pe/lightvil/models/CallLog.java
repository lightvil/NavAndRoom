package kr.pe.lightvil.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.telecom.Call;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.sql.Timestamp;
import java.util.Calendar;


/*

 Database : call-log
 Table    : CALL_LOG

 CREATE TABLE CALL_LOG (
   ID : INTEGER PRIMARY KEY AUTOINCREMENT
   NAME : TEXT,

*/
@Entity(tableName = "CALL_LOG")
public class CallLog implements Parcelable {
    private static final Long    NULL_LONG    = -1L;
    private static final Integer NULL_INT  = -1;

    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "ID")
    Long id;
    @ColumnInfo(name="NAME")
    String    name;
    @ColumnInfo(name="CONTACT_ID")
    String    contactId;
    @ColumnInfo(name="MISSED")
    Boolean   missed;
    @ColumnInfo(name="MESSAGE_COUNT")
    Integer   messageCount = 0;
    @ColumnInfo(name="STARTED_AT")
    Timestamp startedAt;
    @ColumnInfo(name="ENDED_AT")
    Timestamp endedAt;

    private CallLog() {
    }

    public CallLog(
            Long id, String name, String contactId,
            Boolean missed,
            Timestamp startedAt, Timestamp endedAt,
            Integer messageCount
    ) {
        this.id           = id;
        this.name         = name;
        this.contactId    = contactId;
        this.missed       = missed;
        this.startedAt    = startedAt == null ? new Timestamp(Calendar.getInstance().getTimeInMillis()) : startedAt;
        this.endedAt      = endedAt;
        this.messageCount = messageCount;
    }

    //
    // Getters
    //
    public Long getId() {
        return id;
    }
    public String getName() { return this.name; }
    public String getContactId() { return this.contactId; }
    public Timestamp getStartedAt() { return this.startedAt; }
    public Timestamp getEndedAt() { return this.endedAt; }
    public Boolean getMissed() { return this.missed; }
    public Integer getMessageCount() {
        return this.messageCount;
    }

    public void setId(Long newId) { this.id = newId; }
    public void  getName(String newName) { this.name = newName; }
    public void getContactId(String newContactId) { this.contactId = newContactId; }
    public void getStartedAt(Timestamp newStartedAt) { this.startedAt = newStartedAt; }
    public void getEndedAt(Timestamp newEndedAt) { this.endedAt = newEndedAt; }
    public void getMissed(Boolean newMissed) { this.missed = newMissed; }
    public void getMessageCount(Integer newMessageCount) {
        this.messageCount = newMessageCount;
    }

    public void setMessageCount(int messageCount) {
        if (messageCount > 0) missed = false;
        this.messageCount = messageCount;
    }

    //
    // Factory methods...
    //
    public static CallLog create(
            Long id, String name, String contactId,
            Boolean missed,
            Timestamp startedAt, Timestamp endedAt,
            Integer messageCount
    ) {
        return new CallLog(id, name, contactId, missed, startedAt, endedAt, messageCount);
    }

    //
    // For Parcelable interface
    //
    public static final Creator<CallLog> CREATOR = new Creator<CallLog>() {
        @Override
        public CallLog createFromParcel(Parcel source) {
            long l;
            byte b;

            l = source.readLong();
            Long id = l == -1L ? null : l;

            String name = source.readString();
            String contactId = source.readString();

            b = source.readByte();
            Boolean missed = b == (byte) - 1 ? null : EyePhoneTypeConverters.toBoolean(b);

            l = source.readLong();
            Timestamp startedAt = l == -1L ? null : EyePhoneTypeConverters.toTimestamp(l);

            l = source.readLong();
            Timestamp endedAt = l == -1L ? null : EyePhoneTypeConverters.toTimestamp(l);

            Integer messageCount = source.readInt();

            return new CallLog(id, name, contactId, missed, startedAt, endedAt, messageCount);
        }
        @Override
        public CallLog[] newArray(int size) {
            return new CallLog[0];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id == null ? -1 : id);
        dest.writeString(name);
        dest.writeString(contactId);
        dest.writeByte(missed == null ? (byte)-1 : EyePhoneTypeConverters.toByte(missed));
        dest.writeLong(this.startedAt == null ? -1 : EyePhoneTypeConverters.toLong(this.startedAt));
        dest.writeLong(this.endedAt == null ? -1 : EyePhoneTypeConverters.toLong(this.endedAt));
        dest.writeInt(this.messageCount);
    }

}
