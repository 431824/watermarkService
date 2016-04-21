package watermarktest;

import lombok.Setter;

public class WatermarkProcessor {
    public static final long SLEEP_DURATION = 1000L;

    @Setter
    private WatermarkDao watermarkDao;

    public void watermark(String ticket, Document document) {
        try {
            simulateHardWork();
            watermarkDao.setStatus(ticket, Status.PROCESSING);
            doWatermark(document);
            simulateHardWork();
            watermarkDao.setDocument(ticket, document);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected Watermark generateWatermark(Document document) {
        Watermark watermark = new Watermark();
        watermark.setAuthor(document.getAuthor());
        watermark.setTitle(document.getTitle());
        if (document instanceof Book) {
            watermark.setTopic(((Book) document).getTopic());
        }
        return watermark;
    }

    private void doWatermark(Document document) {
        document.setWatermark(generateWatermark(document));
    }

    private void simulateHardWork() throws InterruptedException {
        Thread.sleep(SLEEP_DURATION);
    }
}
