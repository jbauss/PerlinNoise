package noise;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageExporter {
	
	public static void saveAsFile(String fileName, RenderedImage image) {
		File file = new File(fileName);
		try {
			ImageIO.write(image, "png", file);
		} catch (IOException e) {
			System.err.println("Failed to save image to file.");
			e.printStackTrace();
		}
	}
	
	public static BufferedImage convertRGBArray(int[][] rgbValues) {
		int height = rgbValues.length;
		int width = rgbValues[0].length;
		
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				bufferedImage.setRGB(x, y, rgbValues[y][x]);
			}
		}
		
		return bufferedImage;		
	}
	
}
