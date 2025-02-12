package edu.kis.powp.jobs2d.drivers.singleton;

import edu.kis.powp.jobs2d.drivers.RefillableDevice;

import java.util.logging.Logger;

public final class DeviceController implements RefillableDevice {
    private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static DeviceController instance;
    private int previousX = 0, previousY = 0;
    private double setPositionDistance = 0.0, operateToDistance = 0.0, lastServiceDistance = 0;

    public static DeviceController getInstance() {
        if (instance == null) {
            instance = new DeviceController();
        }
        return instance;
    }

    public double getSetPositionDistance() {
        return this.setPositionDistance;
    }

    public double getOperateToDistance() {
        return this.operateToDistance;
    }

    public double getWorkDistance() {
        return this.setPositionDistance + this.operateToDistance;
    }

    public void calculateSetPositionDistance(int newX, int newY) {
        double distance = calculate(newX, newY);
        setPositionDistance = distance + setPositionDistance;
        logger.info("Traveled setPosition distance: " + instance.getSetPositionDistance());
    }

    public void calculateOperateToDistance(int newX, int newY) {
        double distance = calculate(newX, newY);
        operateToDistance = distance + operateToDistance;
        addServiceDistance(distance);
        logger.info("Traveled operateTo distance: " + instance.getOperateToDistance());

    }

    private double calculate(int newX, int newY) {
        //d=√((x_2-x_1)²+(y_2-y_1)²
        double distance = Math.sqrt(Math.pow(newX - previousX, 2) + Math.pow(newY - previousY, 2));
        previousX = newX;
        previousY = newY;
        return distance;
    }

    public boolean checkServiceDistanceThreshold(double threshold) {
        return lastServiceDistance >= threshold;
    }

    private void addServiceDistance(double distance) {
        lastServiceDistance = lastServiceDistance + distance;
    }

    public void refill() {
        lastServiceDistance = 0;
    }
}
