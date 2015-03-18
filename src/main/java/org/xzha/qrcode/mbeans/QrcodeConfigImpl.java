package org.xzha.qrcode.mbeans;

/**
 * Class org.xzha.qrcode.mbeans.QrcodeConfigImpl
 * created at 18.03.15 - 13:15
 */
public class QrcodeConfigImpl implements QrcodeConfigMBean{
	private int size;
	private String type;
	private String defaultData;

	public QrcodeConfigImpl(int size, String type, String defaultData) {
		this.size = size;
		this.type = type;
		this.defaultData = defaultData;
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