package spring_core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;
import spring_core.conf.AppConfig;
import spring_core.conf.LoggerConf;
import spring_core.loggers.EventLogger;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class App {

    @Autowired
    private Client client;

    @Resource
    private EventLogger defaultLogger;

    @Resource
    private Map<EventType, EventLogger> loggers;

    public App() {
    }

    App(Client client, EventLogger defaultLogger,
        Map<EventType, EventLogger> loggersMap) {
        this.client = client;
        this.defaultLogger = defaultLogger;
        this.loggers = loggersMap;
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(AppConfig.class, LoggerConf.class);
        ctx.scan("spring_core");
        ctx.refresh();

        App app = (App) ctx.getBean("app");

        Client client = ctx.getBean(Client.class);
        System.out.println("Client says: " + client.getGreeting());

        Event event = ctx.getBean(Event.class);
        app.logEvent(EventType.INFO, event, "Some event for 1");

        event = ctx.getBean(Event.class);
        app.logEvent(EventType.ERROR, event, "Some event for 2");

        event = ctx.getBean(Event.class);
        app.logEvent(null, event, "Some event for 3");

        ctx.close();
    }

    private void logEvent(EventType eventType, Event event, String msg) {
        String message = msg.replaceAll(client.getId(), client.getFullName());
        event.setMsg(message);

        EventLogger logger = loggers.get(eventType);
        if (logger == null) {
            logger = defaultLogger;
        }
        logger.logEvent(event);
    }

}