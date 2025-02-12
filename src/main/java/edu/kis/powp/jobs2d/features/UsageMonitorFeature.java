package edu.kis.powp.jobs2d.features;

import edu.kis.powp.jobs2d.drivers.DriverManager;
import edu.kis.powp.jobs2d.drivers.adapter.controller.DeviceControllerDriver;
import edu.kis.powp.jobs2d.drivers.observer.ApplyDriverDecoratorsSubscriber;

public class UsageMonitorFeature {

    public static void setupDeviceMonitorPlugin(DriverManager driverManager, DeviceControllerDriver deviceControllerDriver) {
        ApplyDriverDecoratorsSubscriber.getInstance().addDriverDecorator(deviceControllerDriver);
        ApplyDriverDecoratorsSubscriber.getInstance().setDriverManager(driverManager);
        driverManager.addSubscriber(ApplyDriverDecoratorsSubscriber.getInstance());
    }
}
