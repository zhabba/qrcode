package org.xzha.qrcode.qrcodegen;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Class org.xzha.qrcode.qrcodegen.QrCodeGen
 * created at 23.03.15 - 12:24
 */
public class QrCodeGen {
	private static ExecutorService es = Executors.newCachedThreadPool();

	/**
	 *
	 * @param data data to encodeString
	 * @param size output image size
	 * @param type output image type
	 * @return Future<T> output stream containing encoded image
	 */
	public static Future<OutputStream> generateQR(String data, String size, String type, OutputStream out) {
		return es.submit(
				new Callable<OutputStream>() {
					@Override
					public OutputStream call() throws WriterException, IOException {
						Hashtable<EncodeHintType, Object> hintMap = new Hashtable<>();
						hintMap.put(EncodeHintType.CHARACTER_SET, "UTF8");
						hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
						MatrixToImageWriter.writeToStream(
								new QRCodeWriter().encode(
										data, BarcodeFormat.QR_CODE,
										Integer.parseInt(size),
										Integer.parseInt(size),
										hintMap
								),
								type,
								out
						);
						return out;
					}
				}
		);
	}
}
