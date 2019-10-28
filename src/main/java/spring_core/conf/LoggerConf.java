package spring_core.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import spring_core.EventType;
import spring_core.loggers.EventLogger;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;

public class LoggerConf {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigIn() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Resource(name = "consoleEventLogger")
    private EventLogger consoleEventLogger;

    @Resource(name = "fileLogger")
    private EventLogger fileEventLogger;

    @Resource(name = "combinedEventLogger")
    private EventLogger combinedEventLogger;

    @Resource(name = "cacheFileEventLogger")
    private EventLogger cacheEventLogger;

    @Bean
    public Collection<EventLogger> combinedLoggers() {
        Collection<EventLogger> loggers = new ArrayList<EventLogger>(2);
        loggers.add(consoleEventLogger);
        loggers.add(fileEventLogger);
        return loggers;
    }

    @Bean
    public Map<EventType, EventLogger> loggerMap() {
        Map<EventType, EventLogger> map = new EnumMap<>(EventType.class);
        map.put(EventType.INFO, consoleEventLogger);
        map.put(EventType.ERROR, combinedEventLogger);
        return map;
    }

    @Bean
    public EventLogger defaultLogger() {
        return cacheEventLogger;
    }
}
