package spring_core.loggers;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import spring_core.Event;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

@Component("fileLogger")
public class FileEventLogger implements EventLogger {

    private File file;

    @Value("${events.file:target/events_log.txt}")
    private String filename;

    public FileEventLogger() {
    }

    public FileEventLogger(String filename) {
        this.filename = filename;
    }

    @PostConstruct
    public void init() throws IOException {
        file = new File(filename);
        if (file.exists() && !file.canWrite()) {
            throw new IllegalArgumentException(
                    "Can't write to file " + filename);
        } else if (!file.exists()) {
            file.createNewFile();
        }
    }

    @Override
    public void logEvent(Event event) {
        try {
            FileUtils.writeStringToFile(file, event.toString() + "\n", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}