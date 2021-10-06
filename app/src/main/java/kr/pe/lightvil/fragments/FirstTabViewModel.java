package kr.pe.lightvil.fragments;

import android.app.Application;
import android.app.Dialog;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import kr.pe.lightvil.models.CallLog;
import kr.pe.lightvil.models.DialogMessage;
import kr.pe.lightvil.models.EyePhoneDatabase;
import kr.pe.lightvil.repositories.CallLogRepository;

public class FirstTabViewModel extends AndroidViewModel {
    private static final String LOG_TAG = "FirstTabViewModel";
    // TODO: Implement the ViewModel
    LiveData<Map<CallLog, List<DialogMessage>>> callLog = new LiveData<Map<CallLog, List<DialogMessage>>>() {
    };

    CallLogRepository repository;

    public FirstTabViewModel(Application application) {
        super(application);
        repository = new CallLogRepository(application);


        int count = repository.getLogCount();
        Log.i(LOG_TAG, "LOG COUNT=" + count);

        CallLog log = CallLog.create(
                null,
                "수신자",
                "-1",
                false,
                new Timestamp(Calendar.getInstance().getTimeInMillis()),
                new Timestamp(Calendar.getInstance().getTimeInMillis()),
                1
                );
        List<DialogMessage> messages = new ArrayList<>();
//        public static DialogMessage create(
//                Long callLogId, Integer seq, DialogMessage.MESSAGE_TYPE messageType, Timestamp createdAt, String message
//        ) {

        messages.add(DialogMessage.create(
                log.getId(),
                1,
                DialogMessage.MESSAGE_TYPE.RECEIVED,
                new Timestamp(Calendar.getInstance().getTimeInMillis()),
                "메시지"
        ));
    }

    public LiveData<Map<CallLog, List<DialogMessage>>> getCallLog() {
        return callLog;
    }
}