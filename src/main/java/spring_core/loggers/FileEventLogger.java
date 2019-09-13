package spring_core.loggers;

import org.apache.commons.io.FileUtils;
import spring_core.Event;

import java.io.File;
import java.io.IOException;

public class FileEventLogger implements EventLogger {

    private String filename;
    private File file;

    public FileEventLogger() {
    }

    public FileEventLogger(String filename) {
        this.filename = filename;
        this.file = new File(filename);
    }

    public void init() throws IOException {
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
