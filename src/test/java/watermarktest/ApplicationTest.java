package watermarktest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ApplicationTest {
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final WatermarkService watermarkService = Application.wireUp();

    @Test
    public void mainProcess() throws InterruptedException {
        Book book = new Book();
        String ticket = watermarkService.watermark(book);
        Status status = watermarkService.getStatus(ticket);
        assertThat(status, equalTo(Status.NOT_STARTED));
        Thread.sleep(100);

        Thread.sleep(2000);
        assertThat(status, equalTo(Status.FINISHED));
    }

}