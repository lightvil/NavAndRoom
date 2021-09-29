package kr.pe.lightvil.models;

import androidx.room.TypeConverter;

import java.sql.Date;
import java.sql.Timestamp;

public class EyePhoneTypeConverters {
    //
    // Timestamp <==> Long
    //
    @TypeConverter
    public static Timestamp toTimestamp(Long value) {
        return (value == null) ? null : new Timestamp(value);
    }

    @TypeConverter
    public static Long toLong(Timestamp value) {
        return (value == null) ? null : value.getTime();
    }

    //
    // Date <==> Long
    //
    @TypeConverter
    public static Date toDate(Long value) {
        return (value == null) ? null : new Date(value);
    }

    @TypeConverter
    public static Long toLong(Date value) {
        return (value == null) ? null : value.getTime();
    }

    //
    //  Boolean <==> Integer
    //
    @TypeConverter
    public static Boolean toBoolean(Integer value) {
        return (value == null) ? null : (value == 0 ? Boolean.FALSE : Boolean.TRUE);
    }

    private static final Integer ZERO_INT = Integer.valueOf(0);
    private static final Integer ONE_INT  = Integer.valueOf(1);
    private static final Byte ZERO_BYTE   = Byte.valueOf((byte)0);
    private static final Byte ONE_BYTE    = Byte.valueOf((byte)1);
    @TypeConverter
    public static Integer toInteger(Boolean value) {
        return (value == null) ? null : (value ? ONE_INT : ZERO_INT);
    }
    @TypeConverter
    public static Boolean toBoolean(Byte value) {
        return (value == null) ? null : (value.intValue() == 0 ? Boolean.FALSE : Boolean.TRUE);
    }

    @TypeConverter
    public static Byte toByte(Boolean value) {
        return (value == null) ? null : (value ? ONE_BYTE : ZERO_BYTE);
    }

}
