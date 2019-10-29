package spring_core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.util.Date;
import java.util.Random;

@Component
@Scope("prototype")
public class Event {


    public int id = new Random().nextInt();
    private String message;

    @Autowired
    @Qualifier("newDate")
    private Date date;

    @Autowired
    private final DateFormat df;

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
