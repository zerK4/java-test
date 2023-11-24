package main.java.org.chirica.calculation;

import java.awt.image.DataBufferByte;

import java.awt.image.BufferedImage;

import java.awt.image.WritableRaster;

public class Calculator {

	private BufferedImage binaryImage;

	public BufferedImage getBinaryImage() {
		return binaryImage;
	}

	public void setBinaryImage(BufferedImage binaryImage) {
		this.binaryImage = binaryImage;
	}

	private static byte[] extractBytes(BufferedImage input) {

		WritableRaster raster = input.getRaster();
		DataBufferByte data = (DataBufferByte) raster.getDataBuffer();

		return (data.getData());

	}

	public double getPercentageOfMartenzite() {
		int whiteCounter = 0;

		byte[] imageBytes = extractBytes(this.binaryImage);

		for (byte i : imageBytes) {
			if (i == -1)
				whiteCounter++;
		}

		return whiteCounter / (float) imageBytes.length;

	}

}
