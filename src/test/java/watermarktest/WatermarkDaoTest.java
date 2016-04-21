package watermarktest;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class WatermarkDaoTest {
    @Test
    public void checkStatusWithoutDocument() throws Exception {
        WatermarkService watermarkService = Application.wireUp();
        WatermarkDao watermarkDao = watermarkService.getWatermarkDao();
        watermarkDao.setStatus("1", Status.NOT_STARTED);
        assertThat(watermarkDao.getDocument("1"), is(nullValue()));
    }

    @Test
    public void checkStatusWithDocument() throws Exception {
        WatermarkService watermarkService = Application.wireUp();
        WatermarkDao watermarkDao = watermarkService.getWatermarkDao();
        Document document = new Document();
        watermarkDao.setDocument("1", document);
        assertThat(watermarkDao.getStatus("1"), equalTo(Status.FINISHED));
    }

}