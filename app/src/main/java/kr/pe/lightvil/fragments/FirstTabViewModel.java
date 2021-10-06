package kr.pe.lightvil.fragments;

import android.app.Application;
import android.app.Dialog;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.Map;

import kr.pe.lightvil.models.CallLog;
import kr.pe.lightvil.models.DialogMessage;

public class FirstTabViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel
    LiveData<Map<CallLog, List<DialogMessage>>> callLog = new LiveData<Map<CallLog, List<DialogMessage>>>() {
    };
    public FirstTabViewModel(Application application) {
        super(application);
    }

    public LiveData<Map<CallLog, List<DialogMessage>>> getCallLog() {
        return callLog;
    }
}