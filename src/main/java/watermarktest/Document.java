package watermarktest;

import lombok.Data;

@Data
public class Document {
    private String content;
    private String title;
    private String author;
    private Watermark watermark;    
}
