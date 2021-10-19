package com.cydinfo.roomandnav.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cydinfo.roomandnav.R
import com.cydinfo.roomandnav.models.CallLogListItem

class CallLogViewHolder(parent : ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(
                R.layout.fragment_first_tab, parent,false
        )
) {
    private val LOG_TAG = "CallLogViewHolder"

    private val nameView = itemView.findViewById<RecyclerView>(R.id.recycler_view_dialog)

    fun bindTo(item : CallLogListItem) {
        if (item is CallLogListItem.Item) {

        } else {

        }
    }
}