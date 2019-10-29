package spring_core.loggers;

import org.springframework.stereotype.Component;
import spring_core.Event;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@Component
public class CombinedEventLogger implements EventLogger {

    @Resource(name = "combinedLoggers")
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
