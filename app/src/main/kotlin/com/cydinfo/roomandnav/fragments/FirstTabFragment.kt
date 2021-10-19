package com.cydinfo.roomandnav.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import com.cydinfo.roomandnav.databinding.FragmentFirstTabBinding
import com.cydinfo.roomandnav.R

class FirstTabFragment : Fragment() {
    private val LOG_TAG = "FirstTabFragment"

    private var mViewModel: CallLogViewModel? = null
    lateinit var binding: FragmentFirstTabBinding;

    fun newInstance(): FirstTabFragment? {
        return FirstTabFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.i(LOG_TAG, "onCreateView()")
        binding = FragmentFirstTabBinding.inflate(inflater, container, false)
        mViewModel = ViewModelProvider(this).get(CallLogViewModel::class.java)

        val adapter = CallLogAdapter()
        binding.recyclerViewDialog.adapter = adapter
        lifecycleScope.launch {
            mViewModel?.getCallLogs("")?.collectLatest { adapter.submitData(it)}
        }

        return binding.root
    }
}