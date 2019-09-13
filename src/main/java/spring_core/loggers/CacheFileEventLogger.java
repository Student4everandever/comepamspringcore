package spring_core.loggers;

import spring_core.Event;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CacheFileEventLogger extends FileEventLogger {

    int cacheSize;
    List<Event> cache = new ArrayList<Event>();

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
