package kr.pe.lightvil.models;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kr.pe.lightvil.daos.CallLogDAO;

@Database(entities = {CallLog.class, DialogMessage.class}, version = 1)
@TypeConverters({EyePhoneTypeConverters.class, DialogMessage.MESSAGE_TYPE.MESSAGE_TYPE_CONVERTER.class})
public abstract  class EyePhoneDatabase extends RoomDatabase {

    private static final int NUMBER_OF_THREADS = 4;
    private static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    public static void executeWrite(Runnable runnble) {
        databaseWriteExecutor.execute(runnble);
    }
    private static volatile EyePhoneDatabase INSTANCE;
    public static EyePhoneDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (EyePhoneDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            EyePhoneDatabase.class, "word_database")
                            .allowMainThreadQueries() // TODO 나중에 빼야 한다.
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract CallLogDAO callLogDAO();

    public CallLogDAO getCallLogDAO() {
        return this.callLogDAO();
    }
}
