package kr.pe.lightvil.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kr.pe.lightvil.R;

public class FirstTabFragment extends Fragment {

    private FirstTabViewModel mViewModel;

    public static FirstTabFragment newInstance() {
        return new FirstTabFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(FirstTabViewModel.class);
        // TODO: Use the ViewModel
        return inflater.inflate(R.layout.fragment_first_tab, container, false);
    }

}