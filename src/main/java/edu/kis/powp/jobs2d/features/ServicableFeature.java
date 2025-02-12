package edu.kis.powp.jobs2d.features;

import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.drivers.adapter.controller.DeviceControllerDriver;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("Convert2Lambda")
public class ServicableFeature {

	private static Application application;
	private static DeviceControllerDriver deviceControllerDriver = null;

	public static void setupServicablePlugin(
			Application application,
			DeviceControllerDriver deviceControllerDriver
	) {
		ServicableFeature.deviceControllerDriver = deviceControllerDriver;
		ServicableFeature.application = application;
		ServicableFeature.application.addComponentMenu(ServicableFeature.class, "Servicing");
		ServicableFeature.application.addComponentMenuElement(ServicableFeature.class, "Refill", refillListener);
	}

	private static final ActionListener refillListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			deviceControllerDriver.refill();
		}
	};
}
