package test.design.kettle;

/**
 * Церемония заваривания зеленого чая.
 */
public class GreenKettleCeremony {
    private final Kettle kettle;
    private int persons;

    public GreenKettleCeremony(Kettle kettle, int persons) {
        this.kettle = kettle;
        this.persons = persons;
        if (kettle.getMaximumWaterLevel() / 200 < persons) {
            throw new IllegalArgumentException("Kettle is too small");
        }
    }

    /**
     * <ol>
     * <li>Проверить, возможно все уже заварено до нас</li>
     * <li>долить воды по необходиомти</li>
     * <li>включить, убедиться что горит индикатор</li>
     * <li>дождаться закипания воды до 80 градусов
     * </ol>
     */
    public void begin() throws InterruptedException {
        if (kettle.getCurrentTemperature() < 80) {
            if (kettle.getCurrentWaterLevel() < kettle.getMaximumWaterLevel()) {
                kettle.setCurrentWaterLevel(persons * 200);
            }
            kettle.setTurnedOn(false);
            while (kettle.isTurnedOn()) {
                Thread.sleep(10L);
            }
            assert kettle.getCurrentTemperature() == 80 : "Kettle is broken";
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Kettle kettle = new Kettle();
        kettle.setMinimumWaterLevel(200);
        kettle.setMaximumWaterLevel(2500);
        kettle.setDesiredTemperature(80);
        new GreenKettleCeremony(kettle, 5).begin();
    }
}
