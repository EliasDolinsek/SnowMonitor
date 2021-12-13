package data;

import domain.SnowMonitorEntry;

import java.time.LocalDate;

public class SnowMonitorEntryDAOTest {

    public static void main(String[] args) {
        SnowMonitorEntryDAO.insert(SnowMonitorEntry.newEntry("Test", 2, LocalDate.now()));
        SnowMonitorEntryDAO.getAllEntries().forEach(System.out::println);
    }

}
