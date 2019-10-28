package spring_core.loggers;

import org.springframework.stereotype.Component;
import spring_core.Event;

import javax.annotation.Resource;
import java.util.Collection;

@Component
public class CombinedEventLogger implements EventLogger {

    @Resource(name = "combinedLoggers")
    private Collection<EventLogger> loggers;

    @Override
    public void logEvent(Event event) {
        for (EventLogger eventLogger : loggers) {
            eventLogger.logEvent(event);
        }
    }
}
