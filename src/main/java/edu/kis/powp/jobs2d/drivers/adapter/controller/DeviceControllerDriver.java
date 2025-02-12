package edu.kis.powp.jobs2d.drivers.adapter.controller;

import edu.kis.powp.jobs2d.drivers.VisitableJob2dDriver;
import edu.kis.powp.jobs2d.drivers.adapter.DriverDecorator;
import edu.kis.powp.jobs2d.drivers.singleton.DeviceController;

import java.util.ArrayList;
import java.util.List;

public class DeviceControllerDriver implements DriverDecorator {

    private VisitableJob2dDriver driver;
    private final List<IThresholdListener> listeners = new ArrayList<>();
    private double distanceThreshold = 0;

    public DeviceControllerDriver() {
        addListener(new LoggingThresholdListener());
    }

    public DeviceControllerDriver(VisitableJob2dDriver driver) {
        this.driver = driver;
        addListener(new LoggingThresholdListener());
    }

    public void setDistanceThreshold(double distanceThreshold) {
        this.distanceThreshold = distanceThreshold;
    }

    public void addListener(IThresholdListener listener) {
        listeners.add(listener);
    }

    @Override
    public void setPosition(int x, int y) {
        driver.setPosition(x, y);
        DeviceController.getInstance().calculateSetPositionDistance(x, y);
    }

    @Override
    public void operateTo(int x, int y) {
        if (DeviceController.getInstance().checkServiceDistanceThreshold(distanceThreshold)) {
            notifyListeners();
            return;
        }
        driver.operateTo(x, y);
        DeviceController.getInstance().calculateOperateToDistance(x, y);
    }

    public void refill() {
        DeviceController.getInstance().refill();
    }

    public VisitableJob2dDriver getDriver() {
        return this.driver;
    }

    public void setDriver(VisitableJob2dDriver driver) {
        this.driver = driver;
    }

    private void notifyListeners() {
        for (IThresholdListener listener : listeners) {
            listener.onThresholdExceeded();
        }
    }
}
