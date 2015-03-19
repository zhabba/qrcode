package org.xzha.qrcode.mbeans;

/**
 * Created by zhabba on 18.03.15.
 */
public interface QrCodeConfigMBean {
	public void setQrCodeSize(int size);
	public int getQrCodeSize();

	public void setOutType(String type);
	public String getOutType();

	public void setDefaultData(String defaultData);
	public String getDefaultData();

	public String readConfig();
}
