package kr.pe.lightvil.models;

import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.TypeConverter;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;

/*
  CREATE TABLE MESSAGES (
    CALL_LOG_ID  INTEGER,
    SEQ          INTEGER,
    MESSAGE_TYPE INTEGER
    CREATED_AT   INTEGER,
    MESSAGE      TEXT
    PRIMARY KEY(CALL_LOG_ID, SEQ)
  );
*/

@Entity(tableName = "MESSAGES", primaryKeys = {"CALL_LOG_ID", "SEQ"})
public class DialogMessage implements Parcelable {

    public enum MESSAGE_TYPE {
        RECEIVED(0), SENT(1), SYSTEM(2);
        private int code;

        MESSAGE_TYPE(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }

        public static class MESSAGE_TYPE_CONVERTER {
            @TypeConverter
            public static Integer toInteger(MESSAGE_TYPE value) {
                if (value == null) return null;
                else return value.getCode();
            }

            @TypeConverter
            public static MESSAGE_TYPE toMESSAGE_TYPE(Integer value) {
                if (value == null) return null;
                else {
                    for (MESSAGE_TYPE mt : MESSAGE_TYPE.values()) {
                        if (mt.getCode() == value) return mt;
                    }
                }
                return null;
            }
        }

    }

    @ColumnInfo(name = "CALL_LOG_ID")
    @NonNull
    Long callLogId;
    @ColumnInfo(name = "SEQ")
    @NonNull
    Integer seq;
    @ColumnInfo(name = "MESSAGE_TYPE")
    MESSAGE_TYPE messageType;

    @ColumnInfo(name = "CREATED_AT")
    Timestamp createdAt;
    @ColumnInfo(name = "MESSAGE")
    String message;

    public DialogMessage(Long callLogId, Integer seq, MESSAGE_TYPE messageType, Timestamp createdAt, String message) {
        this.callLogId = callLogId;
        this.seq = seq;
        this.messageType = messageType;
        this.createdAt = createdAt;
        this.message = message;
    }

    //
    // Getters
    //
    public Long getCallLogId() {
        return this.callLogId;
    }

    @NonNull
    public Integer getSeq() {
        return this.seq;
    }

    public String getMessage() {
        return this.message;
    }

    public Timestamp getCreatedAt() {
        return this.createdAt;
    }

    public MESSAGE_TYPE getMessageType() {
        return this.messageType;
    }

    //
    // Setters
    //
    public void setCallLogId(Long callLogId) {
        this.callLogId = callLogId;
    }

    public void setSeq(@NonNull Integer seq) {
        this.seq = seq;
    }

    public void setMessageType(MESSAGE_TYPE messageType) {
        this.messageType = messageType;
    }

    public void setCreatedAt(Timestamp newCreatedAt) {
        this.createdAt = newCreatedAt;
    }

    public void setMessage(String newMessage) {
        this.message = newMessage;
    }

    public static DialogMessage create(Long callLogId, Integer seq, MESSAGE_TYPE messageType, Timestamp createdAt, String message) {
        return new DialogMessage(callLogId, seq, messageType, createdAt, message);
    }

    //
    // For Parcelable interface
    //
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.getCallLogId());
        dest.writeLong(this.callLogId == null ? -1 : this.callLogId);
        dest.writeInt(this.seq == null ? -1 : this.seq);
        dest.writeInt(this.messageType.getCode());
        dest.writeLong(this.createdAt == null ? -1 : EyePhoneTypeConverters.toLong(this.createdAt));
        dest.writeString(this.message);
    }
    public static final Creator<DialogMessage> CREATOR = new Creator<DialogMessage>() {
        @Override
        public DialogMessage createFromParcel(Parcel source) {
            long l;
            byte b;
            int i;
            Long callLogId;
            Integer seq;
            MESSAGE_TYPE messageType;
            Timestamp createdAt;
            String message;

            l = source.readLong();
            callLogId = l == -1L ? null : l;
            i = source.readInt();
            seq = i == -1 ? null : i;
            i = source.readInt();
            messageType = i == -1 ? null : MESSAGE_TYPE.MESSAGE_TYPE_CONVERTER.toMESSAGE_TYPE(i);
            l = source.readLong();
            createdAt = l == -1 ? null : new Timestamp(l);
            message = source.readString();
            return DialogMessage.create(callLogId, seq, messageType, createdAt, message);
        }

        @Override
        public DialogMessage[] newArray(int size) {
            return new DialogMessage[0];
        }
    };

}