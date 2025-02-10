package edu.kis.powp.jobs2d.features;

import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.drivers.adapter.monitor.DeviceMonitorDriver;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("Convert2Lambda")
public class ServicableFeature {

	private static Application application;
	private static final DeviceMonitorDriver deviceMonitorDriver = new DeviceMonitorDriver();

	public static void setupServicablePlugin(
			Application application
	) {
		ServicableFeature.application = application;
		ServicableFeature.application.addComponentMenu(ServicableFeature.class, "Servicing");
		ServicableFeature.application.addComponentMenuElement(ServicableFeature.class, "Refill", refillListener);
	}

	private static final ActionListener refillListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			deviceMonitorDriver.refill();
		}
	};
}
