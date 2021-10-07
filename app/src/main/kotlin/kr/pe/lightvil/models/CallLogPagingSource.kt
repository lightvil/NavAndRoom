package kr.pe.lightvil.models

import androidx.paging.PagingSource
import androidx.paging.PagingState

class CallLogPagingSource(val query : String) : PagingSource<Int, CallLog>() {
    override fun getRefreshKey(state: PagingState<Int, CallLog>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CallLog> {
        TODO("Not yet implemented")
    }
}