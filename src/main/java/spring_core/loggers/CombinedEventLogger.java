package spring_core.loggers;

import spring_core.Event;

import java.io.IOException;
import java.util.List;

public class CombinedEventLogger implements EventLogger {
    private List<EventLogger> loggerList;

    public CombinedEventLogger(List<EventLogger> loggerList) {
        this.loggerList = loggerList;
    }

    @Override
    public void logEvent(Event event) throws IOException {
        for (EventLogger eventLogger : loggerList) {
            eventLogger.logEvent(event);
        }
    }
}
