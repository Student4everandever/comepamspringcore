package spring_core;

import java.text.DateFormat;
import java.util.Date;
import java.util.Random;

public class Event {

    private final DateFormat df;
    public int id = new Random().nextInt();
    private String message;
    private Date date;

    Event(Date date, DateFormat df) {
        this.date = date;
        this.df = df;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Event{" +
                "df=" + df +
                ", id=" + id +
                ", message='" + message + '\'' +
                ", date=" + date +
                '}';
    }
}
