package com.cydinfo.roomandnav.fragments

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.cydinfo.roomandnav.R
import com.cydinfo.roomandnav.models.CallLog

class CallLogAdapter : PagingDataAdapter<CallLog, CallLogAdapter.CallLogViewHolder>(CALL_LOG_DIFF) {
    private val LOG_TAG = "CallLogAdapter"

    object CALL_LOG_DIFF : DiffUtil.ItemCallback<CallLog>() {
        override fun areItemsTheSame(oldItem: CallLog, newItem: CallLog): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CallLog, newItem: CallLog): Boolean {
            return oldItem.id == newItem.id
        }
    }

    class CallLogViewHolder(parent : ViewGroup) : RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                    R.layout.fragment_first_tab, parent,false
            )
    ) {
        private val LOG_TAG = "CallLogViewHolder"

        private val view = itemView.findViewById<RecyclerView>(R.id.recycler_view_dialog)

        fun bindTo(item : CallLog?) {
            Log.i(LOG_TAG, "bindTo():" + (item?.toString() ?: ""))
//            if (item is CallLog) {
//            } else {
//            }
        }
    }
    override fun onBindViewHolder(holder: CallLogViewHolder, position: Int) {
        getItem(position)?.let{ with(holder) { bindTo(it) } }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CallLogViewHolder {
        return CallLogViewHolder(parent)
    }
}