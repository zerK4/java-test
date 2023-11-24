package main.java.org.chirica;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Base64;
import java.util.Scanner;

import javax.imageio.ImageIO;

import main.java.org.chirica.calculation.Calculator;
import main.java.org.chirica.manipulation.Manipulator;

public class Procesor {

	static BufferedImage original;

	public static void main(String[] args) throws IOException {

		System.out.println("Starging image main.java.raian.manipulation");
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter file name: ");
		String filename = scanner.nextLine();
		File file = new File(filename);

		original = ImageIO.read(file);
		System.out.println("File read");

		Manipulator im = new Manipulator();

		BufferedImage binarized = im.binarize(original);

		Calculator fc = new Calculator();

		fc.setBinaryImage(binarized);

		double martensiteBinary = fc.getPercentageOfMartenzite();

		System.out.println("Saving image binarized+file_name");
		writeImage("binarized-" + filename + ".png", binarized);

		BufferedImage output = binarized;

		output = im.erose(output, 1);
		output = im.dilatate(output, 1);

		System.out.println("Saving binary processed image output.jpg");
		writeImage("binarized-" + filename + ".png", binarized);

		fc.setBinaryImage(output);

		double martensiteDilatate = fc.getPercentageOfMartenzite();

		System.out.println("Fraction in binary: " + martensiteBinary);
		System.out.println("Fraction in binary processed with opening: " + martensiteDilatate);

	}

	private static void writeImage(String output, BufferedImage imageToSave) throws IOException {
		String originalExtension = output.substring(output.lastIndexOf(".") + 1);
		String binaryExtension = "png";
		String newOutput = output.replace(originalExtension, binaryExtension);

		// Salvează imaginea PNG
		File pngFile = new File(newOutput);
		ImageIO.write(imageToSave, binaryExtension, pngFile);

		// Construiește un document XML
		Document doc = new Document();
		Element rootElement = new Element("image");
		rootElement.addContent(new Element("filename").setText(pngFile.getName()));
		rootElement.addContent(new Element("binaryData").setText(imageToBase64(imageToSave)));

		doc.setRootElement(rootElement);

		// Salvează documentul XML
		String xmlOutput = "image-data-" + pngFile.getName() + ".xml";
		XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
		xmlOutputter.output(doc, new FileOutputStream(xmlOutput));

		System.out.println("Image and XML data saved.");
	}

	private static String imageToBase64(BufferedImage image) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(image, "png", baos);
		byte[] imageBytes = baos.toByteArray();
		return Base64.getEncoder().encodeToString(imageBytes);
	}

}
