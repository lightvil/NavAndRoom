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
    private val LOG_TAG = "CallLogRepository"

    companion object {
        private val LOG_TAG = "CallLogRepository.Companion"

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
        return Pager(
                config = PagingConfig(pageSize = 10),
                pagingSourceFactory = { CallLogPagingSource(dao, query) }
        ).flow
    }

    fun getMessageByCallLogId(callLogId: Long) : Flow<PagingData<CallLogMessage>> {
        return Pager(
                config = PagingConfig(pageSize = 10),
                pagingSourceFactory = { CallLogMessagePagingSource(dao, callLogId) }
        ).flow
    }

    suspend fun insert(callLog: CallLog) : Long {
        val newId: Long = dao.insertCallLog(callLog)
        callLog.id = newId
        return newId
    }

    suspend fun insert(callLog: CallLog, messages : List<CallLogMessage>) : Long {
        return dao.insertCallLogWithMessages(callLog, messages)!!
    }

    suspend fun delete(callLog: CallLog) {
        dao.deleteCallLogAndMessages(callLog)
    }
}