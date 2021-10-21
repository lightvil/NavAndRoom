package com.cydinfo.roomandnav.models

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cydinfo.roomandnav.daos.CallLogDao
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Database(entities = [CallLog::class, CallLogMessage::class], version = 1)

@TypeConverters(EyePhoneTypeConverters::class, CallLogMessage.MESSAGE_TYPE.MESSAGE_TYPE_CONVERTER::class)
abstract class EyePhoneDatabase : RoomDatabase() {

    abstract fun callLogDao() : CallLogDao
    companion object {
        const val NUMBER_OF_THREADS = 4
        const val LOG_TAG = "CallLogAdapter"
        private var instance : EyePhoneDatabase? = null
        fun getInstance(context : Context) : EyePhoneDatabase? {
            Log.i(LOG_TAG, "getInstance()")
            if (instance == null) {
                synchronized(EyePhoneDatabase::class) {
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            EyePhoneDatabase::class.java,
                            "eyephone"
                    ).allowMainThreadQueries().build();
                    return instance;
                }
            } else {
                return instance;
            }
        }
    }

    private val databaseWriteExecutor: ExecutorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS)
    open fun executeWrite(runnble: Runnable?) {
        databaseWriteExecutor.execute(runnble)
    }
}