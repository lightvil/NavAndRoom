package kr.pe.lightvil.models

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kr.pe.lightvil.daos.CallLogDao
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Database(entities = [CallLog::class, CallLogMessage::class], version = 1)
@TypeConverters(EyePhoneTypeConverters::class, CallLogMessage.MESSAGE_TYPE.MESSAGE_TYPE_CONVERTER::class)
abstract class EyePhoneDatabase : RoomDatabase() {
    abstract fun callLogDao() : CallLogDao
    companion object {
        private var instance : EyePhoneDatabase? = null
        fun getInstance(context : Context) : EyePhoneDatabase? {
            if (instance == null) {
                synchronized(EyePhoneDatabase::class) {
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            EyePhoneDatabase::class.java,
                            "eyephone"
                    ).build();
                    return instance;
                }
            } else {
                return instance;
            }
        }
    }

    private val NUMBER_OF_THREADS = 4
    private val databaseWriteExecutor: ExecutorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS)
    open fun executeWrite(runnble: Runnable?) {
        databaseWriteExecutor.execute(runnble)
    }
}