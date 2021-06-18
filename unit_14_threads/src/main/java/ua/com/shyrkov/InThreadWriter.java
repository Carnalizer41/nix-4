package ua.com.shyrkov;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class InThreadWriter implements Runnable {

    private final String filepath;
    private final StringBuffer currentValue;
    private final StringBuffer oldValue;
    private AtomicBoolean running;
    private final Logger logger = LoggerFactory.getLogger(InThreadWriter.class);

    public InThreadWriter(String filepath) {
        this.filepath = filepath;
        this.currentValue = new StringBuffer();
        this.running = new AtomicBoolean(true);
        this.oldValue = new StringBuffer();
    }

    @Override
    public void run() {
        logger.info("Starting new writer thread...");
        while (running.get()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.error("Writer thread was interrupted :(");
            }
            if (!currentValue.toString().equals(oldValue.toString())) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(filepath, false))) {
                    logger.info("Saving new data...");
                    writer.write(currentValue.toString());
                    writer.flush();
                    oldValue.delete(0, oldValue.length());
                    oldValue.append(currentValue);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void appendData(String data) {
        currentValue.append(data);
    }

    public void stop(){
        logger.info("Stopping writer thread...");
        running.set(false);
    }

}
