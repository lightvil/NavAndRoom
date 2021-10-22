package com.cydinfo.roomandnav.fragments

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import com.cydinfo.roomandnav.models.CallLog
import com.cydinfo.roomandnav.models.CallLogMessage
import com.cydinfo.roomandnav.repositories.CallLogRepository

class CallLogViewModel(application: Application) : AndroidViewModel(application) {
    private val LOG_TAG = "CallLogViewModel"

    var repository: CallLogRepository? = CallLogRepository.getInstance(application)
    private var callLogs: Flow<PagingData<CallLog>>? = null

    fun getCallLogsWithPaing(query : String): Flow<PagingData<CallLog>>? {
        Log.i(LOG_TAG, "getCallLogs(): repository is null? ==> " + (repository == null))
        Log.i(LOG_TAG, "getCallLogs(): query=$query")
        callLogs = repository?.getCallLogsWithPaging(query = query)
        return callLogs
    }

    fun getMessagesByCallLogId(callLogId : Long) : List<CallLogMessage>? {
        return repository?.getMessageByCallLogId(callLogId)
    }

    suspend fun insert(callLog : CallLog, messages : List<CallLogMessage>) {
        Log.i(LOG_TAG, "insert(): repository is null? ==> " + (repository == null))
        val newId = repository?.insert(callLog, messages)
        Log.i(LOG_TAG, "insert(): newId: " + newId + ", callLog.id: " + callLog.id)
    }
}