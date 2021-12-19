package domain

import java.time.LocalDate

data class SnowMonitorEntry(var id: Int = -1, var skiResort: String, var snowDepth: Int, var date: LocalDate)