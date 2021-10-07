package kr.pe.lightvil.models

import androidx.paging.PagingSource
import androidx.paging.PagingState

class CallLogMessagePagingSource(val callLogId : Long, val query : String) : PagingSource<Int, CallLogMessage>() {
    override fun getRefreshKey(state: PagingState<Int, CallLogMessage>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CallLogMessage> {
        TODO("Not yet implemented")
    }
}