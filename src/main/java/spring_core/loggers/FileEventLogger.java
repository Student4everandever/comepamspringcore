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

    @Value("${events.file:LogFromSpring.txt}")
    private String filename;

    private File file;

    public FileEventLogger() {
    }

    public FileEventLogger(String filename) {
        this.filename = filename;
    }

    @PostConstruct
    public void init() throws IOException {
        file = new File(filename);
        if(!file.canWrite()) {
            System.exit(1);
        }
        writeToFile(file, "", false);
    }

    @Override
    public void logEvent(Event event) throws IOException {
        writeToFile(file, event.getMessage(), true);
    }

    private void writeToFile(File file, String message, boolean bool ) throws IOException {
        FileUtils.writeStringToFile(file, message, bool);
    }
}
