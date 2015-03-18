package org.xzha.qrcode.listeners;

import org.apache.log4j.Logger;
import org.xzha.qrcode.mbeans.QrcodeConfigImpl;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.lang.management.ManagementFactory;

/**
 * Class org.xzha.qrcode.listeners.QrcodeListener
 * created at 18.03.15 - 13:03
 */
public class QrcodeListener implements ServletContextListener{
	private static final int DEFAULT_SIZE = 32;
	private static final String DEFAULT_TYPE = "png";
	private static final String DEFAULT_DATA = "Welcome to xzha!";

	private static final Logger LOG = Logger.getLogger(QrcodeListener.class);

	private QrcodeConfigImpl qrMBean;

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
		QrcodeConfigImpl mbean = new QrcodeConfigImpl(DEFAULT_SIZE, DEFAULT_TYPE, DEFAULT_DATA);
		try {
			ObjectName objectName = new ObjectName("org.xzha.qrcode.mbeans:type=QrcodeConfigImpl");
			mBeanServer.registerMBean(mbean, objectName);
			qrMBean = mbean;
		} catch (MalformedObjectNameException e) {
			LOG.error(e);
		} catch (NotCompliantMBeanException e) {
			LOG.error(e);
		} catch (InstanceAlreadyExistsException e) {
			LOG.error(e);
		} catch (MBeanRegistrationException e) {
			LOG.error(e);
		}
		LOG.info("MBean registered.");
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		LOG.info("MBean registered.");
	}
}
