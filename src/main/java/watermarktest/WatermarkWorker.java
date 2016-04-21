package watermarktest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Simulate Queue/Rest/Http/TimerJob
 */
public class WatermarkWorker {
    private static final int MAX_RUNNERS = 5;
    Queue<Entry> queue = new ConcurrentLinkedQueue<>();
    @Setter
    private WatermarkDao watermarkDao;
    @Getter
    @Setter
    private WatermarkProcessor watermarkProcessor;
    private int runnerCount;

    public void watermark(String ticket, Document document) {
        queue.add(new Entry(ticket, document));
        startRunner();
    }

    private void startRunner() {
        if (runnerCount < MAX_RUNNERS) {
            Thread thread = new Thread(this::run);
            thread.start();
        }
    }

    public void run() {
        runnerCount++;
        while (true) {
            Entry e = queue.poll();
            if (e != null) {
                watermarkProcessor.watermark(e.ticket, e.document);
            } else {
                runnerCount--;
                return;
            }
        }
    }

    @AllArgsConstructor
    private static class Entry {
        String ticket;
        Document document;
    }
}