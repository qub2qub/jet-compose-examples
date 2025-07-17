package com.example.jetlagged.data

import com.example.jetlagged.sleep.SleepGraphData

data class JetLaggedHomeScreenState(
    val sleepGraphData: SleepGraphData = sleepData,
    val wellnessData: WellnessData = WellnessData(10, 4, 5),
    val heartRateData: HeartRateOverallData = HeartRateOverallData(),
)

data class WellnessData(val snoring: Int, val coughing: Int, val respiration: Int)

data class HeartRateOverallData(val averageBpm: Int = 65, val listData: List<HeartRateData> = heartRateGraphData)
