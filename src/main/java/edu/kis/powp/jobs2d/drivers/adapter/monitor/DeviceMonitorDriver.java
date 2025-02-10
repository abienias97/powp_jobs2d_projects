package edu.kis.powp.jobs2d.drivers.adapter.monitor;

import edu.kis.powp.jobs2d.drivers.VisitableJob2dDriver;
import edu.kis.powp.jobs2d.drivers.adapter.DriverDecorator;
import edu.kis.powp.jobs2d.drivers.singleton.DeviceMonitor;

import java.util.logging.Logger;

public class DeviceMonitorDriver implements DriverDecorator {

    private VisitableJob2dDriver driver;
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public DeviceMonitorDriver() {
    }

    public DeviceMonitorDriver(VisitableJob2dDriver driver) {
        this.driver = driver;
    }

    @Override
    public void setPosition(int x, int y) {
        driver.setPosition(x, y);
        DeviceMonitor.getInstance().calculateSetPositionDistance(x, y);
    }

    @Override
    public void operateTo(int x, int y) {
        if (DeviceMonitor.getInstance().checkServiceDistanceThreshold(2000.0)) {
            logger.info("Service threshold exceeded");
            return;
        }
        driver.operateTo(x, y);
        DeviceMonitor.getInstance().calculateOperateToDistance(x, y);
    }

    public void refill() {
        DeviceMonitor.getInstance().refill();
    }

    public VisitableJob2dDriver getDriver() {
        return this.driver;
    }

    public void setDriver(VisitableJob2dDriver driver) {
        this.driver = driver;
    }

}
