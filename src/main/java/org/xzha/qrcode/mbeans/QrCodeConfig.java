package org.xzha.qrcode.mbeans;

import org.apache.log4j.Logger;

/**
 * Class org.xzha.qrcode.mbeans.QrcodeConfigImpl
 * created at 18.03.15 - 13:15
 */
public class QrCodeConfig implements QrCodeConfigMBean {

	private static final int DEFAULT_SIZE = 128;
	private static final String DEFAULT_TYPE = "png";
	private static final String DEFAULT_DATA = "Welcome to xzha!";

	private	static Logger LOG = Logger.getLogger(QrCodeConfig.class);

	private int size;
	private String type;
	private String defaultData;

	public QrCodeConfig() {
		this.size = DEFAULT_SIZE;
		this.type = DEFAULT_TYPE;
		this.defaultData = DEFAULT_DATA;
	}

	@Override
	public void setQrCodeSize(int size) {
		this.size = size;
	}

	@Override
	public int getQrCodeSize() {
		return size;
	}

	@Override
	public void setOutType(String type) {
		this.type = type;
	}

	@Override
	public String getOutType() {
		return type;
	}

	@Override
	public void setDefaultData(String defaultData) {
		this.defaultData = defaultData;
	}

	@Override
	public String getDefaultData() {
		return defaultData;
	}

	@Override
	public String readConfig() {
		LOG.debug("size = " + String.valueOf(size) + " type = " + type + " data = " + defaultData);
		return "Current out image size is " + size + " and type is " + type + " and data is " + defaultData;
	}

	@Override
	public String readCurrentSize() {
		return String.valueOf(getQrCodeSize());
	}

	@Override
	public String readCurrentType() {
		return String.valueOf(getOutType());
	}

	@Override
	public String readCurrentData() {
		return String.valueOf(getDefaultData());
	}
}