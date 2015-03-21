package org.xzha.qrcode.servlets;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.management.ManagementFactory;
import java.util.Hashtable;
import java.util.Iterator;

/**
 * Created by zhabba on 26.07.14.
 */
public class QrCodeGenServlet extends HttpServlet {

	private static final Logger LOG = Logger.getLogger(QrCodeGenServlet.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOG.info("GET request processing.");
        // Parse parameters
		int size;
		String data = request.getParameter("data");
        String type = request.getParameter("type");
		String ssize = request.getParameter("size");
		if (ssize == null || type == null || data == null) {
			size = Integer.parseInt((String) invokeMBean("readCurrentSize"));
			type = (String) invokeMBean("readCurrentType");
			data = (String) invokeMBean("readCurrentData");
			LOG.debug(String.format("Using MBean data. size = %s, type = %s, data = %s.", size, type, data));
		} else {
			size = Integer.parseInt(ssize);
		}
        try {
            Hashtable<EncodeHintType, Object> hintMap = new Hashtable<>();
            hintMap.put(EncodeHintType.CHARACTER_SET, "UTF8");
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            BufferedImage image = MatrixToImageWriter.toBufferedImage(
                    new QRCodeWriter().encode(data, BarcodeFormat.QR_CODE, size, size, hintMap)
            );

            /* if we want custom colors:
            BufferedImage image = MatrixToImageWriter.toBufferedImage(
                    new QRCodeWriter().encode(data, BarcodeFormat.QR_CODE, size, size, hintMap),
                    new MatrixToImageConfig(Color.RED.getRGB(), Color.GREEN.getRGB()));
            */

            ByteArrayOutputStream byteOut =  new ByteArrayOutputStream();
            ImageOutputStream outImage = ImageIO.createImageOutputStream(byteOut);
            Iterator iter = ImageIO.getImageWritersByFormatName(type);
            ImageWriter writer = null;
            if (iter.hasNext()) {
                writer = (ImageWriter)iter.next();
            }
            if (writer != null) {
                writer.setOutput(outImage);
                writer.write(image);
                writer.dispose();
            }
            OutputStream out = response.getOutputStream();
            response.setContentType("image/" + type);
            response.setContentLength(byteOut.size());
            out.write(byteOut.toByteArray());
            out.flush();
            out.close();
        } catch (WriterException e) {
            LOG.error(e);
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
			LOG.error(e);
		}
		return result;
	}
}
