package spring_core.loggers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import spring_core.Event;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CacheFileEventLogger extends FileEventLogger {

    //private String filename;
    @Value("${cache.size:3}")
    private int cacheSize;
    private List<Event> cache;

    public CacheFileEventLogger() { }

    public CacheFileEventLogger(String filename, int cacheSize) {
        super(filename);
        this.cacheSize = cacheSize;
    }

    public void logEvent(Event event) throws IOException {
        cache.add(event);

        if(cache.size() == cacheSize) {
            writeEventFromCache();
            cache.clear();
        }
    }

    @PostConstruct
    public void initCache() {
        this.cache = new ArrayList<Event>(cacheSize);
    }

    @PreDestroy
    public void destroy() throws IOException {
        if(!cache.isEmpty()) {
            writeEventFromCache();
        }
    }

    private void writeEventFromCache() throws IOException {

        for (Event event : cache) {
            super.logEvent(event);
        }
    }
}
