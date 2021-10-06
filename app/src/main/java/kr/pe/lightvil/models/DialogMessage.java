package kr.pe.lightvil.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.TypeConverter;

import java.sql.Date;
import java.sql.Timestamp;

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
public class DialogMessage {
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
                    for(MESSAGE_TYPE mt : MESSAGE_TYPE.values()) {
                        if (mt.getCode() == value) return mt;
                    }
                }
                return null;
            }
        }

    }

    @ColumnInfo(name="CALL_LOG_ID") @NonNull
    Long callLogId;
    @ColumnInfo(name="SEQ") @NonNull
    Integer  seq;
    @ColumnInfo(name="MESSAGE_TYPE")
    MESSAGE_TYPE     messageType;
    @ColumnInfo(name="CREATED_AT")
    Timestamp createdAt;
    @ColumnInfo(name="MESSAGE")
    String message;

    public DialogMessage(Long callLogId, Integer seq, MESSAGE_TYPE messageType, Timestamp createdAt, String message) {
        this.callLogId   = callLogId;
        this.seq         = seq;
        this.messageType = messageType;
        this.createdAt   = createdAt;
        this.message     = message;
    }

    public static DialogMessage create(Long callLogId, Integer seq, MESSAGE_TYPE messageType, Timestamp createdAt, String message) {
        return new DialogMessage(callLogId, seq, messageType, createdAt, message);
    }

    public void setCallLogId(Long callLogId) {
        this.callLogId = callLogId;
    }

    public Long getCallLogId() {
        return this.callLogId;
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
}
