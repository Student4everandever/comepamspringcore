package spring_core;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring_core.loggers.EventLogger;

import java.io.IOException;

public class App {

    private static ConfigurableApplicationContext applicationContext;
    private Client client;
    private static int count = 5;

    public App(Client client) {
        this.client = client;
    }

    public static void main(String [] args) throws IOException {

        applicationContext = new ClassPathXmlApplicationContext("spring.xml");

        App app = (App) applicationContext.getBean("app");

        for(int i = 1; i <= count; i++) {
            app.logEvent("Some event for user " + i + "\n" );
        }
        applicationContext.close();
    }

    private void logEvent(String msg) throws IOException {
        String message = msg.replaceAll(
                client.getId(), client.getFullName());
        Event event = (Event) applicationContext.getBean("event");
        EventLogger eventLogger = (EventLogger) applicationContext.getBean("cacheFileLogger");
        event.setMessage(message);
        eventLogger.logEvent(event);
    }
}
