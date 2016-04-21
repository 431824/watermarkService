package watermarktest;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by bmalik on 20.04.16.
 */
public class WatermarkProcessorTest {

    private final WatermarkService watermarkMockService = mock(WatermarkService.class);

    @Before
    public void setUp() {
        WatermarkWorker worker = new WatermarkWorker();
        worker.setWatermarkProcessor(new WatermarkProcessor());
        when(watermarkMockService.getWatermarkWorker()).thenReturn(worker);
        WatermarkWorker watermarkWorker = watermarkMockService.getWatermarkWorker();
    }

    @Test
    public void compareBooksTopic() throws Exception {
        Book document = new Book();
        document.setTopic("123");
        WatermarkWorker watermarkWorker = watermarkMockService.getWatermarkWorker();
        WatermarkProcessor watermarkProcessor = watermarkWorker.getWatermarkProcessor();
        Watermark watermark = watermarkProcessor.generateWatermark(document);
        assertThat(watermark.getTopic(), equalTo(document.getTopic()));
    }

    @Test
    public void compareJournalTopic() throws Exception {
        Journal document = new Journal();
        WatermarkWorker watermarkWorker = watermarkMockService.getWatermarkWorker();
        WatermarkProcessor watermarkProcessor = watermarkWorker.getWatermarkProcessor();
        Watermark watermark = watermarkProcessor.generateWatermark(document);
        assertThat(watermark.getTopic(), equalTo(null));
    }

    @Test
    public void checksWatermarkRealProcess() throws Exception {
        WatermarkService watermarkService = Application.wireUp();
        Book book = new Book();
        String ticket = watermarkService.watermark(book);
        Thread.sleep(100);
        assertThat(watermarkService.getStatus(ticket), equalTo(Status.NOT_STARTED));
        Thread.sleep(1200);
        assertThat(watermarkService.getStatus(ticket), equalTo(Status.PROCESSING));
        Thread.sleep(2000);
        assertThat(watermarkService.getStatus(ticket), equalTo(Status.FINISHED));
    }

    private void simulateWhen(Status status) {
        when(watermarkMockService.getStatus(anyString())).thenReturn(status);
    }

    @Test
    public void checksWatermarkProcess() throws Exception {
        Book book = new Book();
        String ticket = watermarkMockService.watermark(book);
        Thread.sleep(1);
        simulateWhen(Status.NOT_STARTED);
        assertThat(watermarkMockService.getStatus(ticket), equalTo(Status.NOT_STARTED));
        Thread.sleep(2);
        simulateWhen(Status.PROCESSING);
        assertThat(watermarkMockService.getStatus(ticket), equalTo(Status.PROCESSING));
        Thread.sleep(3);
        simulateWhen(Status.FINISHED);
        assertThat(watermarkMockService.getStatus(ticket), equalTo(Status.FINISHED));
    }
}