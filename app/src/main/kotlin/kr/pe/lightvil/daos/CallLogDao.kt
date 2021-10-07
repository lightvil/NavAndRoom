package kr.pe.lightvil.daos

import androidx.room.*
import kr.pe.lightvil.models.CallLog
import kr.pe.lightvil.models.CallLogMessage
import kr.pe.lightvil.models.CallLogMessagePagingSource
import kr.pe.lightvil.models.CallLogPagingSource

@Dao
interface CallLogDao {
    @Query("SELECT * FROM CALL_LOG ORDER BY id DESC ")
    fun getCallLogs(query : String): CallLogPagingSource

    @Query("SELECT * FROM MESSAGES WHERE CALL_LOG_ID = :callLogId ORDER BY SEQ")
    fun getMessagesByCallLogId(callLogId: Long, query : String): CallLogMessagePagingSource

    //
    // 개별 테이블의 인서트를 위한 것...
    //
    @Insert
    fun insertCallLog(log: CallLog?): Long

    @Insert
    fun insertMessages(messages: List<CallLogMessage>?)

    //
    // INSERT는 Transaction으로 묶어서 CallLog과 DialogMessage의 목록을 한꺼번에...
    //   대화(통화)가 완료되면 한꺼번에 넣을 수 있도록 할 수 있을 것 같다.
    //
    @Transaction
    fun insertCallLogWithMessages(log: CallLog, messages: List<CallLogMessage>?): Long? {
        if (messages != null) {
            log.messageCount = messages.size
        } else {
            log.messageCount = 0;
        }
        val callLogId = insertCallLog(log)
        if (messages != null && messages.isNotEmpty()) {
            for (i: Int in 1..messages.size) {
                messages[i].callLogId = callLogId;
                messages[i].seq = i;
            }
            insertMessages(messages)
        }
        return callLogId;
    }

    // UPDATE는 존재하지 않는다.
    //
    // 개별 테이블에서 삭제...
    //
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