package com.cydinfo.roomandnav.models

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingSource.LoadResult.Page
import androidx.paging.PagingState
import com.cydinfo.roomandnav.daos.CallLogDao
import java.lang.Exception

class CallLogPagingSource(private val dao : CallLogDao, private val query : String) : PagingSource<Int, CallLog>() {
    private val LOG_TAG = "CallLogPagingSource"

    override fun getRefreshKey(state: PagingState<Int, CallLog>): Int? {
        val refreshKey = state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
        Log.i(LOG_TAG, "getRefreshKey(): refreshKey=$refreshKey")
        return refreshKey
    }


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CallLog> {
        Log.i(LOG_TAG, "load(): params.key=" + params.key+ ", params.loadSize=" + params.loadSize)

        // 첫 페이지는 1
        val page = params.key ?: 1
        val pageSize = params.loadSize
        return try {
            Log.i(LOG_TAG, "load(): CALL DAO.getCallLogs(page=$page, pageSize=$pageSize)")
            val items = dao.getCallLogsWithPaging(page, pageSize)
            Log.i(LOG_TAG, "load(): items.size=" + items.size)
            for (item in items) {
                Log.i(LOG_TAG, "\titem==>$item")
            }
            Page(
                    data = items,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (items.isEmpty()) null else page + (params.loadSize / 10)
            )
        }catch (e : Exception) {
            return LoadResult.Error(e);
        }
    }

}