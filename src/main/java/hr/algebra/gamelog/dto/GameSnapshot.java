package hr.algebra.gamelog.dto;

import java.io.Serializable;

public class GameSnapshot implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String title;
    private String status;
    private int hoursPlayed;

    public GameSnapshot(Long id, String title, String status, int hoursPlayed) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.hoursPlayed = hoursPlayed;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getStatus() { return status; }
    public int getHoursPlayed() { return hoursPlayed; }
}