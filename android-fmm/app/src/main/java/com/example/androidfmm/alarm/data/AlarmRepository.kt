package com.example.androidfmm.alarm.data

import androidx.lifecycle.LiveData
import com.example.androidfmm.alarm.AlarmItem

class AlarmRepository(private val alarmDao: AlarmDao){
    val readAllData: LiveData<List<AlarmItem>> = alarmDao.getAllAlarms()

    suspend fun addAlarm(alarmItem: AlarmItem) {
        alarmDao.addAlarm(alarmItem)
    }
}