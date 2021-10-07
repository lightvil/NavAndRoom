package kr.pe.lightvil.fragments;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.PagingData;

import java.util.List;

import kotlinx.coroutines.flow.Flow;
import kr.pe.lightvil.models.CallLog;
import kr.pe.lightvil.repositories.CallLogRepo;
import kr.pe.lightvil.repositories.CallLogRepository;

public class FirstTabViewModel extends AndroidViewModel {
    private static final String LOG_TAG = "FirstTabViewModel";
    // TODO: Implement the ViewModel
    CallLogRepository repository;

    private Flow<PagingData<CallLog>> callLogs = null;


    public FirstTabViewModel(Application application) {
        super(application);
        repository = new CallLogRepository(application);

        repository.
//        callLogDataSource = repository.callLogs();
//        int count = repository.getLogCount();
//        Log.i(LOG_TAG, "LOG COUNT=" + count);
//
//        CallLog log = CallLog.create(
//                null,
//                "수신자",
//                "-1",
//                false,
//                new Timestamp(Calendar.getInstance().getTimeInMillis()),
//                new Timestamp(Calendar.getInstance().getTimeInMillis()),
//                1
//                );
//        List<DialogMessage> messages = new ArrayList<>();
////        public static DialogMessage create(
////                Long callLogId, Integer seq, DialogMessage.MESSAGE_TYPE messageType, Timestamp createdAt, String message
////        ) {
//
//        messages.add(DialogMessage.create(
//                log.getId(),
//                1,
//                DialogMessage.MESSAGE_TYPE.RECEIVED,
//                new Timestamp(Calendar.getInstance().getTimeInMillis()),
//                "메시지"
//        ));
//        repository.insert(log, messages);
    }

    public LiveData<List<CallLog>> getCallLogs() {
        return callLogs;
    }
}