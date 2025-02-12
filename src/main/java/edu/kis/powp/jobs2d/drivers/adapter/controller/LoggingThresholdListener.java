package edu.kis.powp.jobs2d.drivers.adapter.controller;

import java.util.logging.Logger;

public class LoggingThresholdListener implements IThresholdListener{
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    public void onThresholdExceeded() {
        logger.info("Service threshold exceeded");
    }
}
