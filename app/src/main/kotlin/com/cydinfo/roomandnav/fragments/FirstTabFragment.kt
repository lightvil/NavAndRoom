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
            mViewModel?.getCallLogsWithPaing("")?.collectLatest {
                Log.i(LOG_TAG, "getCallLogs() item==> $it")
                adapter.submitData(it)
            }
        }

        lifecycleScope.launch {
            Log.i(LOG_TAG, "INSERTING A NEW CALL LOG")
            var messages : List<CallLogMessage> = ArrayList<CallLogMessage>()
            //
            messages = messages + CallLogMessage.sentMessage("안녕하세요?")
            messages = messages + CallLogMessage.receivedMessage("네, 안녕하세요?")
            messages = messages + CallLogMessage.sentMessage("오늘은 날씨가 춥네요.")
            messages = messages + CallLogMessage.sentMessage("내일 오전 10시에 스타벅스에서 보기로 한 거 맞죠?")
            messages = messages + CallLogMessage.receivedMessage("네. 옷 든든하게 입고 나오셔야 하겠네요.")
            messages = messages + CallLogMessage.sentMessage("그럼, 내일 오전 10시에 뵐께요.")
            for(message in messages) {
                Log.i(LOG_TAG, message.toString())
            }
            mViewModel?.insert(CallLog.missedCall("최상환", null, null), messages)
        }

        return binding.root
    }
}