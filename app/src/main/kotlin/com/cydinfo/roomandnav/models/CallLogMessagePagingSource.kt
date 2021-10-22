package com.cydinfo.roomandnav.models

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.cydinfo.roomandnav.daos.CallLogDao
import java.lang.Exception

class CallLogMessagePagingSource(private val dao : CallLogDao, val callLogId : Long) : PagingSource<Int, CallLogMessage>() {
    private val LOG_TAG = "CallLogAdapter"

    override fun getRefreshKey(state: PagingState<Int, CallLogMessage>): Int? {
        val refreshKey = state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                    ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
        Log.i(LOG_TAG, "getRefreshKey(): ancfhorPosition=" + state.anchorPosition+ ", refreshKey=" + refreshKey)
        return refreshKey
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CallLogMessage> {
        val page = params.key ?: 1
        val pageSize = params.loadSize
        return try {
            val items = dao.getMessagesByCallLogIdWithPaging(callLogId, page, pageSize)
            Log.i(LOG_TAG, "load(): items.size=" + items.size)
            for (item in items) {
                Log.i(LOG_TAG, "\titem==>$item")
            }
            LoadResult.Page(
                    data = items,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (items.isEmpty()) null else page + (params.loadSize / 10)
            )
        }catch (e : Exception) {
            return LoadResult.Error(e);
        }
    }
}