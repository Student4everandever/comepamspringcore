package spring_core.loggers;

import org.springframework.stereotype.Component;
import spring_core.Event;

@Component
public class ConsoleEventLogger implements EventLogger {

    public void logEvent(Event event){
        System.out.println(event);
    }
}
