package kr.pe.lightvil.repositories;
import android.content.Context;

import java.util.List;
import java.util.Map;

import kr.pe.lightvil.daos.CallLogDAO;
import kr.pe.lightvil.models.CallLog;
import kr.pe.lightvil.models.DialogMessage;
import kr.pe.lightvil.models.EyePhoneDatabase;

public class CallLogRepository {
    private CallLogDAO callLogDAO;


    public CallLogRepository(Context context) {
        this.callLogDAO = EyePhoneDatabase.getDatabase(context).getCallLogDAO();
    }

    public int getLogCount() {
        return this.callLogDAO.getLogCount();
    }

    public List<CallLog> findAllCallLogs() {
        return callLogDAO.findAll();
    }

    public CallLog findCallLogById(Long callLogId) {
        return callLogDAO.findById(callLogId);
    }

    public Map<CallLog, List<DialogMessage>> findCallLogWithMessagesById(Long callLogId) {
        return callLogDAO.findWithMessagesById(callLogId);
    }

    public List<DialogMessage> findMessagesById(Long callLogId) {
       return callLogDAO.findMessagesByCallLogId(callLogId);
    }

    public void insert(CallLog callLog, List<DialogMessage> messages) {
        EyePhoneDatabase.executeWrite(() -> {
            long newId = callLogDAO.insertCallLogWithMessages(callLog, messages);
            callLog.setId(newId);
        });
    }

    public void insert(CallLog callLog) {
        EyePhoneDatabase.executeWrite(() -> {
            long newId = callLogDAO.insertCallLog(callLog);
            callLog.setId(newId);
        });
    }
}
