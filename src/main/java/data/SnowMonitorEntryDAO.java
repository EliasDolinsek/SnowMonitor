package data;

import domain.SnowMonitorEntry;

import java.sql.*;
import java.util.ArrayList;

public class SnowMonitorEntryDAO {

    public static void insertAndUpdateId(SnowMonitorEntry entry) {
        int id = insert(entry);
        entry.setId(id);
    }

    public static int insert(SnowMonitorEntry entry) {
        String sql = "INSERT INTO snow_entries (ski_resort, snow_depth, date) VALUES (?, ?, ?)";
        try (PreparedStatement statement = DbConnection.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entry.getSkiResort());
            statement.setInt(2, entry.getSnowDepth());
            statement.setDate(3, Date.valueOf(entry.getDate()));

            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public static ArrayList<SnowMonitorEntry> getAllEntries() {
        ArrayList<SnowMonitorEntry> entries = new ArrayList<>();
        try {
            Connection connection = DbConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM snow_entries");

            while (result.next()) {
                entries.add(getEntryFromResultSet(result));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return entries;
    }

    private static SnowMonitorEntry getEntryFromResultSet(ResultSet set) throws SQLException {
        return new SnowMonitorEntry(set.getInt(1), set.getString(2), set.getInt(3), set.getDate(4).toLocalDate());
    }
}
