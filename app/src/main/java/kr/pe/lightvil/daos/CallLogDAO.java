package kr.pe.lightvil.daos;

import android.telecom.Call;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;
import java.util.Map;

import kr.pe.lightvil.models.CallLog;
import kr.pe.lightvil.models.DialogMessage;

//
// LiveData<>를 사용하는 것은 아직 공부하지 않아서 모르겠다.
//
@Dao
public interface CallLogDAO {

    @Query("SELECT COUNT(*) FROM CALL_LOG")
    int getLogCount();

    @Query("SELECT * FROM CALL_LOG")
    List<CallLog> findAll();

    @Query("SELECT * FROM CALL_LOG WHERE ID = :id")
    CallLog findById(Long id);

    //
    // JOIN은 Map 형태로 받아온다.
    //  CallLog 하나에 DialogMessage 여러개의 형식
    //
    @Query("SELECT * FROM CALL_LOG JOIN MESSAGES ON CALL_LOG.ID == MESSAGES.CALL_LOG_ID WHERE CALL_LOG.ID = id")
    Map<CallLog, List<DialogMessage>> findWithMessagesById(Long id);

    @Query("SELECT * FROM MESSAGES WHERE CALL_LOG_ID = :callLogId ORDER BY SEQ")
    List<DialogMessage> findMessagesByCallLogId(Long callLogId);

    //
    // 개별 테이블의 인서트를 위한 것...
    //
    @Insert
    Long insertCallLog(CallLog log);
    @Insert
    void insertMessages(List<DialogMessage> messages);

    //
    // INSERT는 Transaction으로 묶어서 CallLog과 DialogMessage의 목록을 한꺼번에...
    //   대화(통화)가 완료되면 한꺼번에 넣을 수 있도록 할 수 있을 것 같다.
    //
    @Transaction
    default Long insertCallLogWithMessages(CallLog log, List<DialogMessage> messages) {
        Long callLogId = insertCallLog(log);
        if (messages != null && messages.size() > 0) {
            for(DialogMessage message : messages) {
                message.setCallLogId(callLogId);
            }
            insertMessages(messages);
            log.setMessageCount(messages.size());
        }
        return insertCallLog(log);
    }

    // UPDATE는 존재하지 않는다.

    //
    // 개별 테이블에서 삭제...
    //
    @Delete
    void delete(CallLog callLog);
    @Delete
    @Query("DELETE FROM MESSAGES WHERE CALL_LOG_ID = :callLogId")
    void deleteMessagesByCallLogId(Long callLogId);

    // 개별 대화 삭제는 없다.
    // CallLog 단위로 Transaction으로 묶어 삭제
    @Transaction
    default void deleteCallLogAndMessages(CallLog callLog) {
        if (callLog != null && callLog.getId() != null) {
            deleteMessagesByCallLogId(callLog.getId());
            delete(callLog);
        }
    }
}
