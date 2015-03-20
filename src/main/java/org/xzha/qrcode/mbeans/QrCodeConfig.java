package org.xzha.qrcode.mbeans;

import org.apache.log4j.Logger;

/**
 * Class org.xzha.qrcode.mbeans.QrcodeConfigImpl
 * created at 18.03.15 - 13:15
 */
public class QrCodeConfig implements QrCodeConfigMBean {

	private static final String DEFAULT_SIZE = "128";
	private static final String DEFAULT_TYPE = "png";
	private static final String DEFAULT_DATA = "Welcome to xzha!";

	private	static Logger LOG = Logger.getLogger(QrCodeConfig.class);

	private String size;
	private String type;
	private String data;

	public QrCodeConfig() {
		this.size = DEFAULT_SIZE;
		this.type = DEFAULT_TYPE;
		this.data = DEFAULT_DATA;
	}

	@Override
	public String readConfig() {
		return "Current out image size is " + size + " and type is " + type + " and data is " + data;
	}

	@Override
	public void writeConfig(String size, String type, String data) {
		this.size = size;
		this.type = type;
		this.data = data;
	}

	@Override
	public String readCurrentSize() {
		return String.valueOf(size);
	}

	@Override
	public String readCurrentType() {
		return String.valueOf(type);
	}

	@Override
	public String readCurrentData() {
		return String.valueOf(data);
	}

	@Override
	public void writeCurrentSize(String size) {
		this.size = size;
	}

	@Override
	public void writeCurrentType(String type) {
		this.type = type;
	}

	@Override
	public void writeCurrentData(String data) {
		this.data = data;
	}
}