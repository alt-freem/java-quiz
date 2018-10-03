package test.design.kettle;

public class KettleManager {
    public static final int GREEN_TEE = 80;
    public static final int BLACK_TEE = 100;

    private final Kettle[] kettles;

    private volatile int freeKettle;

    public KettleManager(Kettle[] kettles) {
        this.kettles = kettles;
    }

    public float makeSomeTee(int cups, int teaType) throws InterruptedException {
        Kettle kettle;
        do {
            kettle = kettles[freeKettle++];
        } while (kettle.isTurnedOn() && freeKettle < kettles.length);
        kettle.setDesiredTemperature(teaType);
        int waterRequired = cups * 200;
        if (kettle.getMaximumWaterLevel() < waterRequired) {
            throw new IllegalArgumentException("Kettle is too small");
        }
        if (kettle.getCurrentWaterLevel() < kettle.getMaximumWaterLevel()) {
            kettle.setCurrentWaterLevel(waterRequired);
        }
        if (kettle.getCurrentTemperature() < kettle.getDesiredTemperature()) {
            kettle.setTurnedOn(true);
        }
        kettle.setCurrentWaterLevel(kettle.getCurrentWaterLevel() - waterRequired);
        return kettle.getCurrentTemperature();
    }
}
