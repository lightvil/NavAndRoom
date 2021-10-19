package com.cydinfo.roomandnav.models

sealed  class CallLogListItem(val displayString: String?) {
    data class Item(val callLog : CallLog) : CallLogListItem(callLog.name)
    data class Separator(private val dateTime : String) : CallLogListItem(dateTime)
}