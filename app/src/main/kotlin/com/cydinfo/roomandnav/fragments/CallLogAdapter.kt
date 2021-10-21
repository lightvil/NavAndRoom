package com.cydinfo.roomandnav.fragments

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.cydinfo.roomandnav.R
import com.cydinfo.roomandnav.models.CallLog
import com.cydinfo.roomandnav.models.EyePhoneDatabase.Companion.LOG_TAG

class CallLogAdapter : PagingDataAdapter<CallLog, CallLogAdapter.CallLogViewHolder>(CALL_LOG_DIFF) {
    companion object {
        const val LOG_TAG = "CallLogAdapter"
    }

    object CALL_LOG_DIFF : DiffUtil.ItemCallback<CallLog>() {
        override fun areItemsTheSame(oldItem: CallLog, newItem: CallLog): Boolean {
            Log.i(LOG_TAG, "areItemsTheSame(): $oldItem :: $newItem")
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CallLog, newItem: CallLog): Boolean {
            Log.i(LOG_TAG, "areContentsTheSame(): $oldItem :: $newItem")
            return oldItem.id == newItem.id && oldItem.name.equals(newItem.name) && oldItem.startedAt.equals(newItem.startedAt)
        }
    }

    class CallLogViewHolder(parent : ViewGroup) : RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                    R.layout.call_log_item, parent,false
            )
    ) {
        private val LOG_TAG = "CallLogViewHolder"

        private val view = itemView.findViewById<TextView>(R.id.call_log_message)

        fun bindTo(item : CallLog) {
            Log.i(LOG_TAG, "bindTo():" + (item?.toString() ?: ""))
            view.text = if (item.name == null) "" else item.name + "::" + item.id
        }
    }

    override fun onBindViewHolder(holder: CallLogViewHolder, position: Int) {
        val itemCount = super.getItemCount()
        Log.i(Companion.LOG_TAG, "onBindViewHolder(): position=$position, itemCount=$itemCount")
        getItem(position)?.let{ with(holder) { bindTo(it) } }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CallLogViewHolder {
        return CallLogViewHolder(parent)
    }

    override fun getItemCount(): Int {
        val itemCount = super.getItemCount()
        Log.i(Companion.LOG_TAG, "getItemCount(): super.getItemCount()=$itemCount" )
        return itemCount
    }

}