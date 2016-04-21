package watermarktest;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ApplicationTest {
    private final WatermarkService watermarkService = Application.wireUp();

    @Test
    public void mainProcess() throws InterruptedException {
        Book book = new Book();
        String ticket = watermarkService.watermark(book);
        assertThat(watermarkService.getStatus(ticket), equalTo(Status.NOT_STARTED));
        Thread.sleep(100);
        assertThat(watermarkService.getStatus(ticket), equalTo(Status.NOT_STARTED));

        Thread.sleep(2000);
        assertThat(watermarkService.getStatus(ticket), equalTo(Status.FINISHED));
    }
}