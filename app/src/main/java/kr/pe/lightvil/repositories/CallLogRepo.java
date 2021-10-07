package kr.pe.lightvil.repositories;
//import android.content.Context;
//
//import androidx.paging.Pager;
//import androidx.paging.PagingConfig;
//import androidx.paging.PagingData;
//import androidx.paging.PagingSource;
//
//import java.util.List;
//
//import kr.pe.lightvil.daos.CallLogDao;
//import kotlin.jvm.functions.Function0;
//import kotlinx.coroutines.flow.Flow;
//import kr.pe.lightvil.models.CallLog;
//import kr.pe.lightvil.models.CallLogMessage;
//import kr.pe.lightvil.models.EyePhoneDatabase;

public class CallLogRepo {
//    private CallLogDao dao;
//
//
//    public CallLogRepo(Context context) {
//        this.dao = EyePhoneDatabase.Companion.getInstance(context).callLogDao();
//    }
////
////    public int getLogCount() {
////        return this.callLogDAO.getLogCount();
////    }
////
////    public List<CallLog> findAllCallLogs() {
////        return callLogDAO.findAll();
////    }
////
////    public CallLog findCallLogById(Long callLogId) {
////        return callLogDAO.findById(callLogId);
////    }
////
////    public Map<CallLog, List<CallLogMessage>> findCallLogWithMessagesById(Long callLogId) {
////        return callLogDAO.findWithMessagesById(callLogId);
////    }
////
////    public List<CallLogMessage> findMessagesById(Long callLogId) {
////       return callLogDAO.findMessagesByCallLogId(callLogId);
////    }
//
//    public void insert(CallLog callLog, List<CallLogMessage> messages) {
//        EyePhoneDatabase.Companion.get
//        EyePhoneDatabase.executeWrite(() -> {
//            long newId = dao.insertCallLogWithMessages(callLog, messages)
//            callLog.setId(newId)
//        });
//    }
//
//    public void insert(CallLog callLog) {
//        EyePhoneDatabase.executeWrite(() -> {
//            long newId = callLogDAO.insertCallLog(callLog);
//            callLog.setId(newId);
//        });
//    }
//
//    public Flow<PagingData<CallLog>> callLogs() {
//        return new Pager<Integer, CallLog>(
//                new PagingConfig(10, 1),
//                (Function0<? extends PagingSource<Integer, CallLog>>) new CallLogPagingSource()
//        ).getFlow();
//    }
}
