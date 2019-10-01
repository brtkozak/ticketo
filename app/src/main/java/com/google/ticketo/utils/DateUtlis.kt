package com.google.ticketo.utils

import android.content.Context
import com.google.ticketo.R
import java.util.*

object DateUtlis {

    val minutesInWeek = 10080
    val minutesInDay = 1440
    val minutesInHour = 60

    fun getPeriod(date : Date, context : Context) : String{
        val difference = Date().time - date.time
        val minutesDifference = difference / (60 * 1000) % 60

        if( minutesDifference/ minutesInWeek > 0 )
            return "${(minutesDifference/ minutesInWeek)} ${context.getString(R.string.week)}"
        else if(minutesDifference/minutesInDay > 0)
            return "${minutesDifference/ minutesInDay} ${context.getString(R.string.days)}"
        else if(minutesDifference/minutesInHour > 0)
            return "${minutesDifference/ minutesInHour} ${context.getString(R.string.hours)}"
        else if(minutesDifference<1)
            return context.getString(R.string.just_now)
        else
            return "$minutesDifference ${context.getString(R.string.minutes)}"
    }
}