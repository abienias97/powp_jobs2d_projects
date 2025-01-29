package edu.kis.powp.jobs2d.drivers.visitor;

import edu.kis.powp.jobs2d.drivers.VisitableJob2dDriver;
import edu.kis.powp.jobs2d.drivers.adapter.DriverDecorator;
import edu.kis.powp.jobs2d.drivers.composite.IDriverComposite;
import edu.kis.powp.jobs2d.drivers.logger.LoggerDriver;

import java.util.List;

public class DriversCountVisitor implements IDriverVisitor {
    private long count = 0;

    public long getCount() {
        return count;
    }

    @Override
    public void visit(IDriverComposite driver) {
        count = 0; // Reset
        List<VisitableJob2dDriver> drivers = driver.getDrivers();
        drivers.forEach(d -> d.accept(this));
        count += 1;
    }

    @Override
    public void visit(DriverDecorator driver) {
        count = 0; // Reset
        VisitableJob2dDriver wrappedDriver = driver.getDriver();
        wrappedDriver.accept(this);
        count += 1;
    }

    @Override
    public void visit(VisitableJob2dDriver driver) {
        count = 1; // Reset plus driver
    }

    @Override
    public void visit(LoggerDriver driver) {
        count = 1; // Reset plus driver
    }
}