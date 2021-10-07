package kr.pe.lightvil.repositories

import android.content.Context
import androidx.paging.PagingSource
import kr.pe.lightvil.daos.CallLogDao
import kr.pe.lightvil.models.*

class CallLogRepository(val context: Context)  {
    private val  dao : CallLogDao by lazy { EyePhoneDatabase.getInstance(context)!!.callLogDao() }

    fun getCallLogs(query : String) : CallLogPagingSource {
        return dao.getCallLogs(query)
    }

    fun getMessageByCallLogId(callLogId: Long, query: String) : CallLogMessagePagingSource {
        return dao.getMessagesByCallLogId(callLogId, query)
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