package test.design.kettle;

/**
 * Чайник.
 * Имеет выключатель, индикатор нагрева, мин. / макс. уровень воды, указатель темературы, желаемую темературу нагрева.
 */
public class Kettle {
    private int minimumWaterLevel, maximumWaterLevel, currentWaterLevel;
    private float currentTemperature;
    private int desiredTemperature;
    private boolean isTurnedOn;

    public void setTurnedOn(boolean turnedOn) throws InterruptedException {
        if (turnedOn) {
            if (isTurnedOn)
                throw new IllegalStateException("Kettle already turned on");
            if (currentWaterLevel < minimumWaterLevel)
                throw new IllegalStateException("Not enough water");
        }
        isTurnedOn = turnedOn;
        System.out.println("Boiling...");
        while (currentTemperature < desiredTemperature) {
            currentTemperature += 200f / currentWaterLevel;
            if(currentTemperature % 10 == 0)
                System.out.println(currentTemperature + " / " + desiredTemperature);
            Thread.sleep(10L);
        }
        isTurnedOn = false;
    }

    public int getMinimumWaterLevel() {
        return minimumWaterLevel;
    }

    public void setMinimumWaterLevel(int minimumWaterLevel) {
        this.minimumWaterLevel = minimumWaterLevel;
    }

    public int getMaximumWaterLevel() {
        return maximumWaterLevel;
    }

    public void setMaximumWaterLevel(int maximumWaterLevel) {
        this.maximumWaterLevel = maximumWaterLevel;
    }

    public int getCurrentWaterLevel() {
        return currentWaterLevel;
    }

    public void setCurrentWaterLevel(int currentWaterLevel) {
        this.currentWaterLevel = currentWaterLevel;
    }

    public float getCurrentTemperature() {
        return currentTemperature;
    }

    public void setCurrentTemperature(float currentTemperature) {
        this.currentTemperature = currentTemperature;
    }

    public int getDesiredTemperature() {
        return desiredTemperature;
    }

    public void setDesiredTemperature(int desiredTemperature) {
        this.desiredTemperature = desiredTemperature;
    }

    public boolean isTurnedOn() {
        return isTurnedOn;
    }
}
