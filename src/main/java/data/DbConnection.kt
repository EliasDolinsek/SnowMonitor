package data

import kotlin.jvm.Synchronized
import kotlin.Throws
import java.sql.SQLException
import data.DbConnection
import java.lang.ClassNotFoundException
import java.sql.Connection
import java.sql.DriverManager

object DbConnection {
    // Defaults
    private const val DEFAULT_USER = "root"
    private const val DEFAULT_PASSWORD = "my-secret-pw"
    private const val DEFAULT_SERVER = "localhost" // IP-address
    private const val DEFAULT_PORT = "3306" // port number
    private const val DEFAULT_SCHEMA = "snow_monitor" // schema name
    private var connection // database connection
            : Connection? = null

    @Synchronized
    @Throws(SQLException::class)
    fun getConnection(): Connection {
        if (connection == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver")
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            }

            // connect to the database
            val url = String.format("%s:%s/%s", DEFAULT_SERVER, DEFAULT_PORT, DEFAULT_SCHEMA)
            val fullURL = String.format("jdbc:mysql://%s?useSSL=false", url)
            connection = DriverManager.getConnection(fullURL, DEFAULT_USER, DEFAULT_PASSWORD)
        }

        return connection!!
    }

    @Throws(SQLException::class)
    fun close() {
        if (connection != null) {
            connection!!.close()
            connection = null
        }
    }
}