package watermarktest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

public class WatermarkDao {

    public static final Entry EMPTY = new Entry(Status.NOT_FOUND, null);
    private Map<String, Entry> map = new HashMap<>();
    
    public void setStatus(String ticket, Status status) {
        synchronized (map) {
            map.put(ticket, new Entry(status, null));
        }
    }
    
    public void setDocument(String ticket, Document document) {
        synchronized (map) {
            map.put(ticket, new Entry(Status.FINISHED, document));
        }
    }
    
    public Status getStatus(String ticket) {
        return map.getOrDefault(ticket, EMPTY).getStatus();
    }
    
    public Document getDocument(String ticket) {
        return map.getOrDefault(ticket, EMPTY).getDocument();
    }

    @Getter
    @Setter
    @AllArgsConstructor
    private static class Entry {
        private Status status;
        private Document document;
    }
}
