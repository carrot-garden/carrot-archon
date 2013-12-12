package com.carrotgarden.settings.git;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(name = Name.PID)
public class Provider {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Activate
	protected void activate(final ComponentContext context) {
		log.info("### activate");
	}

	@Deactivate
	protected void deactivate() {
		log.info("### deactivate");
	}

	@Modified
	protected void modified() {
		log.info("### modified");
	}

}
