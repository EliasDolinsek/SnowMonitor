package data

import domain.SnowMonitorEntry
import java.sql.Date
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement
import java.time.LocalDate
import java.util.ArrayList
import kotlin.Throws

object SnowMonitorEntryDAO {

    fun insert(entry: SnowMonitorEntry): Int {
        val sql = "INSERT INTO snow_entries (ski_resort, snow_depth, date) VALUES (?, ?, ?)"
        try {
            DbConnection.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS).use {
                it.setString(1, entry.skiResort)
                it.setInt(2, entry.snowDepth)
                it.setDate(3, entry.date.toSQLDate())
                it.executeUpdate()

                val resultSet = it.generatedKeys
                if (resultSet.next()) {
                    return resultSet.getInt(1)
                }
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }

        return -1
    }

    fun getAllEntries(): ArrayList<SnowMonitorEntry> {
        val entries = ArrayList<SnowMonitorEntry>()
        try {
            val connection = DbConnection.getConnection()
            val statement = connection.createStatement()
            val result = statement.executeQuery("SELECT * FROM snow_entries")
            while (result.next()) {
                entries.add(getEntryFromResultSet(result))
            }
        } catch (throwables: SQLException) {
            throwables.printStackTrace()
        }

        return entries
    }

    @Throws(SQLException::class)
    private fun getEntryFromResultSet(set: ResultSet): SnowMonitorEntry {
        return SnowMonitorEntry(
                id = set.getInt(1),
                skiResort = set.getString(2),
                snowDepth = set.getInt(3),
                date = set.getDate(4).toLocalDate()
        )
    }

    private fun LocalDate.toSQLDate(): Date = Date.valueOf(this)
}