package spring_core.loggers;

import spring_core.Event;

import java.io.IOException;

public interface EventLogger {
    void logEvent(Event event) throws IOException;
}
