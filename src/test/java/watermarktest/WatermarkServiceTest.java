package watermarktest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.not;

public class WatermarkServiceTest {
    @Rule
    public final ExpectedException thrown = ExpectedException.none();
    private WatermarkService watermarkService = Application.wireUp();

    @Test
    public void checksCorrectWireUp() {
        assertThat(watermarkService.getWatermarkDao(), is(notNullValue()));
        WatermarkWorker watermarkWorker = watermarkService.getWatermarkWorker();
        assertThat(watermarkWorker, is(notNullValue()));
        assertThat(watermarkWorker.getWatermarkProcessor(), is(notNullValue()));
    }

    @Test
    public void returnsNotEmptyOrNullableTicket() {
        String watermark = watermarkService.watermark(new Document());
        assertThat(watermark, is(not(isEmptyString())));
    }

    @Test
    public void returnsStatusForNullTicket() {
        Status status = watermarkService.getStatus(null);
        assertThat(status, equalTo(Status.NOT_FOUND));
    }

    @Test
    public void returnsStatusForEmptyTicket() {
        Status status = watermarkService.getStatus("");
        assertThat(status, equalTo(Status.NOT_FOUND));
    }

    @Test
    public void returnsDocumentWithEmptyTicket() {
        Document document = watermarkService.getDocument("");
        assertThat(document, is(nullValue()));
    }

    @Test
    public void returnsDocumentWithNullTicket() {
        Document document = watermarkService.getDocument(null);
        assertThat(document, is(nullValue()));
    }

}