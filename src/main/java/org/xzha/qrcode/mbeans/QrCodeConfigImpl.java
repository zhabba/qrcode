package org.xzha.qrcode.mbeans;

import javax.annotation.ManagedBean;
import javax.enterprise.inject.Default;
import javax.inject.Inject;

/**
 * Class org.xzha.qrcode.mbeans.QrcodeConfigImpl
 * created at 18.03.15 - 13:15
 */
@Default
public class QrCodeConfigImpl implements QrCodeConfigMBean {

	private static final int DEFAULT_SIZE = 128;
	private static final String DEFAULT_TYPE = "png";
	private static final String DEFAULT_DATA = "Welcome to xzha!";

	private int size;
	private String type;
	private String defaultData;

	public QrCodeConfigImpl() {
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
	public String doConfig() {
		return "Current out image size is " + getQrCodeSize() + " and type is " + getOutType() + " and data is " + getDefaultData();
	}
}