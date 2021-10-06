package kr.pe.lightvil.fragments;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Map;

import kr.pe.lightvil.R;
import kr.pe.lightvil.databinding.FragmentFirstTabBinding;
import kr.pe.lightvil.models.CallLog;
import kr.pe.lightvil.models.DialogMessage;

public class FirstTabFragment extends Fragment {

    private FirstTabViewModel mViewModel;
    private FragmentFirstTabBinding binding;
    public static FirstTabFragment newInstance() {
        return new FirstTabFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFirstTabBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mViewModel = new ViewModelProvider(this).get(FirstTabViewModel.class);
        // TODO: Use the ViewModel
        mViewModel.callLog.observe(getViewLifecycleOwner(), new Observer<Map<CallLog, List<DialogMessage>>>() {
            @Override
            public void onChanged(Map<CallLog, List<DialogMessage>> callLogListMap) {
                if (callLogListMap.size() > 0) {
                    for(Map.Entry<CallLog, List<DialogMessage>> entry : callLogListMap.entrySet()) {
                        CallLog key = entry.getKey();
                        List<DialogMessage> messages = entry.getValue();
                        binding.textViewRecipient.setText("대와:");
                        //binding.recyclerViewDialog.addView();
                        RecyclerView.Adapter adapter = binding.recyclerViewDialog.getAdapter();
                        if (adapter == null) {
                        } else {
                        }
                    }
                }
            }
        });

        return root;
    }

}