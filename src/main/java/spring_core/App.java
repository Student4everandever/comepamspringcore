package spring_core;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring_core.loggers.EventLogger;

import java.io.IOException;
import java.util.Map;

public class App {

    private static ConfigurableApplicationContext applicationContext;
    private Client client;
    private EventLogger cacheFileEventLogger;
    private Map<EventType, EventLogger> loggers;
    private static int count;

    public App(Client client, EventLogger cacheFileEventLogger, Map<EventType, EventLogger> loggers, int count) {
        this.client = client;
        this.cacheFileEventLogger = cacheFileEventLogger;
        this.loggers = loggers;
        App.count = count;
    }

    public static void main(String [] args) throws IOException {

        applicationContext = new ClassPathXmlApplicationContext("spring.xml");

        App app = (App) applicationContext.getBean("app");

        for(int i = 1; i <= count; i++) {
            app.logEvent("Some event for user " + i + "\n", EventType.INFO );
        }
        app.logEvent("New event for user 1" + "\n", EventType.ERROR);
        app.logEvent("One more event for user 1" + "\n", null);
        applicationContext.close();
    }

    private void logEvent(String msg, EventType type) throws IOException {
        String message = msg.replaceAll(
                client.getId(), client.getFullName());
        Event event = (Event) applicationContext.getBean("event");
        event.setMessage(message);
        EventLogger logger = loggers.get(type);
        if(logger == null) {
            logger = cacheFileEventLogger;
        }
        logger.logEvent(event);
    }
}
