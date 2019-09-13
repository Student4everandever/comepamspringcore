package spring_core;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.*;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.ResolvableType;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.ProtocolResolver;
import org.springframework.core.io.Resource;
import spring_core.loggers.CacheFileEventLogger;
import spring_core.loggers.EventLogger;
import spring_core.loggers.FileEventLogger;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Locale;
import java.util.Map;

public class App {

    private Client client;
    private Event event;
    private EventLogger eventLogger;
    private FileEventLogger fileLogger;
    private CacheFileEventLogger cacheFileLogger;

    public App(Client client, Event event, EventLogger eventLogger, FileEventLogger fileLogger, CacheFileEventLogger cacheFileLogger) {
        this.client = client;
        this.event = event;
        this.eventLogger = eventLogger;
        this.fileLogger = fileLogger;
        this.cacheFileLogger = cacheFileLogger;
    }

    public static void main(String [] args) throws IOException {



/*        App app = new App();

        app.client = new Client("1", "John Smith");
        app.eventLogger = new ConsoleEventLogger();

        app.logEvent("Some event for user 1");

 */

        //ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");

        ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");

        App app = (App) ctx.getBean("app");

        app.logEvent("Some event for user 1");
        app.logEvent("Some event for user 2");

        ctx.close();

    }

    private void logEvent(String msg) throws IOException {
        String message = msg.replaceAll(
                client.getId(), client.getFullName());
        event.setMessage(message);
        eventLogger.logEvent(event);
        fileLogger.logEvent(event);
        cacheFileLogger.logEvent(event);
    }
}
