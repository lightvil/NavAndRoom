package com.cydinfo.roomandnav.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import com.cydinfo.roomandnav.databinding.FragmentFirstTabBinding
import com.cydinfo.roomandnav.R
import com.cydinfo.roomandnav.models.CallLog
import com.cydinfo.roomandnav.models.CallLogMessage

class FirstTabFragment : Fragment() {
    private val LOG_TAG = "FirstTabFragment"

    private var mViewModel: CallLogViewModel? = null
    private lateinit var binding: FragmentFirstTabBinding;

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

        binding.recyclerViewDialog.layoutManager = LinearLayoutManager(this.context)
        val adapter = CallLogAdapter()
        binding.recyclerViewDialog.adapter = adapter
        // 조회시작...
        lifecycleScope.launch {
            Log.i(LOG_TAG, "RETREIVING CALL LOGS")
            mViewModel?.getCallLogs("")?.collectLatest {
                Log.i(LOG_TAG, "getCallLogs() item==> $it")
                adapter.submitData(it)
            }
        }

        lifecycleScope.launch {
            Log.i(LOG_TAG, "INSERTING A NEW CALL LOG")
            mViewModel?.insert(CallLog.missedCall("최상환", null, null), ArrayList<CallLogMessage>())
        }

        return binding.root
    }
}