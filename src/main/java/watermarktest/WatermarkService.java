package watermarktest;

import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.security.SecureRandom;

public class WatermarkService {
    private SecureRandom random = new SecureRandom();

    @Setter
    @Getter
    private WatermarkDao watermarkDao;
    
    @Setter
    @Getter
    private WatermarkWorker watermarkWorker;
    
    public String watermark(Document document) {
        String ticket = generateTicket();
        watermarkDao.setStatus(ticket, Status.NOT_STARTED);
        watermarkWorker.watermark(ticket, document);
        return ticket;
    }

    private String generateTicket() {
        return new BigInteger(130, random).toString(32);
    }

    public Status getStatus(String ticket) {
        return watermarkDao.getStatus(ticket);
    }

    public Document getDocument(String ticket) {
        return watermarkDao.getDocument(ticket);
    }
}