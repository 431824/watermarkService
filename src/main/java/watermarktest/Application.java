package watermarktest;

public class Application {

    /**
     * Simulate DI.
     */
    public static WatermarkService wireUp() {
        WatermarkDao watermarkDao = new WatermarkDao();
        
        WatermarkProcessor watermarkProcessor = new WatermarkProcessor();
        watermarkProcessor.setWatermarkDao(watermarkDao);
        
        WatermarkWorker watermarkWorker = new WatermarkWorker();
        watermarkWorker.setWatermarkDao(watermarkDao);
        watermarkWorker.setWatermarkProcessor(watermarkProcessor);
        
        WatermarkService watermarkService = new WatermarkService();
        watermarkService.setWatermarkDao(watermarkDao);
        watermarkService.setWatermarkWorker(watermarkWorker);
        
        return watermarkService;
    }
}
