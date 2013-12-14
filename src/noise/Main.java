package noise;

import java.awt.image.BufferedImage;
import java.util.Random;

public class Main {

	// x is a number in the range min..max
	// converts x to a number in the range [a,b]
	public static float scaleRange(float min, float max, float a, float b, float x) {
	    return (b - a) * (x - min) / (max - min) + a;
	}

	// converts x from a number in the range [-1, 1] to a number in the range [0, 255]
	public static float scale256(float x) {
	    return scaleRange(-1, 1, 0, 255, x);
	}
	
	public static int getARGBColor(int scaledValue) {
		int red = scaledValue;
		int green = scaledValue;
		int blue = scaledValue;
		
		if(scaledValue > 155) {
			// light grey
			red = 195;
			green = 193;
			blue = 187;
		} else if(scaledValue > 150) {
			// medium grey
			red = 155;
			green = 153;
			blue = 147;
		} else if(scaledValue > 140) {
			// dark grey
			red = 118;
			green = 110;
			blue = 116;			
		} else if(scaledValue > 135) {
			// light green
			red = 140;
			green = 166;
			blue = 83;	
		} else if(scaledValue > 130) {
			// sandy yellow
			red = 220;
			green = 217;
			blue = 180;
		} else {
			// ocean blue
			red = 30;
			green = 110;
			blue = 160;
		}
			
		return ((255 << 24) | red << 16 | green << 8 | blue);
	}
	
	public static int getARGBGreyscale(int scaledValue) {
		return ((255 << 24) | scaledValue << 16 | scaledValue << 8 | scaledValue);
	}
	
	public static void generatePerlinImages() {
		Random random = new Random();
		// Create a seeded noise generator
		Noise n = new Noise(random.nextInt());
		
		// Prepare two arrays to fill with ARGB values
		// The images' size will be 800 by 600 pixels
		int[][] argbColor = new int[800][600];
		int[][] argbGreyscale = new int[800][600];
		
		for(int y = 0; y < argbColor.length; y++) {
			for(int x = 0; x < argbColor[0].length; x++) {
				// Generate a noise value for the coordinate (x,y)
				float noiseValue = 0;
				noiseValue += scale256(n.interpolatedNoise(x * 0.01f, y * 0.01f));
				noiseValue += scale256(n.interpolatedNoise(x * 0.02f, y * 0.02f));
				noiseValue += scale256(n.interpolatedNoise(x * 0.04f, y * 0.04f));
				noiseValue += scale256(n.interpolatedNoise(x * 0.08f, y * 0.08f));
				int roundedValue = Math.round(noiseValue / 4f);
				
				// Generate a color and a greyscale ARGB value based on that noise value
				int colorValue = getARGBColor(roundedValue);
				int greyscaleValue = getARGBGreyscale(roundedValue);
				argbColor[y][x] = colorValue;
				argbGreyscale[y][x] = greyscaleValue;
				
			}
		}
		
		// Convert the arrays to buffered PNG-Images
		BufferedImage colorImage = ImageExporter.convertRGBArray(argbColor);
		BufferedImage greyscaleImage = ImageExporter.convertRGBArray(argbGreyscale);
		// Save those PNG-Images to the file system
		ImageExporter.saveAsFile("perlinColor.png", colorImage);
		ImageExporter.saveAsFile("perlinGreyscale.png", greyscaleImage);
	}
	
	public static void main(String[] args) {

		generatePerlinImages();

	}

}