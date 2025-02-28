package edu.kis.powp.jobs2d.drivers.adapter.controller;

import edu.kis.powp.jobs2d.drivers.VisitableJob2dDriver;
import edu.kis.powp.jobs2d.drivers.adapter.DriverDecorator;
import edu.kis.powp.jobs2d.drivers.singleton.DeviceManager;


public class DeviceControllerDriver implements DriverDecorator {

    private VisitableJob2dDriver driver;


    public DeviceControllerDriver() {
    }

    public DeviceControllerDriver(VisitableJob2dDriver driver) {
        this.driver = driver;
    }
    //send to DriverManager
    public void addDistanceThreshold(double threshold) {
        DeviceManager.getInstance().addDistanceThreshold(threshold);
    }


    @Override
    public void setPosition(int x, int y) {
        driver.setPosition(x, y);
        DeviceManager.getInstance().calculateSetPositionDistance(x, y);
    }

    @Override
    public void operateTo(int x, int y) {
        //if distance extended - notify observers by DriverManager
        if (DeviceManager.getInstance().checkServiceDistanceThreshold()) {
            return;
        }
        driver.operateTo(x, y);
        DeviceManager.getInstance().calculateOperateToDistance(x, y);
    }

    public void refill() {
        DeviceManager.getInstance().refill();
    }

    public VisitableJob2dDriver getDriver() {
        return this.driver;
    }

    public void setDriver(VisitableJob2dDriver driver) {
        this.driver = driver;
    }


}