package com.example.jetlagged.sleep

import com.example.jetlagged.R
import java.time.Duration
import java.time.LocalDateTime

data class SleepGraphData(val sleepDayData: List<SleepDayData>) {
    val earliestStartHour: Int by lazy {
        sleepDayData.minOf { it.firstSleepStart.hour }
    }
    val latestEndHour: Int by lazy {
        sleepDayData.maxOf { it.lastSleepEnd.hour }
    }
}

data class SleepDayData(val startDate: LocalDateTime, val sleepPeriods: List<SleepPeriod>, val sleepScore: Int) {
    val firstSleepStart: LocalDateTime by lazy {
        sleepPeriods.sortedBy(SleepPeriod::startTime).first().startTime
    }
    val lastSleepEnd: LocalDateTime by lazy {
        sleepPeriods.sortedBy(SleepPeriod::startTime).last().endTime
    }
    val totalTimeInBed: Duration by lazy {
        Duration.between(firstSleepStart, lastSleepEnd)
    }

    val sleepScoreEmoji: String by lazy {
        when (sleepScore) {
            in 0..40 -> "😖"
            in 41..60 -> "😏"
            in 60..70 -> "😴"
            in 71..100 -> "😃"
            else -> "🤷‍"
        }
    }

    fun fractionOfTotalTime(sleepPeriod: SleepPeriod): Float {
        return sleepPeriod.duration.toMinutes() / totalTimeInBed.toMinutes().toFloat()
    }

    fun minutesAfterSleepStart(sleepPeriod: SleepPeriod): Long {
        return Duration.between(
            firstSleepStart,
            sleepPeriod.startTime,
        ).toMinutes()
    }
}

data class SleepPeriod(val startTime: LocalDateTime, val endTime: LocalDateTime, val type: SleepType) {

    val duration: Duration by lazy {
        Duration.between(startTime, endTime)
    }
}

enum class SleepType(val title: Int) {
    Awake(R.string.sleep_type_awake),
    REM(R.string.sleep_type_rem),
    Light(R.string.sleep_type_light),
    Deep(R.string.sleep_type_deep),
}
