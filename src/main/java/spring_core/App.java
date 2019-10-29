package spring_core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import spring_core.conf.AppConfig;
import spring_core.conf.LoggerConfig;
import spring_core.loggers.EventLogger;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

@Service
public class App {

    //private static ConfigurableApplicationContext applicationContext;

    private static AnnotationConfigApplicationContext applicationContext;

    @Autowired
    private Client client;

    @Resource
    private EventLogger cacheFileEventLogger;

    @Resource
    private Map<EventType, EventLogger> loggers;

//    @Value("${count:3}")
    private static int count = 5;

    public App() {}

    public App(Client client, EventLogger cacheFileEventLogger, Map<EventType, EventLogger> loggers, int count) {
        this.client = client;
        this.cacheFileEventLogger = cacheFileEventLogger;
        this.loggers = loggers;
        App.count = count;
    }

    public static void main(String [] args) throws IOException {

        //applicationContext = new ClassPathXmlApplicationContext("spring.xml");

        applicationContext = new AnnotationConfigApplicationContext();

        applicationContext.register(AppConfig.class, LoggerConfig.class);
        applicationContext.scan("spring_core");
        applicationContext.refresh();

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
