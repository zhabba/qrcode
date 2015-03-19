package org.xzha.qrcode.listeners;

import org.apache.log4j.Logger;
import org.xzha.qrcode.mbeans.QrCodeConfigImpl;
import org.xzha.qrcode.mbeans.QrCodeConfigMBean;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.StandardMBean;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.lang.management.ManagementFactory;

/**
 * Class org.xzha.qrcode.listeners.QrcodeListener
 * created at 18.03.15 - 13:03
 */
@WebListener
public class QrCodeListener implements ServletContextListener{


	private static final Logger LOG = Logger.getLogger(QrCodeListener.class);

	private MBeanServer mBeanServer;
	private ObjectName objectName;

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		mBeanServer = ManagementFactory.getPlatformMBeanServer();
		QrCodeConfigImpl qrCodeConfig = new QrCodeConfigImpl();
		try {
			StandardMBean standardMBean = new StandardMBean(qrCodeConfig, QrCodeConfigMBean.class);
			objectName = new ObjectName("org.xzha.qrcode.mbeans:type=QrcodeConfigImpl");
			mBeanServer.registerMBean(standardMBean, objectName);
			QrCodeConfigImpl qrMBean = qrCodeConfig;
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
