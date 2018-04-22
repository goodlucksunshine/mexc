package com.mexc.common.util.google;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 */
public class QRUtils {

	/*默认宽度*/
	private final static int DEFAULT_WIDTH = 150;
	/*默认高度*/
	private final static int DEFAULT_HEIGHT = 150;


	/**
	 * 生成二维码文件
	 *
	 * @param content
	 * @param height
	 * @param width
	 * @param filePath
	 * @param fileName
	 * @throws WriterException
	 * @throws IOException
	 */
	public static void generateMatrixPic(String content, int height, int width, String filePath, String fileName) throws WriterException, IOException {
		BitMatrix bitMatrix = getBitMatrix(content, height, width);
		Path path = FileSystems.getDefault().getPath(filePath, fileName);
		MatrixToImageWriter.writeToPath(bitMatrix, "png", path);
	}

	/**
	 * 生成二维码内存流
	 *
	 * @param content
	 * @param height
	 * @param width
	 * @return
	 * @throws WriterException
	 */
	public static BufferedImage generateMatrixBufferedImage(String content, int height, int width) throws WriterException {
		BitMatrix bitMatrix = getBitMatrix(content, height, width);
		return MatrixToImageWriter.toBufferedImage(bitMatrix);
	}

	/**
	 * 生成二维码内存流
	 *
	 * @param content
	 * @return
	 * @throws WriterException
	 */
	public static BufferedImage generateMatrixBufferedImage(String content) throws WriterException {
		return generateMatrixBufferedImage(content, DEFAULT_HEIGHT, DEFAULT_WIDTH);
	}


	/**
	 * 生成矩阵
	 *
	 * @param content
	 * @param height
	 * @param width
	 * @return
	 * @throws WriterException
	 */
	private static BitMatrix getBitMatrix(String content, int height, int width) throws WriterException {
		Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		hints.put(EncodeHintType.MARGIN, 1);
		return new MultiFormatWriter().encode(content,
				BarcodeFormat.QR_CODE, width, height, hints);
	}


}
