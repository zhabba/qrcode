package org.xzha.qrcode.listeners;

import org.apache.log4j.Logger;
import org.xzha.qrcode.mbeans.QrCodeConfig;

import javax.inject.Inject;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.lang.management.ManagementFactory;

/**
 * Class org.xzha.qrcode.listeners.QrcodeListener
 * created at 18.03.15 - 13:03
 */
@WebListener
public class QrCodeListener implements ServletContextListener {

	private static final Logger LOG = Logger.getLogger(QrCodeListener.class);

	@Inject
	private QrCodeConfig qrMBean;

	private MBeanServer mBeanServer;
	private ObjectName objectName;

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		mBeanServer = ManagementFactory.getPlatformMBeanServer();
		try {
			objectName = new ObjectName(qrMBean.getClass().getPackage().getName() + ":type=" + qrMBean.getClass().getSimpleName());
			mBeanServer.registerMBean(qrMBean, objectName);
			LOG.info("MBean registered.");
		} catch (Exception e) {
			LOG.error(e);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		try {
			mBeanServer.unregisterMBean(objectName);
			LOG.info("MBean unregistered.");
		} catch (Exception e) {
			LOG.error(e);
		}
		LOG.info("QRCode context destroyed.");
	}
}
