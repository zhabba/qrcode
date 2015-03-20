package org.xzha.qrcode.mbeans;

/**
 * Created by zhabba on 18.03.15.
 */
public interface QrCodeConfigMBean {

	public String readConfig();

	public void writeConfig(String size, String type, String data);

	public String readCurrentSize();

	public String readCurrentType();

	public String readCurrentData();

	public void writeCurrentSize(String size);

	public void writeCurrentType(String type);

	public void writeCurrentData(String data);
}
