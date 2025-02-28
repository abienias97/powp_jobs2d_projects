package edu.kis.powp.jobs2d.drivers.singleton;

import edu.kis.powp.jobs2d.drivers.RefillableDevice;
import edu.kis.powp.jobs2d.drivers.adapter.controller.IThresholdListener;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public final class DeviceManager implements RefillableDevice {
    private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static DeviceManager instance;
    private int previousX = 0, previousY = 0;
    private double setPositionDistance = 0.0, operateToDistance = 0.0, lastServiceDistance = 0;

    private final List<IThresholdListener> thresholdListeners = new ArrayList<>();
    private final List<Double> distanceThresholds = new ArrayList<>();

    public static DeviceManager getInstance() {
        if (instance == null) {
            instance = new DeviceManager();
        }
        return instance;
    }
    public void addThresholdListener(IThresholdListener listener) {
        thresholdListeners.add(listener);
    }
    private void notifyThresholdListeners() {
        for (IThresholdListener listener : thresholdListeners) {
            listener.onThresholdExceeded();
        }
    }
    public void addDistanceThreshold(double threshold) {
        distanceThresholds.add(threshold);
    }

    public void clearDistanceThresholds() {
        distanceThresholds.clear();
    }

    public List<Double> getDistanceThresholds() {
        return new ArrayList<>(distanceThresholds);
    }

    public boolean checkServiceDistanceThreshold() {
        for (Double threshold : distanceThresholds) {
            if (lastServiceDistance >= threshold) {
                notifyThresholdListeners();
                return true;
            }
        }
        return false;
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
        setPositionDistance += distance;
        addServiceDistance(distance);
        logger.info("Traveled setPosition distance: " +getSetPositionDistance());
    }

    public void calculateOperateToDistance(int newX, int newY) {
        double distance = calculate(newX, newY);
        operateToDistance += distance;
        addServiceDistance(distance);
        logger.info("Traveled operateTo distance: " +getOperateToDistance());

    }

    private double calculate(int newX, int newY) {
        //d=√((x_2-x_1)²+(y_2-y_1)²
        double distance = Math.sqrt(Math.pow(newX - previousX, 2) + Math.pow(newY - previousY, 2));
        previousX = newX;
        previousY = newY;
        return distance;
    }



    private void addServiceDistance(double distance) {
        lastServiceDistance += distance;
    }

    public void refill() {
        lastServiceDistance = 0;
    }
}