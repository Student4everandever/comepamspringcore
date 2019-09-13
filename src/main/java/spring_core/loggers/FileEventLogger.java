package spring_core.loggers;

import org.apache.commons.io.FileUtils;
import spring_core.Event;

import java.io.File;
import java.io.IOException;

public class FileEventLogger implements EventLogger {

    String filename;
    File file;

    public FileEventLogger(String filename) {
        this.filename = filename;
        this.file = new File(filename);
    }

    public void init() throws IOException {
        if(!file.canWrite()) {
            System.exit(1);
        }
    }

    @Override
    public void logEvent(Event event) throws IOException {
        FileUtils.writeStringToFile(file, event.getMessage(), true);
    }
}
