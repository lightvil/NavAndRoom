package com.cydinfo.utils

import java.sql.Timestamp
import java.text.SimpleDateFormat

fun Timestamp.format(format : String = "yyyy/MM/dd HH:mm:ss.SSS") : String {
    val sd = SimpleDateFormat(format)
    return sd.format(this);
}
