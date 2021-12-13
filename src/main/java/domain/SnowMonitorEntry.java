package domain;

import java.time.LocalDate;

public class SnowMonitorEntry {

    private int id;
    private String skiResort;
    private int snowDepth;
    private LocalDate date;

    public static SnowMonitorEntry newEntry(String skiResort, int snowDepth, LocalDate date) {
        return new SnowMonitorEntry(-1, skiResort, snowDepth, date);
    }

    public SnowMonitorEntry(int id, String skiResort, int snowDepth, LocalDate date) {
        this.id = id;
        this.skiResort = skiResort;
        this.snowDepth = snowDepth;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSkiResort() {
        return skiResort;
    }

    public void setSkiResort(String skiResort) {
        this.skiResort = skiResort;
    }

    public int getSnowDepth() {
        return snowDepth;
    }

    public void setSnowDepth(int snowDepth) {
        this.snowDepth = snowDepth;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "SnowMonitorEntry{" +
                "id=" + id +
                ", skiResort='" + skiResort + '\'' +
                ", snowDepth=" + snowDepth +
                ", date=" + date +
                '}';
    }
}
