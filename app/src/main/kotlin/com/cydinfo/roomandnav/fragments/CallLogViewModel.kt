package com.cydinfo.roomandnav.fragments

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import com.cydinfo.roomandnav.models.CallLog
import com.cydinfo.roomandnav.repositories.CallLogRepository

class CallLogViewModel(application: Application) : AndroidViewModel(application) {
    private val LOG_TAG = "CallLogViewModel"

    var repository: CallLogRepository? = CallLogRepository.getInstance(application)
    private val callLogs: Flow<PagingData<CallLog>>? = null

    fun getCallLogs(query : String): Flow<PagingData<CallLog>>? {
        Log.i(LOG_TAG, "getCallLogs(): " + query)
        return repository?.getCallLogs(query = query)
    }
}