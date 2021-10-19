package com.cydinfo.roomandnav.models

import androidx.paging.PagingSource
import androidx.paging.PagingSource.LoadResult.Page
import androidx.paging.PagingState
import com.cydinfo.roomandnav.daos.CallLogDao
import java.lang.Exception

class CallLogPagingSource(private val dao : CallLogDao, private val query : String) : PagingSource<Int, CallLog>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CallLog> {
        val page = params.key ?: 1
        val pageSize = params.loadSize
        return try {
            val items = dao.getCallLogs(page, pageSize)
            Page(
                    data = items,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (items.isEmpty()) null else page + (params.loadSize / 10)
            )
        }catch (e : Exception) {
            return LoadResult.Error(e);
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CallLog>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                    ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}