package org.xzha.qrcode.servlets;

import org.apache.log4j.Logger;
import org.xzha.qrcode.qrcodegen.QrCodeGen;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.management.ManagementFactory;
import java.util.concurrent.Future;

/**
 * Created by zhabba on 26.07.14.
 */
public class QrCodeGenServlet extends HttpServlet {

	private static final Logger LOG = Logger.getLogger(QrCodeGenServlet.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOG.info("GET request processing.");
        // Parse parameters
		String data = request.getParameter("data");
        String type = request.getParameter("type");
		String size = request.getParameter("size");
		if (size == null) {
			size = (String) invokeMBean("readCurrentSize");
			LOG.debug(String.format("Using MBean data. size = %s.", size));
		}
		if (type == null) {
			type = (String) invokeMBean("readCurrentType");
			LOG.debug(String.format("Using MBean data. type = %s.", type));
		}
		if (data == null) {
			data = (String) invokeMBean("readCurrentData");
			LOG.debug(String.format("Using MBean data. data = %s.", data));
		}
		Future<OutputStream> result = QrCodeGen.generateQR(data, size, type, response.getOutputStream());
		while(!result.isDone()) {
			try {
				result.get();
				response.setContentType("image/" + type);
			} catch (Exception e) {
				LOG.error(" error: " + e.getMessage());
				response.setStatus(HttpServletResponse.SC_NO_CONTENT);
			}
		}
    }

	/**
	 *
	 * @param operation
	 * @return
	 */
	private Object invokeMBean(String operation) {
		Object result = null;
		try {
			ObjectName objectName = new ObjectName("org.xzha.qrcode.mbeans:type=QrCodeConfig");
			MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
			result = mbs.invoke(objectName, operation, null, null);
		} catch (Exception e) {
			LOG.error("MBean error: " + e.getMessage());
		}
		return result;
	}
}
