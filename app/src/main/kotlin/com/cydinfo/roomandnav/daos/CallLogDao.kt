package com.cydinfo.roomandnav.daos

import androidx.room.*
import com.cydinfo.roomandnav.models.CallLog
import com.cydinfo.roomandnav.models.CallLogMessage

@Dao
interface CallLogDao {
    @Query("SELECT * FROM CALL_LOGS ORDER BY ID DESC LIMIT :pageSize OFFSET (:page - 1) * :pageSize")
    fun getCallLogsWithPaging(page : Int, pageSize : Int): List<CallLog>

    @Query("SELECT * FROM CALL_LOGS ORDER BY ID DESC")
    fun getCallLogs(): List<CallLog>

    @Query("SELECT * FROM MESSAGES WHERE CALL_LOG_ID = :callLogId ORDER BY CREATED_AT LIMIT :pageSize OFFSET :page * :pageSize")
    fun getMessagesByCallLogIdWithPaging(callLogId: Long, page : Int, pageSize : Int): List<CallLogMessage>

    @Query("SELECT * FROM MESSAGES WHERE CALL_LOG_ID = :callLogId ORDER BY CREATED_AT")
    fun getMessagesByCallLogId(callLogId: Long): List<CallLogMessage>

    //
    // 개별 테이블의 인서트를 위한 것...
    //
    @Transaction
    @Insert
    fun insertCallLog(log: CallLog?): Long

    @Transaction
    @Insert
    fun insertMessages(messages: List<CallLogMessage>?)

    //
    // INSERT는 Transaction으로 묶어서 CallLog과 DialogMessage의 목록을 한꺼번에...
    //   대화(통화)가 완료되면 한꺼번에 넣을 수 있도록 할 수 있을 것 같다.
    //
    @Transaction
    fun insertCallLogWithMessages(log: CallLog, messages: List<CallLogMessage>?): Long {
        if (messages != null) {
            log.messageCount = messages.size
        } else {
            log.messageCount = 0;
        }
        val callLogId = insertCallLog(log)
        log.id = callLogId
        if (messages != null && messages.isNotEmpty()) {
            for(message in messages) {
                message.callLogId = callLogId
            }
            insertMessages(messages)
        }
        return callLogId;
    }

    // UPDATE는 존재하지 않는다.
    //
    // 개별 테이블에서 삭제...
    //
    @Transaction
    @Delete
    fun delete(callLog: CallLog?)

    //
    // SEE : https://stackoverflow.com/questions/47538857/android-room-delete-with-parameters
    //
    @Query("DELETE FROM MESSAGES WHERE CALL_LOG_ID = :callLogId")
    fun deleteMessagesByCallLogId(callLogId: Long?): Int

    // 개별 대화 삭제는 없다.
    // CallLog 단위로 Transaction으로 묶어 삭제
    @Transaction
    fun deleteCallLogAndMessages(callLog: CallLog?) {
        if (callLog?.id != null) {
            deleteMessagesByCallLogId(callLog.id)
            delete(callLog)
        }
    }
}