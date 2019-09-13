package spring_core;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring_core.loggers.EventLogger;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class App {

    private static ConfigurableApplicationContext applicationContext;
    private Client client;
    private EventLogger eventLogger;
    private Map<EventType, EventLogger> loggers = new Map<EventType, EventLogger>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean containsKey(Object key) {
            return false;
        }

        @Override
        public boolean containsValue(Object value) {
            return false;
        }

        @Override
        public EventLogger get(Object key) {
            return null;
        }

        @Override
        public EventLogger put(EventType key, EventLogger value) {
            return null;
        }

        @Override
        public EventLogger remove(Object key) {
            return null;
        }

        @Override
        public void putAll(Map<? extends EventType, ? extends EventLogger> m) {

        }

        @Override
        public void clear() {

        }

        @Override
        public Set<EventType> keySet() {
            return null;
        }

        @Override
        public Collection<EventLogger> values() {
            return null;
        }

        @Override
        public Set<Entry<EventType, EventLogger>> entrySet() {
            return null;
        }
    };
    private static int count;

    public App(Client client, EventLogger eventLogger, Map<EventType, EventLogger> loggers, int count) {
        this.client = client;
        this.eventLogger = eventLogger;
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
            logger = eventLogger;
        }
        logger.logEvent(event);
    }
}
