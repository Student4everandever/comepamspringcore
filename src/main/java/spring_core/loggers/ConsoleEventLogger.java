package spring_core.loggers;

import spring_core.Event;

public class ConsoleEventLogger implements EventLogger {

    public void logEvent(Event event){
        System.out.println(event);
    }
}
