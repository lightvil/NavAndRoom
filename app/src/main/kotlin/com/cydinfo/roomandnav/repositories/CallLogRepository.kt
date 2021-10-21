package com.cydinfo.roomandnav.repositories

import android.content.Context
import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.cydinfo.roomandnav.models.*
import kotlinx.coroutines.flow.Flow
import com.cydinfo.roomandnav.daos.CallLogDao

class CallLogRepository(val context: Context)  {

    companion object {
        const val LOG_TAG = "CallLogRepository"

        private var instance : CallLogRepository? = null
        fun getInstance(context : Context) : CallLogRepository? {
            Log.i(LOG_TAG, "getInstance()")

            if (instance == null) {
                synchronized(CallLogRepository::class) {
                    instance = CallLogRepository(context);
                    return instance;
                }
            } else {
                return instance;
            }
        }
    }

    private val  dao : CallLogDao by lazy { EyePhoneDatabase.getInstance(context)!!.callLogDao() }

    fun getCallLogs(query : String) : Flow<PagingData<CallLog>> {
        Log.i(LOG_TAG, "getCallLogs(): query=$query")
        return Pager(
                config = PagingConfig(pageSize = 10),
                pagingSourceFactory = { CallLogPagingSource(dao, query) }
        ).flow
    }

    fun getMessageByCallLogId(callLogId: Long) : Flow<PagingData<CallLogMessage>> {
        Log.i(LOG_TAG, "getMessageByCallLogId(): query=$callLogId")
        return Pager(
                config = PagingConfig(pageSize = 10),
                pagingSourceFactory = { CallLogMessagePagingSource(dao, callLogId) }
        ).flow
    }

    suspend fun insert(callLog: CallLog) : Long {
        Log.i(LOG_TAG, "insert(): callLog=$callLog")
        val newId = dao.insertCallLog(callLog)
        callLog.id = newId
        return newId
    }

    suspend fun insert(callLog: CallLog, messages : List<CallLogMessage>?) : Long {
        Log.i(LOG_TAG, "insert(): callLog=$callLog")
        val newId = dao.insertCallLogWithMessages(callLog, messages)
        callLog.id = newId
        return newId
    }

    suspend fun delete(callLog: CallLog) {
        Log.i(LOG_TAG, "delete(): callLog=$callLog")
        dao.deleteCallLogAndMessages(callLog)
    }
}